package com.ubs.codingtest.calculator;

import com.ubs.codingtest.exception.InvalidDelimiter;
import com.ubs.codingtest.exception.InvalidNumberException;
import com.ubs.codingtest.strategy.ParsingStrategy;
import com.ubs.codingtest.util.Constants;
import com.ubs.codingtest.util.DelimiterUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringCalculator {

    private ParsingStrategy parsingStrategy;

    public StringCalculator(ParsingStrategy parsingStrategy){
        this.parsingStrategy = parsingStrategy;
    }

    public int add(String numbers){

        int total = 0;
        if(!StringUtils.isEmpty(numbers)) {

            Map<String,String> delimiterMap = parsingStrategy.getDelimiterAndNumbers(numbers);
            String delimiter = delimiterMap.get(Constants.DELIMITER);
            numbers = delimiterMap.get(Constants.NUMBERS);

            List<Integer> negativeValues = new ArrayList();

            try {
                total = Arrays.stream(numbers.split(DelimiterUtils.getEscapedDelimiter(delimiter)))
                        .map((number) -> Integer.parseInt(number))
                        .filter((number) -> number < Constants.DEFAULT_ADD_THRESHOLD)
                        .map((number) -> {
                            if (number < 0) {
                                negativeValues.add(number);
                            }
                            return number;
                        })
                        .collect(Collectors.summingInt(Integer::intValue));

            }catch(NumberFormatException  nfe){
                throw new InvalidDelimiter("Invalid delimiter supplied : "+ numbers);
            }

            if(negativeValues.size() > 0){
                throw new InvalidNumberException("negatives not allowed : "+ negativeValues);
            }
        }

        return total;
    }

    public ParsingStrategy getParsingStrategy() {
        return parsingStrategy;
    }

    public void setParsingStrategy(ParsingStrategy parsingStrategy) {
        this.parsingStrategy = parsingStrategy;
    }
}
