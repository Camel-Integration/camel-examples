package org.example.module.numberconversion.route;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.example.module.numberconversion.model.NumberConversionDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
@UseAdviceWith
public class NumberToDollarsConversionRouteTest {

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CamelContext context;

    ModelCamelContext modelCamelContext;

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new NumberToDollarsConversionRoute();
    }

    @BeforeEach
    public void setup() throws Exception {
        modelCamelContext = (ModelCamelContext) context;
        context.addRoutes(createRouteBuilder());
    }

    @AfterEach
    public void tearDown() throws Exception {
        List<RouteDefinition> routeDefinitions = new ArrayList<>(modelCamelContext.getRouteDefinitions());
        for (RouteDefinition routeDefinition : routeDefinitions) {
            modelCamelContext.removeRouteDefinition(routeDefinition);
        }
        context.addRoutes(new NumberToDollarsConversionRoute());
    }

    @Test
    public void testNumberConversionToDollarsRouteWithMockedSoapCall_10() throws Exception {
        // Setup mock data
        setupMockSoapCall_NumberToDollars("ten dollars");

        NumberConversionDataType data = new NumberConversionDataType();
        NumberToDollars numberToDollars = new NumberToDollars();
        numberToDollars.setDNum(new BigDecimal("10"));
        data.setNumberToDollarsInputType(numberToDollars);

        NumberConversionDataType response;
        response = template.requestBody(
                NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRoute(),
                data,
                NumberConversionDataType.class);

        assertNotNull(response);

        Assertions.assertEquals("ten dollars", response.getNumberToDollarsOutputType().getNumberToDollarsResult());

    }

    @Test
    public void testNumberConversionToDollarsRouteWithMockedSoapCall_20() throws Exception {
        // Setup mock data
        setupMockSoapCall_NumberToDollars("twenty dollars");

        NumberConversionDataType data = new NumberConversionDataType();
        NumberToDollars numberToDollars = new NumberToDollars();
        numberToDollars.setDNum(new BigDecimal("20"));
        data.setNumberToDollarsInputType(numberToDollars);

        NumberConversionDataType response;
        response = template.requestBody(
                NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRoute(),
                data,
                NumberConversionDataType.class);

        assertNotNull(response);

        Assertions.assertEquals("twenty dollars", response.getNumberToDollarsOutputType().getNumberToDollarsResult());

    }

    private void setupMockSoapCall_NumberToDollars(String numberAsDollarsString) throws Exception {
        AdviceWith.adviceWith(modelCamelContext.getRouteDefinition(NumberConversionRoutes.NUMBER_CONVERSION_TO_DOLLARS_ROUTE.getRouteId()), context, new AdviceWithRouteBuilder() {
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
