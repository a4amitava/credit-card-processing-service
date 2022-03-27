package com.sapient.card.validation;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class NumberUtils {
    public static final Function<Long, List<Integer>> convertNumberToIntList = (cardNumber) -> String.valueOf(cardNumber).chars().mapToObj(Character::getNumericValue).collect(toList());

    public static Integer calculateSumOfAllOddPlacesDigit(List<Integer> numberList) {
        return IntStream.range(0, numberList.size())
                .filter(index -> index % 2 == 0)
                .map(numberList::get)
                .sum();
    }

    public static Integer calculateSumOfDoubleWeightedEvenPlacesDigit(List<Integer> numberList) {
        return IntStream.range(1, numberList.size())
                .filter(index -> index % 2 != 0)
                .map(numberList::get)
                .map(doubleWeightedValue::apply)
                .sum();
    }

    // Double the value and if the double value is greater than 9, then sum each digit, else return the value
    private final static UnaryOperator<Integer> doubleWeightedValue = value -> {
        Integer doubleWeightedValue = value * 2;
        if (doubleWeightedValue < 10) {
            return doubleWeightedValue;
        }
        String stringFormattedDoubleDigit = String.valueOf(doubleWeightedValue);
        return Character.getNumericValue(stringFormattedDoubleDigit.charAt(0)) + Character.getNumericValue(stringFormattedDoubleDigit.charAt(1));
    };
}

