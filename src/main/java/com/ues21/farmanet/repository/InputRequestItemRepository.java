package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.InputRequestItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRequestItemRepository extends JpaRepository<InputRequestItemEntity, Long> {

}