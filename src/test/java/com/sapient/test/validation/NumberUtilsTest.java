package com.sapient.test.validation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.sapient.test.validation.NumberUtils.calculateSumOfAllOddPlacesDigit;
import static com.sapient.test.validation.NumberUtils.calculateSumOfDoubleWeightedEvenPlacesDigit;
import static com.sapient.test.validation.NumberUtils.convertNumberToIntList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberUtilsTest {

    @Test
    public void testConvertNumberToIntList() {
        List<Integer> lists = convertNumberToIntList.apply(123L);
        assertEquals(lists.size(), 3);
        assertTrue(IntStream.range(0, lists.size())
                .allMatch(index -> index + 1 == lists.get(index)));
    }

    @Test
    void getSumOfOddDigit() {
        List<Integer> cardNumbers = convertNumberToIntList.apply(12345L);
        Integer sumOfOddPosition = calculateSumOfAllOddPlacesDigit(cardNumbers);
        assertEquals(9, sumOfOddPosition);
        Integer[] anotherCardNumbers = {1, 2, 3, 4, 5, 0, 0};
        assertEquals(9, calculateSumOfAllOddPlacesDigit(Arrays.asList(anotherCardNumbers)));
    }

    @Test
    void getSumOfDoubleWeightedEvenPlacesValue() {
        Integer[] cardNumbers = {1, 2, 3, 4, 5};
        assertEquals(12, calculateSumOfDoubleWeightedEvenPlacesDigit(Arrays.asList(cardNumbers)));
        Integer[] anotherCardNumbers = {1, 2, 3, 4, 5, 6};
        // the sum should be = 2*2 + 4*2 + 6*2(1+2) = 15
        assertEquals(15, calculateSumOfDoubleWeightedEvenPlacesDigit(Arrays.asList(anotherCardNumbers)));
    }
}