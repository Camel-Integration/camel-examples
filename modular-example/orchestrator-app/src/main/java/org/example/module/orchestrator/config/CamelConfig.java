package org.example.module.orchestrator.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.example.module.numberconversion.route.NumberToDollarsConversionRoute;
import org.example.module.numberconversion.route.NumberToWordsConversionRoute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    private final CamelContext camelContext;

    public CamelConfig(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                camelContext.addRoutes(new NumberToWordsConversionRoute());
                camelContext.addRoutes(new NumberToDollarsConversionRoute());
            }
        };
    }
}
