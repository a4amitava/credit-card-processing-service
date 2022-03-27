package com.sapient.card.rest;
import com.sapient.card.exception.ApplicationException;
import com.sapient.card.model.CardDetails;
import com.sapient.card.service.CardService;
import com.sapient.card.service.ValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.sapient.card.exception.ErrorCodes.DB_QUERY_TIME_OUT;
import static com.sapient.card.validation.ValidationMessages.INVALID_CARD_NUMBER;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerTest {

    @LocalServerPort
    private int port;
    private final String baseUrl = "http://localhost:%s/api/cards";
    @MockBean
    private CardService cardService;
    @MockBean
    private ValidatorService validatorService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final CardDetails sampleCardDetails = CardDetails.builder().cardNumber(125L).cardLimit(BigDecimal.ONE).printableName("CardHolderName").build();


    @Test
    void givenAValidCardDetailsVerifyCardHasBeenAddedSuccessfully() {
        when(validatorService.apply(sampleCardDetails)).thenReturn(emptySet());
        doNothing().when(cardService).addCard(sampleCardDetails);
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format(baseUrl, port), sampleCardDetails, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // ensure card service was invoked only once
        verify(cardService, times(1)).addCard(sampleCardDetails);
        verify(validatorService, times(1)).apply(sampleCardDetails);
    }

    @Test
    void givenInvalidCardNumberVerifyErrorIsReturnedWithBadRequestStatus() {
        when(validatorService.apply(sampleCardDetails)).thenReturn(Set.of(INVALID_CARD_NUMBER));
        ResponseEntity<String> response = testRestTemplate.postForEntity(String.format(baseUrl, port), sampleCardDetails, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(validatorService, times(1)).apply(sampleCardDetails);
        assertTrue(requireNonNull(response.getBody()).contains(INVALID_CARD_NUMBER));
    }

    @Test
    void verifyWhenNoCardIsPresentInSystemThenEmptyListIsReturned() {
        // Day 1 use case when there is no card has been added
        when(cardService.getAllCards()).thenReturn(emptyList());
        ResponseEntity<List<CardDetails>> responseEntity =
                testRestTemplate.exchange(String.format(baseUrl, port),
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(requireNonNull(responseEntity.getBody()).isEmpty());
    }

    @Test
    void verifyWhenAllTheStoredCardAreReturned() {
        when(cardService.getAllCards()).thenReturn(List.of(sampleCardDetails));
        ResponseEntity<List<CardDetails>> responseEntity =
                testRestTemplate.exchange(String.format(baseUrl, port),
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(List.of(sampleCardDetails), requireNonNull(responseEntity.getBody()));
        // verify card service was invoked
        verify(cardService, times(1)).getAllCards();
    }

    @Test
    void verifyWhenDataBaseThrowsQueryTimeoutThenInternalServerErrorIsThrown() {
        when(cardService.getAllCards()).thenThrow(new ApplicationException(DB_QUERY_TIME_OUT, new Throwable("TimedOut")));
        ResponseEntity<String> response =
                testRestTemplate.exchange(String.format(baseUrl, port),
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // verify card service was invoked
        verify(cardService, times(1)).getAllCards();
        assertTrue(requireNonNull(response.getBody()).contains(DB_QUERY_TIME_OUT.getDescription()));
    }
}