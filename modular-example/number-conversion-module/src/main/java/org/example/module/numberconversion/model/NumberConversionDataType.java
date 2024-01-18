package org.example.module.numberconversion.model;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import com.dataaccess.webservicesserver.NumberToWords;
import com.dataaccess.webservicesserver.NumberToWordsResponse;
import lombok.Data;

/**
 * This class represents the data for number conversion.
 * For the NumberToWords route, use numberToWordsInputType as input and expect numberToWordsOutputType as output.
 * For the NumberToDollars route, use numberToDollarsInputType as input and expect numberToDollarsOutputType as output.
 */
@Data
public class NumberConversionDataType {
    private NumberToWords numberToWordsInputType;
    private NumberToWordsResponse numberToWordsOutputType;
    private NumberToDollars numberToDollarsInputType;
    private NumberToDollarsResponse numberToDollarsOutputType;
}
