package org.example.module.numberconversion.route;

import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.example.module.numberconversion.model.NumberConversionDataType;
import org.example.module.numberconversion.util.GetNumberToWordsRequestBuilder;
import org.example.module.numberconversion.util.NumberConversionHeaderUtil;
import org.springframework.stereotype.Component;

@Component
public class NumberToWordsConversionRoute extends RouteBuilder {

    public final String NUMBER_TO_WORDS_ROUTE = NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRoute();
    public final String NUMBER_TO_WORDS_ROUTE_ID = NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRouteId();

    private final String NUMBER_SERVICE_URI = "cxf:bean:convertNumber";
    @Override
    public void configure() {

        from(NUMBER_TO_WORDS_ROUTE).id(NUMBER_TO_WORDS_ROUTE_ID)
                .process((Exchange exchange) -> {
                    NumberConversionDataType data = exchange.getIn().getBody(NumberConversionDataType.class);
                    NumberToWords contents = data.getNumberToWordsInputType();
                    var number = contents.getUbiNum();
                    exchange.getIn().setBody(number);
                })
                .bean(GetNumberToWordsRequestBuilder.class, "getNumberToWords")
                .marshal().jaxb()
                .process(NumberConversionHeaderUtil::setNumberToWordsHeader)
                .to(NUMBER_SERVICE_URI).id("soap-call-number-to-words")    // call the SOAP service
                .process(exchange -> {
                    NumberToWordsResponse contents = exchange.getIn().getBody(NumberToWordsResponse.class);
                    NumberConversionDataType data = new NumberConversionDataType();
                    data.setNumberToWordsOutputType(contents);
                    exchange.getIn().setBody(data);
                })
                .end();
    }
}
