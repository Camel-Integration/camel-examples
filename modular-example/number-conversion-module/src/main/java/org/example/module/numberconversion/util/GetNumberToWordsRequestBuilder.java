package org.example.module.numberconversion.util;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToWords;

import java.math.BigDecimal;
import java.math.BigInteger;

public class GetNumberToWordsRequestBuilder {
    public NumberToWords getNumberToWords(BigInteger number) {
        NumberToWords request = new NumberToWords();
        request.setUbiNum(number);
        return request;
    }

    public NumberToDollars getNumberToDollars(BigDecimal number) {
        NumberToDollars request = new NumberToDollars();
        request.setDNum(number);
        return request;
    }
}
