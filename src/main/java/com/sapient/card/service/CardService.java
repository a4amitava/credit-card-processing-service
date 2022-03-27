package com.sapient.card.service;


import com.sapient.card.model.CardDetails;

import java.util.List;


public interface CardService {

    void addCard(CardDetails cardDetails);

    List<CardDetails> getAllCards();
}
