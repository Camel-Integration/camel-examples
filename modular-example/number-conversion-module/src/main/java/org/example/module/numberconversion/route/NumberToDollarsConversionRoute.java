package org.example.module.numberconversion.route;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.example.module.numberconversion.model.NumberConversionDataType;
import org.example.module.numberconversion.util.GetNumberToWordsRequestBuilder;
import org.example.module.numberconversion.util.NumberConversionHeaderUtil;
import org.springframework.stereotype.Component;

@Component
public class NumberToDollarsConversionRoute extends RouteBuilder {

    public final String NUMBER_TO_DOLLARS_ROUTE = NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRoute();
    public final String NUMBER_TO_DOLLARS_ROUTE_ID = NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRouteId();
    private final String NUMBER_SERVICE_URI = "cxf:bean:convertNumber";
    @Override
    public void configure() throws Exception {

        from(NUMBER_TO_DOLLARS_ROUTE).id(NUMBER_TO_DOLLARS_ROUTE_ID)
                .process((Exchange exchange) -> {
                    NumberConversionDataType data = exchange.getIn().getBody(NumberConversionDataType.class);
                    NumberToDollars contents = data.getNumberToDollarsInputType();
                    var number = contents.getDNum();
                    exchange.getIn().setBody(number);
                })
                .bean(GetNumberToWordsRequestBuilder.class, "getNumberToDollars")
                .marshal().jaxb()
                .process(NumberConversionHeaderUtil::setNumberToDollarsHeader)
                .to(NUMBER_SERVICE_URI).id("soap-call-number-to-dollars")    // call the SOAP service
                .process(exchange -> {
                    NumberToDollarsResponse contents = exchange.getIn().getBody(NumberToDollarsResponse.class);
                    NumberConversionDataType data = new NumberConversionDataType();
                    data.setNumberToDollarsOutputType(contents);
                    exchange.getIn().setBody(data);
                })
                .end();
    }
}
