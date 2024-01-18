package org.example.module.numberconversion.route;

import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.example.module.numberconversion.model.NumberConversionDataType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@UseAdviceWith
public class NumberToWordsConversionRouteTest {

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CamelContext context;

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new NumberToWordsConversionRoute();
    }

    ModelCamelContext modelCamelContext = (ModelCamelContext) context;

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
        context.addRoutes(new NumberToWordsConversionRoute());
    }

    @Test
    public void testNumberConversionToWordsRouteWithMockedSoapCall_10() throws Exception {
        // Setup mock data
        setupMockSoapCall_NumberToWords("ten");

        NumberConversionDataType data = new NumberConversionDataType();
        NumberToWords numberToWords = new NumberToWords();
        numberToWords.setUbiNum(new java.math.BigInteger("10"));
        data.setNumberToWordsInputType(numberToWords);

        NumberConversionDataType response = template.requestBody(
                NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRoute(),
                data,
                NumberConversionDataType.class);

        assertNotNull(response);

        Assertions.assertEquals("ten", response.getNumberToWordsOutputType().getNumberToWordsResult());

    }

    @Test
    public void testNumberConversionToWordsRouteWithMockedSoapCall_20() throws Exception {
        // Setup mock data
        setupMockSoapCall_NumberToWords("twenty");

        NumberConversionDataType data = new NumberConversionDataType();
        NumberToWords numberToWords = new NumberToWords();
        numberToWords.setUbiNum(new java.math.BigInteger("10"));
        data.setNumberToWordsInputType(numberToWords);

        NumberConversionDataType response;
        response = template.requestBody(
                NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRoute(),
                data,
                NumberConversionDataType.class);

        assertNotNull(response);

        Assertions.assertEquals("twenty", response.getNumberToWordsOutputType().getNumberToWordsResult());

    }

    private void setupMockSoapCall_NumberToWords(String numberAsString) throws Exception {
        AdviceWith.adviceWith(modelCamelContext.getRouteDefinition(NumberConversionRoutes.NUMBER_CONVERSION_TO_WORDS_ROUTE.getRouteId()), context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("soap-call-number-to-words").replace().process(exchange -> {
                    NumberToWordsResponse response = new NumberToWordsResponse();
                    response.setNumberToWordsResult(numberAsString);
                    exchange.getIn().setBody(response);
                });
            }
        });
    }

}
