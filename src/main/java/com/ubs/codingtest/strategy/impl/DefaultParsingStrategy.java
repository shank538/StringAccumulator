package com.ubs.codingtest.strategy.impl;

import com.ubs.codingtest.strategy.ParsingStrategy;
import com.ubs.codingtest.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultParsingStrategy implements ParsingStrategy {

    private static String DEFAULT_DELIMITER = ",";

    @Override
    public Map<String,String> getDelimiterAndNumbers(String numbers) {

        String delimiter = DEFAULT_DELIMITER;
        if(numbers.startsWith("//")){
            // extracting Delimiter pattern between // and \n and numbers after \n
            delimiter = StringUtils.substringBetween(numbers,"//", "\n");
            numbers = StringUtils.substringAfter(numbers, "\n");
        }

        // Nextline should be considered as delimiter as well
        delimiter = delimiter+"|\n";

        Map<String,String> delimiterNumbersMap = new HashMap<>();
        delimiterNumbersMap.put(Constants.DELIMITER, delimiter);
        delimiterNumbersMap.put(Constants.NUMBERS, numbers);

        return delimiterNumbersMap;
    }
}
