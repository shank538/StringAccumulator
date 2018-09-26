package com.ubs.codingtest.calculator;

import com.ubs.codingtest.exception.InvalidDelimiter;
import com.ubs.codingtest.exception.InvalidNumberException;
import com.ubs.codingtest.strategy.impl.DefaultParsingStrategy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;

public class StringCalculatorTest {

    private StringCalculator stringCalculator = null;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){
        stringCalculator = new StringCalculator(new DefaultParsingStrategy());
    }

    @Test
    public void testEmptyStringShouldReturnZero(){

        String numbers = "";
        assertEquals(0, stringCalculator.add(numbers));
    }

    @Test
    public void testSinglePositiveNumberShouldReturnNumber(){

        String numbers = "3";
        assertEquals(3, stringCalculator.add(numbers));
    }

    @Test
    public void testTwoPositiveNumberShouldReturnTotal(){

        String numbers = "3,5";
        assertEquals(8, stringCalculator.add(numbers));
    }

    @Test
    public void testMultiplePositiveNumberShouldReturnTotal(){

        String numbers = "3,5,7,9";
        assertEquals(24, stringCalculator.add(numbers));
    }

    @Test
    public void testMultiplePositiveNumberWithNextLineShouldReturnTotal(){

        String numbers = "3,5\n7,9";
        assertEquals(24, stringCalculator.add(numbers));
    }

    @Test
    public void testMultiplePositiveNumberWithTwoNextLineShouldReturnTotal(){

        String numbers = "3,5\n7,9\n3";
        assertEquals(27, stringCalculator.add(numbers));
    }

    @Test(expected = InvalidDelimiter.class)
    public void testInvalidDelimiterStringToThrowException(){

        String numbers = "3,5\n,7,9";
        assertEquals(24, stringCalculator.add(numbers));
    }

    @Test
    public void testSingleDelimiterInFirstLineToReturnTotal(){

        String numbers = "//;\n7;9;5";
        assertEquals(21, stringCalculator.add(numbers));
    }

    @Test(expected = InvalidDelimiter.class)
    public void testInvalidDelimiterInTheNumbers(){

        String numbers = "//;\n7,9,5";
        stringCalculator.add(numbers);
    }

    @Test
    public void testSingleNegativeNumberShouldThrowException(){

        expectedException.expect(InvalidNumberException.class);
        expectedException.expectMessage("negatives not allowed : [-3]");
        stringCalculator.add("-3");
    }

    @Test
    public void testNegativeNoShouldThrowExceptionWithNegativeValues(){

        expectedException.expect(InvalidNumberException.class);
        expectedException.expectMessage("negatives not allowed : [-3, -2, -5]");
        stringCalculator.add("-3,-2,-5");
    }

    @Test
    public void testShouldIgnoreAddWhenNumberGreaterThan1000(){

        String numbers = "//;\n1007;9;5";
        assertEquals(14, stringCalculator.add(numbers));
    }

    @Test
    public void testDelimiterOfAnyLength(){

        String numbers = "//***\n1007***9***5";
        assertEquals(14, stringCalculator.add(numbers));
    }

    @Test
    public void testMultipleDelimiterOfAnyLength(){

        String numbers = "//***|%%\n111***9%%5%%15";
        assertEquals(140, stringCalculator.add(numbers));
    }

    @Test
    public void testAllPossibleScenerios(){

        String numbers = "//***|%%|;\n111***9%%5%%15***3\n5;1100;12";
        assertEquals(160, stringCalculator.add(numbers));
    }

    @Test
    public void testAllPossibleNegativeScenerios(){

        expectedException.expect(InvalidNumberException.class);
        expectedException.expectMessage("negatives not allowed : [-3, -5, -12]");
        String numbers = "//***|%%|;\n111***9%%5%%15***-3\n-5;1100;-12";
        stringCalculator.add(numbers);
    }
}
