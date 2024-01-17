package org.example.numberconverter.rest.controller;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.example.numberconverter.util.NumberConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/numberConversion")
public class NumberConversionController {

    @PostMapping("/numberToWords")
    public NumberToWordsResponse convertNumberToWords (@RequestBody NumberToWords numberToWords) {
        String convertedNumber = NumberConverter.numberToWords(String.valueOf(numberToWords.getUbiNum()));
        NumberToWordsResponse numberToWordsResponse = new NumberToWordsResponse();
        numberToWordsResponse.setNumberToWordsResult(convertedNumber);
        return numberToWordsResponse;
    }

    @PostMapping("/numberToDollars")
    public NumberToDollarsResponse convertNumberToDollars (@RequestBody NumberToDollars numberToDollars) {
        String convertedNumber = NumberConverter.numberToWords(String.valueOf(numberToDollars.getDNum()));
        NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
        numberToDollarsResponse.setNumberToDollarsResult(convertedNumber);
        return numberToDollarsResponse;
    }

}
