package com.sapient.test.service;


import com.sapient.test.model.CardDetails;

import java.util.List;


public interface CardService {

    void addCard(CardDetails cardDetails);

    List<CardDetails> getAllCards();
}
