package org.example.cxf.springrest.routes;

import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.example.cxf.springrest.model.NumberDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@UseAdviceWith
class SoapIntegrationRouteTest {

    @Value("${camel.routes.number-to-words.name}")
    private String NUMBER_TO_WORDS_ROUTE;
    @Value("${camel.routes.number-to-words.id}")
    private String NUMBER_TO_WORDS_ROUTE_ID;


    @Value("${camel.routes.number-to-dollars.name}")
    private String NUMBER_TO_DOLLARS_ROUTE;
    @Value("${camel.routes.number-to-dollars.id}")
    private String NUMBER_TO_DOLLARS_ROUTE_ID;

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CamelContext context;

    ModelCamelContext modelCamelContext;

    @Autowired
    private SoapIntegrationRoute soapIntegrationRoute;

    @BeforeEach
    public void setup() throws Exception {
        modelCamelContext = (ModelCamelContext) context;
        context.addRoutes(soapIntegrationRoute);
    }

    @EndpointInject("bean:getNumberToWordsRequestBuilder")
    ProducerTemplate beanEP;

    /*
     * This cleans up all the routes from the Camel context
     */
    @AfterEach
    public void tearDown() throws Exception {
        List<RouteDefinition> routeDefinitions = new ArrayList<>(modelCamelContext.getRouteDefinitions());
        for (RouteDefinition routeDefinition : routeDefinitions) {
            modelCamelContext.removeRouteDefinition(routeDefinition);
        }
        context.addRoutes(soapIntegrationRoute);
    }

    @Test
    public void testNumberToWordsRouteReturnsExpectedResult() throws Exception {
        // Setup mock data
        NumberToWordsResponse numberToWordsResponse = new NumberToWordsResponse();
        numberToWordsResponse.setNumberToWordsResult("ten");
        String beanMethod = "getNumberToWords";


        // Given
        NumberDto numberDto = NumberDto.builder().number("10").build();

        // When
        setupMockSoapCall(numberToWordsResponse, NUMBER_TO_WORDS_ROUTE_ID, beanMethod);
        NumberToWordsResponse response = template.requestBody(NUMBER_TO_WORDS_ROUTE, numberDto, NumberToWordsResponse.class);

        // Then
        assertNotNull(response);
        assertEquals("ten", response.getNumberToWordsResult());

    }

    @Test
    public void shouldNumberToDollarsRouteReturnsExpectedResult() throws Exception {
        // Setup mock data
        NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
        numberToDollarsResponse.setNumberToDollarsResult("ten dollars");
        String beanMethod = "getNumberToDollars";

        // Given
        NumberDto numberDto = NumberDto.builder().number("10").build();

        // When
        setupMockSoapCall(numberToDollarsResponse, NUMBER_TO_DOLLARS_ROUTE_ID, beanMethod);
        NumberToDollarsResponse response = template.requestBody(NUMBER_TO_DOLLARS_ROUTE, numberDto, NumberToDollarsResponse.class);

        // Then
        assertNotNull(response);
        assertEquals("ten dollars", response.getNumberToDollarsResult());

    }

    @Test
    public void testProcessExchange() {
        // Arrange
        NumberDto numberDto = new NumberDto();
        numberDto.setNumber("10");
        DefaultExchange exchange = new DefaultExchange(context);
        exchange.getIn().setBody(numberDto);

        // Act
        soapIntegrationRoute.processExchange(exchange);

        // Assert
        assertNotNull(exchange.getIn().getBody());
        assertEquals("10", exchange.getIn().getBody());
    }

    // TODO: Fix this test
    public void testBeanGetNumberToWordsRequestBuilder_MethodCall() throws Exception {
        // Arrange
        String method = "getNumberToWords";

        NumberDto numberDto = new NumberDto();
        numberDto.setNumber("10");
        NumberToWords numberToWords = new NumberToWords();
        numberToWords.setUbiNum(new BigInteger("10"));

        setupBeanGetNumberToWordsRequestBuilderCall(NUMBER_TO_WORDS_ROUTE_ID, method);

        // Act
        NumberToWords response = template.requestBody(NUMBER_TO_DOLLARS_ROUTE, numberDto, NumberToWords.class);

        // Assert
        assertNotNull(response);
//        Mockito.verify(getNumberToWordsRequestBuilder, Mockito.times(1));

    }


    private void setupMockSoapCall(Object responseObject, String routeId, String beanMethod) throws Exception {
        AdviceWith.adviceWith(modelCamelContext.getRouteDefinition(routeId), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("soap-call-" + routeId).replace().process(exchange -> {
                    exchange.getIn().setBody(responseObject);
                });
            }
        });
    }

    private void setupBeanGetNumberToWordsRequestBuilderCall(String routeId, String method) throws Exception {
        AdviceWith.adviceWith(modelCamelContext.getRouteDefinition(routeId), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // TODO: This method of mocking the bean call is not working, please fix it
                interceptSendToEndpoint("bean:GetNumberToWordsRequestBuilder?method=" + method)
                        .skipSendToOriginalEndpoint()
                        .process(exchange ->
                                exchange.getIn().setBody(new NumberToWords())
                        );
            }
        });


    }

}
