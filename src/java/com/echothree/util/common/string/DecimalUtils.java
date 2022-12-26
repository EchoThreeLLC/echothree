// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.common.string;

public class DecimalUtils {

    private DecimalUtils() {
        super();
    }

    private static class DecimalUtilsHolder {
        static DecimalUtils instance = new DecimalUtils();
    }

    public static DecimalUtils getInstance() {
        return DecimalUtilsHolder.instance;
    }

    /**
     * 
     * @param minusSign Character to use when looking for a minus sign in the value, may be null if amount is positive-only
     * @param fractionSeparator Character that separates the whole part of the value from the decimal portion
     * @param fractionDigits Number of characters to look for in the decimal portion of the value
     * @param value Value being parsed
     */
    public String parse(String minusSign, String fractionSeparator, Integer fractionDigits, String value) {
        String result;
        
        if(value.equalsIgnoreCase("MAX_VALUE") || value.equalsIgnoreCase("MIN_VALUE")) {
            result = value;
        } else {
            char []rawValue = value.toCharArray();
            int size = rawValue.length;
            StringBuilder cleanWhole = new StringBuilder(size);
            boolean checkMinusSign = minusSign != null;
            char charMinusSign = checkMinusSign? minusSign.charAt(0): 0;
            boolean minusSignFound = false;
            char charFractionSeparator = fractionSeparator == null? '\u0000': fractionSeparator.charAt(0);
            int intFractionDigits = fractionDigits == null? 0: fractionDigits;
            StringBuilder cleanFraction = new StringBuilder(intFractionDigits);
            boolean separatorFound = false;
            StringBuilder parsedValue = new StringBuilder(size);

            for(int i = 0; i < size; i++) {
                char testChar = rawValue[i];

                if(testChar >= '\u0030' && testChar <= '\u0039') {
                    if(separatorFound) {
                        cleanFraction.append(testChar);
                        if(cleanFraction.length() == intFractionDigits)
                            break;
                    } else {
                        cleanWhole.append(testChar);
                    }
                } else if(testChar == charFractionSeparator) {
                    separatorFound = true;
                } else if(checkMinusSign && testChar == charMinusSign) {
                    minusSignFound = true;
                }
            }

            for(int i = cleanFraction.length(); i < intFractionDigits; i++) {
                cleanFraction.append('0');
            }

            if(minusSignFound) {
                parsedValue.append('-');
            }

            result = parsedValue.append(cleanWhole).append(cleanFraction).toString();
        }
        
        return result;
    }
    
}
