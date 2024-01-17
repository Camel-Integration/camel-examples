package org.example.numberconverter.util;

public class NumberConverter {

    private static final String[] ones = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] teens = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};


    private static String formatNumber(String numberString) {
        try {
            int num = Integer.parseInt(numberString);

            if (num < 1 || num > 1000000) {
                return "Number out of range (1-1000000)";
            }

            if (num == 1000000) {
                return "one million";
            }

            StringBuilder result = new StringBuilder();

            // Extract millions digit
            int millionsDigit = num / 1000000;
            if (millionsDigit > 0) {
                result.append(ones[millionsDigit]).append(" million");
            }

            // Extract thousands digit
            int thousandsDigit = (num % 1000000) / 1000;
            if (thousandsDigit > 0) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(formatNumber(Integer.toString(thousandsDigit))).append(" thousand");
            }

            // Extract hundreds, tens, and ones digits
            int remainder = num % 1000;
            if (remainder > 0 ) {
                if (result.length() > 0 && remainder < 100) {
                    result.append(" and ");
                } else if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(formatHundredsTensOnes(remainder));
            }

            return result.toString();
        } catch (NumberFormatException e) {
            return "Invalid number format";
        }
    }

    private static String formatHundredsTensOnes(int num) {
        StringBuilder result = new StringBuilder();

        int hundredsDigit = num / 100;
        if (hundredsDigit > 0) {
            result.append(ones[hundredsDigit]).append(" hundred");
        }

        int tensOnes = num % 100;
        if (tensOnes > 0) {
            if (hundredsDigit > 0) {
                result.append(" and ");
            }

            if (tensOnes < 10) {
                result.append(ones[tensOnes]);
            } else if (tensOnes < 20) {
                if(tensOnes - 10 == 0)
                    result.append("ten");
                else
                    result.append(teens[tensOnes - 10]);
            } else {
                int tensDigit = tensOnes / 10;
                if (tensDigit > 0 && tensDigit < 10) {  // Ensure the index is within bounds
                    result.append(tens[tensDigit]);
                }

                int onesDigit = tensOnes % 10;
                if (onesDigit > 0) {
                    if (tensDigit > 0) {
                        result.append(" ");
                    }
                    result.append(ones[onesDigit]);
                }
            }
        }

        return result.toString();
    }
    public static String numberToWords(String numberString) {
        return formatNumber(numberString);
    }

    public static String numberToDollars(String numberString) {
        String formattedNumber = formatNumber(numberString);
        return formattedNumber.equals("NaN") ? formattedNumber : formattedNumber + " dollars";
    }

}
