package com.ues21.farmanet.service;

import com.ues21.farmanet.model.ReasonEntity;
import com.ues21.farmanet.repository.ReasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReasonService {

    private ReasonRepository reasonRepository;

    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    public List<ReasonEntity> findAll() {
        return reasonRepository.findAll();
    }

}
