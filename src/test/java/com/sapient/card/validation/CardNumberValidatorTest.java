package com.sapient.card.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CardNumberValidatorTest {
    @InjectMocks
    private CardNumberValidator cardNumberValidator;
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @ParameterizedTest
    @ValueSource(longs = {123455, 4847352989263094L, 2221001234123450L, 79927398713L})
    void testVerifyValidCreditCard(Long validCardNumber) {
        boolean isValid = cardNumberValidator.isValid(validCardNumber, constraintValidatorContext);
        assertTrue(isValid);
    }

    @ParameterizedTest
    @ValueSource(longs = {4847352989263095L, 79927398715L, 10, 1})
    void testVerifyInValidCreditCard(Long invalidCardNumber) {
        assertFalse(cardNumberValidator.isValid(invalidCardNumber, constraintValidatorContext));
    }
}