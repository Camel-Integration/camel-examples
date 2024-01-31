package org.example.cxf.springrest.routes;

import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@UseAdviceWith
class SoapIntegrationRouteTest {

    @Value("${camel.routes.number-to-words.name}")
    private String NUMBER_TO_WORDS_ROUTE;
    @Value("${camel.routes.number-to-words.id}")
    private String NUMBER_TO_WORDS_ROUTE_ID;


    @Value("${camel.routes.number-to-dollars.name}")
    private String NUMBER_TO_WORDS_DOLLARS;
    @Value("${camel.routes.number-to-dollars.id}")
    private String NUMBER_TO_WORDS_DOLLARS_ID;

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CamelContext context;

    ModelCamelContext modelCamelContext;

    private void setupMockSoapCall_NumberToDollars(String numberAsDollarsString) throws Exception {
        AdviceWith.adviceWith(modelCamelContext.getRouteDefinition(NUMBER_TO_WORDS_DOLLARS_ID), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("soap-call-number-to-dollars").replace().process(exchange -> {
                    NumberToDollarsResponse response = new NumberToDollarsResponse();
                    response.setNumberToDollarsResult(numberAsDollarsString);
                    exchange.getIn().setBody(response);
                });
            }
        });
    }

}
