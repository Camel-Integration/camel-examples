package org.example.cxf.restdsl.config.test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolation;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.example.cxf.restdsl.config.NumberConversionRestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberConversionRestConfigTest {
    private NumberConversionRestConfig config;
    Exchange exchange;

   private CamelContext camelContext;

    @BeforeEach
    public void setUp() {
        config = new NumberConversionRestConfig();
        camelContext = new DefaultCamelContext();
        exchange = new DefaultExchange(camelContext);
    }

    @Test
    public void testNumberConversionExceptionHandler_BeanValidationException() {


        Set<ConstraintViolation<Object>> violations = new HashSet<>();

        // Create a mock ConstraintViolation
        ConstraintViolation<Object> violation =  Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation.getMessage()).thenReturn("Test violation message");

        violations.add(violation);
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, new BeanValidationException(exchange, violations, null));

        config.numberConversionExceptionHandler(exchange);

        assertEquals("Validation error", ((Map<String, Object>) exchange.getIn().getBody()).get("message"));
    }

    @Test
    public void testNumberConversionExceptionHandler_UnrecognizedPropertyException() {
        final String errorProperty = "testProperty";

        // Create a mock UnrecognizedPropertyException
        UnrecognizedPropertyException exception = Mockito.mock(UnrecognizedPropertyException.class);
        Mockito.when(exception.getPropertyName()).thenReturn(errorProperty);

        // Set the exception as the EXCEPTION_CAUGHT property of the exchange
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);

        // Call the exception handler method
        config.numberConversionExceptionHandler(exchange);

        // Get the response body
        Map<String, Object> responseBody = exchange.getIn().getBody(Map.class);

        // Assert that the error message is as expected
        assertEquals("The '" + errorProperty + "' property is not recognized.", responseBody.get("errors"));
    }

    @Test
    public void testNumberConversionExceptionHandler_JsonParseException() {

        // Create a mock JsonParseException
        JsonParseException exception = Mockito.mock(JsonParseException.class);
        Mockito.when(exception.getOriginalMessage()).thenReturn("Invalid JSON format.");

        // Set the exception as the EXCEPTION_CAUGHT property of the exchange
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);

        // Call the exception handler method
        config.numberConversionExceptionHandler(exchange);

        // Get the response body
        Map<String, Object> responseBody = exchange.getIn().getBody(Map.class);

        // Assert that the error message is as expected
        assertEquals("Invalid JSON format.", responseBody.get("errors"));
    }

    @Test
    public void testNumberConversionExceptionHandler_GeneralException() {

        // Create a mock JsonParseException
        Exception exception = Mockito.mock(Exception.class);

        // Set the exception as the EXCEPTION_CAUGHT property of the exchange
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);

        // Call the exception handler method
        config.numberConversionExceptionHandler(exchange);

        // Get the response body
        Map<String, Object> responseBody = exchange.getIn().getBody(Map.class);

        // Assert that the error message is as expected
        assertEquals("An unexpected error occurred. Please try again later.", responseBody.get("errors"));
    }

}
