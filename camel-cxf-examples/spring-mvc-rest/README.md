# Camel REST DSL
This example uses the Spring MVC REST controller to expose a RESTful API.<br>
Using the `ProducerTemplate` we can send a message to the Camel route and get the response back.<br>

## What to expect from this example?

<img alt="img.png" src="docs/REST_SpringMVC.png" style="width:800px;"/>

Using **Spring MVC**, we will expose two RESTful API endpoints to convert a number to words and dollars.
The Number Conversion implementation is provided by a public SOAP Web Service.<br>
We're **transforming a SOAP Web Service to a RESTful API**.<br>
Instead of creating Java objects to represent the REST API response, we used the generated POJOs from the WSDL file.<br>
- E.g. `NumberToWordsResponse` and `NumberToDollarsResponse` are generated from the WSDL file and
  used as response objects.

## API Reference

### Convert number to words

```http
  POST /api/number-conversion/convertNumberToWords
```
#### Request Data:

| Parameter | Type     | Description                  | Required Type   |
| :-------- | :------- |:-----------------------------|:----------------|
| `number`  | `string` | Number to convert to words   |**Required**.    |

Example Request:
`
{
"number": "123"
}
`
#### Response Data:
| Parameter             | Type     | Description               |
|:----------------------| :------- |:--------------------------|
| `numberToWordsResult` | `string` | Converted numer to words. |

Example Response:
`
{
"numberToWordsResult": "one hundred and twenty three"
}
`

### Convert number to dollars

```http
  POST /api/number-conversion/convertNumberToDollars
```
#### Request Data:

| Parameter | Type     | Description                  | Required Type   |
| :-------- | :------- |:-----------------------------|:----------------|
| `number`  | `string` | Number to convert to dollars |**Required**.    |

Example Request:
`
{
"number": "123"
}
`
#### Response Data:
| Parameter              | Type     | Description                 |
|:-----------------------| :------- |:----------------------------|
| `numberToDollarsResult`| `string` | Converted numer to dollars. |

Example Response:
`
{
"numberToDollarsResult": "one hundred and twenty three dollars"
}
`

### API Documentation

You can find the API documentation in the JSON format at
[http://localhost:8080/api-docs](http://localhost:8080/api-docs).<br>
This service has also the Swagger UI enable and that is accessible at [https://localhost:8080/swagger-ui.html](https://localhost:8080/swagger-ui.html)

## Run Locally

Clone the project

```bash
  git clone
```

Go to the project directory

```bash
  cd camel-cxf-examples/spring-mvc-rest
```

Start the server

```bash
  mvn spring-boot:run
```




## More about Camel Exception Handling

You can find more about Exception Handling in the [Exception Clause ](https://arc.net/l/quote/vbvfntgf) chapter.
