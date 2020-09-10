package com.ues21.farmanet.service;

import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationEntity> findAll() {
        return locationRepository.findAll();
    }


}
