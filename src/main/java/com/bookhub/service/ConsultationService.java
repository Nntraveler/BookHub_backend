package com.bookhub.service;

import com.bookhub.dao.ConsultationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationDAO consultationDAO;
}
