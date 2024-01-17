package org.example.numberconverter;

import org.example.numberconverter.util.NumberConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberToWordsConverterTest {
    @Test
    public void testFormatNumber() {
        //1
        assertEquals("one", NumberConverter.numberToWords("1"));
        //10
        assertEquals("ten", NumberConverter.numberToWords("10"));
        //11
        assertEquals("eleven", NumberConverter.numberToWords("11"));
        //20
        assertEquals("twenty", NumberConverter.numberToWords("20"));
        //21
        assertEquals("twenty one", NumberConverter.numberToWords("21"));
        //100
        assertEquals("one hundred", NumberConverter.numberToWords("100"));
        //101
        assertEquals("one hundred and one", NumberConverter.numberToWords("101"));
        //110
        assertEquals("one thousand", NumberConverter.numberToWords("1000"));
        //111
        assertEquals("one thousand and one", NumberConverter.numberToWords("1001"));
        //1000
        assertEquals("one million", NumberConverter.numberToWords("1000000"));
        //1001
        assertEquals("Number out of range (1-1000000)", NumberConverter.numberToWords("1000001"));
        //abc
        assertEquals("Invalid number format", NumberConverter.numberToWords("abc"));
        //4322
        assertEquals("four thousand three hundred and twenty two", NumberConverter.numberToWords("4322"));
        //98732
        assertEquals("ninety eight thousand seven hundred and thirty two", NumberConverter.numberToWords("98732"));
        //488888
        assertEquals("four hundred and eighty eight thousand eight hundred and eighty eight", NumberConverter.numberToWords("488888"));
        //99999
        assertEquals("ninety nine thousand nine hundred and ninety nine", NumberConverter.numberToWords("99999"));

    }
}
