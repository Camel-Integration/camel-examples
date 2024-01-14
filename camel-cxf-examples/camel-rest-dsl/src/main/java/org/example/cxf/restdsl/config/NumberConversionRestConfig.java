package org.example.cxf.restdsl.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteConfigurationBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NumberConversionRestConfig extends RouteConfigurationBuilder {
    @Override
    public void configuration() throws Exception {

        routeConfiguration("number-conversion-rest-config")
                .onException(Exception.class)
                .handled(true)
                .process(this::numberConversionExceptionHandler)
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpResponseCode", constant(400))
                .end();
    }

    public void numberConversionExceptionHandler(Exchange exchange) {

        Map<String, Object> errorResponse = new HashMap<>();
        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        errorResponse.put("message", "Validation error");
        if (exception instanceof BeanValidationException) {
            BeanValidationException beanException = (BeanValidationException) exception;
            String errors = beanException.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            errorResponse.put("errors", errors);
        } else if (exception instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException unrecognizedException = (UnrecognizedPropertyException) exception;
            errorResponse.put("errors", "The '" + unrecognizedException.getPropertyName() + "' property is not recognized.");
        } else if (exception instanceof JsonParseException) {
            errorResponse.put("errors", "Invalid JSON format.");
        } else {
            errorResponse.put("errors", "An unexpected error occurred. Please try again later.");
        }

        exchange.getIn().setBody(errorResponse);
    }
}
