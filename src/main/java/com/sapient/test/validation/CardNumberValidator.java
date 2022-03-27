package com.sapient.test.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.List;

import static com.sapient.test.validation.NumberUtils.calculateSumOfAllOddPlacesDigit;
import static com.sapient.test.validation.NumberUtils.calculateSumOfDoubleWeightedEvenPlacesDigit;
import static com.sapient.test.validation.NumberUtils.convertNumberToIntList;

/**
 * Validate the cardNumber using using Luhn 10.
 * For a given credit card number:
 * 1. Double the value of every other digit from right to left, beginning with the second to last digit.
 * 2. Add the digits of the results of Step 1 to the remaining digits in the credit card number.
 * 3. Sum the resulting values from all positions
 * 4.  If the total modulo 10 is equal to 0 then the number is valid according to the Luhn formula; else it is not valid.
 */

@Slf4j
public class CardNumberValidator implements ConstraintValidator<CardNumberValidationConstraint, Long> {

    @Override
    public boolean isValid(Long cardNumber, ConstraintValidatorContext constraintValidatorContext) {
        List<Integer> cardNumberAsList = convertNumberToIntList.apply(cardNumber);
        // reverse the elements and start from right -> left
        Collections.reverse(cardNumberAsList);
        // get Sum Of Double Weighted Even Places Digit - starting from second digit
        Integer sumOfEvenPlacesDigit = calculateSumOfDoubleWeightedEvenPlacesDigit(cardNumberAsList);
        // get all sum of odd places
        Integer sumOfOddPlaces = calculateSumOfAllOddPlacesDigit(cardNumberAsList);
        // add both even and odd sum
        int sumOfBothOddAndEvenPlaces = sumOfOddPlaces + sumOfEvenPlacesDigit;
        // check if divisible by mod 10
        return sumOfBothOddAndEvenPlaces % 10 == 0;
    }
}
