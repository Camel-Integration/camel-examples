package org.example.numberconverter.soap.endpoint;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.example.numberconverter.util.NumberConverter;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NumberConversionEndpoint {

    private static final String NAMESPACE_URI = "http://www.dataaccess.com/webservicesserver/";

    public NumberConversionEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "NumberToWords")
    @ResponsePayload
    public NumberToWordsResponse numberToWords(@RequestPayload NumberToWords request) {
        NumberToWordsResponse response = new NumberToWordsResponse();
        String numberString = String.valueOf(request.getUbiNum());
        response.setNumberToWordsResult(NumberConverter.numberToWords(numberString));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "NumberToDollars")
    @ResponsePayload
    public NumberToDollarsResponse numberToDollars(@RequestPayload NumberToDollars request) {
        NumberToDollarsResponse response = new NumberToDollarsResponse();
        String numberString = String.valueOf(request.getDNum());
        response.setNumberToDollarsResult(NumberConverter.numberToDollars(numberString));
        return response;
    }
}
