package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.OutputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRepository extends JpaRepository<OutputEntity, Long> {
}