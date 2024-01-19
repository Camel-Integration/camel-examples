
# Camel Examples

This repo contains various implementation examples to demonstrate how to use Apache Camel 
for enterprise integration and routing.<br>
Feel free to use the code as you wish with no restrictions.

## Tech Stack

**Framework:** Spring Boot, Apache Camel

**Server:** Tomcat (Embedded)
## Structure

- camel-examples
    - [camel-cxf-examples](camel-cxf-examples/README.md)
      - [x] camel-rest-dsl (expose rest api with Camel REST DSL) 
      - [x] spring-mvc-rest (expose rest api with Spring MVC)
    - messaging
      - [ ] kafka
    - patterns-implementation
      - [ ] wire-tap
      - [ ] dynamic-routing
      - [ ] parallel-processing
    - [modular-example](modular-example/README.md)
      - [x] orchestrator-app (main application)
      - [x] number-conversion-module (built as java library)
      - [ ] module-2
    - [util](util/README.md)
      - [x] number-converter-service (convert number to word)


