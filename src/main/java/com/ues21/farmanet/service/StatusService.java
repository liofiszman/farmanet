package com.ues21.farmanet.service;

import com.ues21.farmanet.model.StatusEntity;
import com.ues21.farmanet.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private StatusRepository statusRepository;


    public StatusService(StatusRepository StatusRepository) {
        this.statusRepository = StatusRepository;
    }

    public List<StatusEntity> findAll() {
        return statusRepository.findAll();
    }

    public StatusEntity get(Integer id) {
        return statusRepository.getById(id);
    }


}
