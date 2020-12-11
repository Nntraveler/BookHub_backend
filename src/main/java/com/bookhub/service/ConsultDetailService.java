package com.bookhub.service;

import com.bookhub.dao.ConsultDetailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultDetailService {
    @Autowired
    ConsultDetailDAO consultDetailDAO;
}
