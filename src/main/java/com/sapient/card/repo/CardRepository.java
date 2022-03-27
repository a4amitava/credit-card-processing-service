package com.sapient.card.repo;


import com.sapient.card.model.CardDetails;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<CardDetails, Long> {

}
