package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {


    @Query("SELECT s FROM StatusEntity s where s.id = :id")
    public StatusEntity getById(@Param("id") Integer id);
}