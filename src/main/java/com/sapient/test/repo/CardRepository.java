package com.sapient.test.repo;


import com.sapient.test.model.CardDetails;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<CardDetails, Long> {

}
