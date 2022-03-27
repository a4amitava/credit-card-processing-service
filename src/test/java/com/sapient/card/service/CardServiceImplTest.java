package com.sapient.card.service;

import com.sapient.card.exception.ApplicationException;
import com.sapient.card.model.CardDetails;
import com.sapient.card.repo.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import java.math.BigDecimal;
import java.util.List;

import static com.sapient.card.exception.ErrorCodes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private static final CardDetails sampleCardDetails = CardDetails.builder().cardNumber(125L).cardLimit(BigDecimal.ONE).printableName("CardHolderName").build();

    @Test
    void testAddCardDetailsSavedSuccessfully() {
        when(cardRepository.save(sampleCardDetails)).thenReturn((sampleCardDetails));
        cardService.addCard(sampleCardDetails);
        verify(cardRepository, times(1)).save(sampleCardDetails);
    }

    @Test
    void verifyWhenDataBasePersistenceErrorOccurredThenApplicationExceptionIsThrown() {
        when(cardRepository.save(sampleCardDetails)).thenThrow(new PersistenceException("Buffer Issue"));
        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> cardService.addCard(sampleCardDetails));
        assertEquals(DB_PERSISTENCE_ERROR, applicationException.getErrorCode());
    }

    @Test
    void verifyErrorCodeForUnknownDBExceptionWhileSavingCardDetails() {
        when(cardRepository.save(sampleCardDetails)).thenThrow(new RuntimeException("Unknown Runtime Error"));
        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> cardService.addCard(sampleCardDetails));
        assertEquals(UNKNOWN_DATA_BASE_ERROR, applicationException.getErrorCode());
    }

    @Test
    void testGetAllCardDetailsReturnsListOfCards() {
        // when the return list is empty - Day 1 - when no card is exist i system
        when(cardRepository.findAll()).thenReturn(List.of());
        assertTrue(cardService.getAllCards().isEmpty());
        // when list of cards are returned
        final CardDetails anotherCardDetails = CardDetails.builder().cardNumber(123L).cardLimit(BigDecimal.ONE).printableName("CardHolderName").build();
        when(cardRepository.findAll()).thenReturn((List.of(sampleCardDetails, anotherCardDetails)));
        assertEquals(2, cardService.getAllCards().size());
        // check the returned cards having the expected list of card number
        assertTrue(cardService.getAllCards().stream()
                .map(CardDetails::getCardNumber)
                .allMatch(cardNumber -> List.of(sampleCardDetails.getCardNumber(), anotherCardDetails.getCardNumber()).contains(cardNumber)));
    }

    @Test
    void verifyQueryTimeOutErrorCode() {
        when(cardRepository.findAll()).thenThrow(new QueryTimeoutException("Many records - query timed out"));
        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> cardService.getAllCards());
        assertEquals(DB_QUERY_TIME_OUT, applicationException.getErrorCode());
    }
}