package com.sapient.card.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {
    public static final String INVALID_CARD_NUMBER = "Invalid Card Number";
    public static final String PRINTABLE_CARD_NAME_IS_MANDATORY = "Printable Card Name is mandatory";
    public static final String CARD_NUMBER_MUST_BE_OF_2_DIGITS_OR_MORE = "Card Number must be of 2 digits or more";
    public static final String NEW_CARD_BALANCE_MESSAGE = "New card to start with a £0 balance";
}
