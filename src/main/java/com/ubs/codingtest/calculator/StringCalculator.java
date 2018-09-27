package com.ubs.codingtest.calculator;

import com.ubs.codingtest.exception.InvalidDelimiter;
import com.ubs.codingtest.exception.InvalidNumberException;
import com.ubs.codingtest.strategy.ParsingStrategy;
import com.ubs.codingtest.util.Constants;
import com.ubs.codingtest.util.DelimiterUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    Logger logger = LoggerFactory.getLogger(StringCalculator.class);

    public int add(String numbers){

        int total = 0;
        if(!StringUtils.isEmpty(numbers)) {

            logger.info("Processing numbers string : {}", numbers);
            Map<String,String> delimiterMap = parsingStrategy.getDelimiterAndNumbers(numbers);
            String delimiter = delimiterMap.get(Constants.DELIMITER);
            numbers = delimiterMap.get(Constants.NUMBERS);

            logger.info("Numbers for the input string : {}", numbers);

            List<Integer> negativeValues = new ArrayList();

            try {
                total = Arrays.stream(numbers.split(DelimiterUtils.getEscapedDelimiter(delimiter)))
                        .map((number) -> Integer.parseInt(number))
                        .filter((number) -> number < Constants.DEFAULT_ADD_THRESHOLD)
                        // Getting all  the negattive numbers
                        .map((number) -> {
                            if (number < 0) {
                                negativeValues.add(number);
                            }
                            return number;
                        })
                        // Calculating the sum of all numbers
                        .collect(Collectors.summingInt(Integer::intValue));

            }catch(NumberFormatException  nfe){
                logger.error("Invalid delimiter supplied : {}",  numbers);
                throw new InvalidDelimiter("Invalid delimiter supplied : "+ numbers);
            }

            if(negativeValues.size() > 0){
                logger.info("negatives not allowed : {}",  negativeValues);
                throw new InvalidNumberException("negatives not allowed : "+ negativeValues);
            }
        }

        logger.info("Total for input string : {}", total);
        return total;
    }

    public ParsingStrategy getParsingStrategy() {
        return parsingStrategy;
    }

    public void setParsingStrategy(ParsingStrategy parsingStrategy) {
        this.parsingStrategy = parsingStrategy;
    }
}
