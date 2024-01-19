package org.example.module.orchestrator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Apache Camel, when it starts up, looks for its components (like CxfEndpoint)
 * in the current Spring application context.
 * If the bean is defined in a library, it's not found in the application's context, hence you'll get an error.
 * To resolve that, I needed to make sure that the Spring application context of
 * my application includes the beans defined in the library.
 * Adding a ComponentScan annotation in the application's configuration to scan
 * the packages of the library:
 */
@Configuration
@ComponentScan(basePackages = {"org.example.module.orchestrator",
        "org.example.module.numberconversion"})
public class ApplicationConfig {
}
