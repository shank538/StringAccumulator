package com.ubs.codingtest.strategy;

import java.util.Map;

public interface ParsingStrategy {

    public Map<String,String> getDelimiterAndNumbers(String numbers);
}
