package com.sapient.test.rest;

import com.sapient.test.exception.ApplicationException;
import com.sapient.test.exception.CardValidationException;
import com.sapient.test.model.CardDetails;
import com.sapient.test.service.CardService;
import com.sapient.test.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.sapient.test.validation.ValidationMessages.NEW_CARD_BALANCE_MESSAGE;
import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;
    private final ValidatorService validatorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> add(@RequestBody CardDetails cardDetails) {
        Long cardNumber = cardDetails.getCardNumber();
        log.info("Received request to add card for #{}", cardNumber);
        Set<String> validationErrorMessages = validatorService.apply(cardDetails);
        //TODO: to rethink if there is a better option to validate a new card
        if (isNull(cardDetails.getBalance()) || cardDetails.getBalance().intValue() != 0) {
            validationErrorMessages.add(NEW_CARD_BALANCE_MESSAGE);
        }
        if (!validationErrorMessages.isEmpty()) {
            log.warn("Validation has failed for the provided payload; ErrorMessages {}", validationErrorMessages);
            throw new CardValidationException(validationErrorMessages);
        }
        cardService.addCard(cardDetails);
        return ResponseEntity.ok(String.format("Card #%s added Successfully", cardNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','VIEWER')")
    public List<CardDetails> fetchAllCards() {
        log.info("Attempting to retrieve all card details");
        return cardService.getAllCards();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CardValidationException.class)
    public Set<String> handleConstraintsValidationExceptions(
            CardValidationException ex) {
        return ex.getErrorMessages();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public Set<String> handleApplicationInternalExceptions(
            ApplicationException ex) {
        return Set.of(ex.getErrorCode().getDescription());
    }
}
