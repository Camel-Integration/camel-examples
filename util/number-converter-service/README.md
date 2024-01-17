# Number Converter Service 

To spin up a local implementation of the SOAP and REST services, for number conversion, just use this service.<br>

## What's inside?
This service contains a SOAP and REST implementation of the number converter service.<br>
I was build to not depend of any external/public SOAP Web Service used throughout these examples.<br>


### SOAP
The WSDL is located in the resources folder `/resources/wsdl/NumberConverterService.wsdl`.<br>
It only holds the implementation to convert the number to words and number to dollars on SOAP 1.1.<br>
The SOAP 1.2 implementation is not available yet.<br>

### REST
Using Spring MVC, we will expose two RESTful API endpoints to convert a number to words and dollars.<br>

## API Reference

After you spin up the service, you can access view the SOAP WSDL at [http://localhost:8080/ws/NumberConversion.wsdl](http://localhost:8085/ws/NumberConversion.wsdl)<br>
You can invoke the two SOAP operations using the Postman.<br>

- SOAP
    - Operation: `NumberToWords`
      - HTTP: POST http://localhost:8080/ws/webservicesserver/numberConversion.wso
        - Request Body
          ```xml
            <?xml version="1.0" encoding="utf-8"?>
             <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
               <tns:NumberToWords xmlns:tns="http://www.dataaccess.com/webservicesserver/">
                <tns:ubiNum>4322</tns:ubiNum>
               </tns:NumberToWords>
              </soap:Body>
             </soap:Envelope>
          ```
        - Response Body
          ```xml
           <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
             <SOAP-ENV:Body>
              <ns2:NumberToWordsResponse xmlns:ns2="http://www.dataaccess.com/webservicesserver/">
               <ns2:NumberToWordsResult>four thousand three hundred and twenty two</ns2:NumberToWordsResult>
              </ns2:NumberToWordsResponse>
             </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
          ```
    - Operation: `NumberToDollars`
        - HTTP: POST: http://localhost:8080/ws/webservicesserver/numberConversion.wso
            - Request Body
              ```xml
                <?xml version="1.0" encoding="utf-8"?>
                 <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                  <soap:Body>
                   <tns:NumberToDollars xmlns:tns="http://www.dataaccess.com/webservicesserver/">
                    <tns:dNum>100</tns:dNum>
                   </tns:NumberToDollars>
                  </soap:Body>
                 </soap:Envelope>
              ```
            - Response Body
              ```xml
               <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP-ENV:Header/>
                 <SOAP-ENV:Body>
                  <ns2:NumberToDollarsResponse xmlns:ns2="http://www.dataaccess.com/webservicesserver/">
                   <ns2:NumberToDollarsResult>four thousand three hundred and twenty two</ns2:NumberToDollarsResult>
                  </ns2:NumberToDollarsResponse>
                 </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
              ```
- REST
    - Operation: `Convert number to words`
        - HTTP: POST http://localhost:8085/api/numberConversion/numberToWords
            - Request Body
              ```json
                {
                  "number": "123"
                }
              ```
            - Response Body
              ```json
                {
                  "numberToWordsResult": "one hundred and twenty three"
                }
              ```
- Operation: `Convert number to dollars`
    - HTTP: POST http://localhost:8085/api/numberConversion/numberToDollars
        - Request Body
          ```json
            {
              "number": "123"
            }
          ```
        - Response Body
          ```json
            {
              "numberToDollarsResult": "one hundred and twenty three dollars"
            }
          ```

## Run Locally

Clone the project

```bash
  git clone
```

Go to the project directory

```bash
  cd util/number-converter-service
```

Build the project

```bash
  mvn clean install
```

Start the server

```bash
  mvn spring-boot:run
```
