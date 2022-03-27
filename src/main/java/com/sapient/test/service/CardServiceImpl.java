package com.sapient.test.service;

import com.sapient.test.exception.ApplicationException;
import com.sapient.test.model.CardDetails;
import com.sapient.test.repo.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.sapient.test.exception.ErrorCodes.DB_PERSISTENCE_ERROR;
import static com.sapient.test.exception.ErrorCodes.DB_QUERY_TIME_OUT;
import static com.sapient.test.exception.ErrorCodes.UNKNOWN_DATA_BASE_ERROR;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public void addCard(CardDetails cardDetails) {
        try {
            cardRepository.save(cardDetails);
            log.info("Card # {} has been saved successfully.", cardDetails.getCardNumber());
        } catch (PersistenceException ex) {
            log.error("A  Data Persistence Exception occurred while saving the card details in Data Base, please check the service log, and/or stacktrace", ex);
            throw new ApplicationException(DB_PERSISTENCE_ERROR, ex);
        } catch (Exception ex) {
            log.error("An unknown error occurred while saving the card details in DB, please check the service log, and/or stacktrace", ex);
            throw new ApplicationException(UNKNOWN_DATA_BASE_ERROR, ex);
        }
    }

    @Override
    public List<CardDetails> getAllCards() {
        try {
            return StreamSupport.stream(cardRepository.findAll().spliterator(), false)
                    .collect(toList());
        } catch (QueryTimeoutException queryTimeoutException) {
            log.error("Query timeout occurred while fetching all card Details");
            throw new ApplicationException(DB_QUERY_TIME_OUT, queryTimeoutException);
        }
    }
}
