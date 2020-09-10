package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.InputRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputRequestRepository extends JpaRepository<InputRequestEntity, Long> {

    @Query("select i from InputRequestEntity i where i.locationByLocationId.id = :location")//
    List<InputRequestEntity> findAllByLocation(@Param("location") int locationId);


    @Query("select i from InputRequestEntity i " +
            "where i.locationByLocationId.id = :location and i.id = :id")
    List<InputRequestEntity> findAllByLocationAndId(@Param("location") int locationId, @Param("id") int inputRequestId);

}