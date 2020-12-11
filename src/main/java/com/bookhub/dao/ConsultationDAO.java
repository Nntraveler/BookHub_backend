package com.bookhub.dao;

import com.bookhub.model.Consultation;
import org.springframework.data.repository.CrudRepository;

public interface ConsultationDAO extends CrudRepository<Consultation, String> {

}

