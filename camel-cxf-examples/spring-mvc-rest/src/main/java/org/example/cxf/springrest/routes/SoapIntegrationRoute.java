package org.example.cxf.springrest.routes;

import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.example.cxf.springrest.model.NumberDto;
import org.example.cxf.springrest.util.GetNumberToWordsRequestBuilder;
import org.example.cxf.springrest.util.NumberConversionHeaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

@Component
@Slf4j
public class SoapIntegrationRoute extends RouteBuilder implements NumberConversionRoutes {
    private static final String NUMBER_SERVICE_URI = "cxf:bean:convertNumber";

    @Value("${camel.routes.number-to-words.name}")
    private String NUMBER_TO_WORDS_ROUTE;
    @Value("${camel.routes.number-to-words.id}")
    private String NUMBER_TO_WORDS_ROUTE_ID;


    @Value("${camel.routes.number-to-dollars.name}")
    private String NUMBER_TO_WORDS_DOLLARS;
    @Value("${camel.routes.number-to-dollars.id}")
    private String NUMBER_TO_WORDS_DOLLARS_ID;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .handled(true)
                .process(this::handleException)
                .end();

        processRoute(NUMBER_TO_WORDS_ROUTE, NUMBER_TO_WORDS_ROUTE_ID, "getNumberToWords", NumberToWordsResponse.class, NumberConversionHeaderUtil::setNumberToWordsHeader);
        processRoute(NUMBER_TO_WORDS_DOLLARS, NUMBER_TO_WORDS_DOLLARS_ID, "getNumberToDollars", NumberToDollarsResponse.class, NumberConversionHeaderUtil::setNumberToDollarsHeader);
    }

    private void handleException(Exchange exchange) throws Exception {
        String step = exchange.getProperty("step", String.class);
        log.error("Exception occurred at step: " + step + " - " + exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage());
        throw new Exception("Error occurred while "+ step);
    }

    private <T> void processRoute(String route, String routeId, String method, Class<T> responseClass, Processor headerSetter) {
        from(route).id(routeId)
                .process(exchange -> exchange.setProperty("step", "processing-input-dto"))
                .process(this::processExchange)
                .bean(GetNumberToWordsRequestBuilder.class, method)
                .marshal().jaxb()
                .process(headerSetter)
                .process(exchange -> exchange.setProperty("step", "calling-soap-service"))
                .to(NUMBER_SERVICE_URI)
                .process(exchange -> {
                    T response = exchange.getIn().getBody(responseClass);
                })
                .end();
    }

    private void processExchange(Exchange exchange) {
        NumberDto numberDto = exchange.getIn().getBody(NumberDto.class);
        var number = numberDto.getNumber();
        exchange.getIn().setBody(numberDto);
    }




    @Override
    public String getNumberToWordsRoute() {
        return NUMBER_TO_WORDS_ROUTE;
    }

    @Override
    public String getNumberToDollarsRoute() {
        return NUMBER_TO_WORDS_DOLLARS;
    }
}
