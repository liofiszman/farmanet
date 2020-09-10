package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.OutputItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputItemRepository extends JpaRepository<OutputItemEntity, Long> {
}