
# Camel Examples

This repo contains various implementation examples to demonstrate how to use Apache Camel 
for enterprise integration and routing.<br>
Feel free to use the code as you wish with no restrictions.

## Tech Stack

**Framework:** Spring Boot, Apache Camel

**Server:** Tomcat (Embedded)
## Structure

- camel-examples
    - camel-cxf-examples
        - [x] [camel-rest-dsl](camel-cxf-examples/camel-rest-dsl/README.md)
    - messaging
        - [ ] kafka
    - patterns-implementation
        - [ ] wire-tap
        - [ ] dynamic-routing
        - [ ] parallel-processing
    - modular
        - [ ] orchestrator
        - [ ] module-1
        - [ ] module-2


## FAQ

#### Do I need to stand up a SOAP Web Service to test the Camel CXF examples? 
No, you don't. These examples use some public SOAP Web Services for testing purposes.<br>
[Number Conversion](https://www.dataaccess.com/webservicesserver/NumberConversion.wso?WSDL) is used in the examples.

