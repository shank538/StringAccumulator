package com.ubs.codingtest.util;

import java.util.HashSet;
import java.util.Set;

public class DelimiterUtils {

    private static Set<String> operators = new HashSet();

    static{
        operators.add("*");
        operators.add("^");
        operators.add("+");
    }

    public static String getEscapedDelimiter(String delimiter) {

        String escapedDelimiter = "";
        for (String operand : delimiter.split("")) {
            if(operators.contains(operand)){
                escapedDelimiter += "\\"+operand;
            }else{
                escapedDelimiter += operand;
            }
        }

        return escapedDelimiter;
    }
}
