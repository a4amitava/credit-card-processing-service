package com.sapient.card.service;

import com.sapient.card.model.CardDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static com.sapient.card.validation.ValidationMessages.INVALID_CARD_NUMBER;
import static com.sapient.card.validation.ValidationMessages.PRINTABLE_CARD_NAME_IS_MANDATORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidatorServiceTest {
    @InjectMocks
    private ValidatorService validatorService;

    @Test
    void testNoErrorMessageForValidCardDetails() {
        CardDetails sampleCardDetails = CardDetails.builder().cardNumber(125L).cardLimit(BigDecimal.ONE).printableName("CardHolderName").build();
        Set<String> errorMessages = validatorService.apply(sampleCardDetails);
        assertTrue(errorMessages.isEmpty());
    }

    @Test
    void testErrorMessagesForInValidCardDetails() {
        CardDetails sampleCardDetails = CardDetails.builder().cardNumber(1251L).cardLimit(BigDecimal.ONE).build();
        Set<String> errorMessages = validatorService.apply(sampleCardDetails);
        assertFalse(errorMessages.isEmpty());
        assertThat(errorMessages).containsAll(Set.of(INVALID_CARD_NUMBER, PRINTABLE_CARD_NAME_IS_MANDATORY));
    }
}