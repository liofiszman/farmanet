package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    @Query("select c from StockEntity c " +
            "where concat(c.itemByItemId.id,'') like lower(concat(:searchTerm, '%')) " +
            "or lower(c.itemByItemId.name) like lower(concat(:searchTerm, '%'))") //
    List<StockEntity> search(@Param("searchTerm") String searchTerm); //

    @Query("select c from StockEntity c " +
            "where concat(c.itemByItemId.id,'') like lower(concat(:searchTerm, '%'))") //
    StockEntity searchById(@Param("searchTerm") String searchTerm); //


    @Query("select c from StockEntity c " +
            "where (concat(c.itemByItemId.id,'') like lower(concat(:searchTerm, '%')) " +
            "or lower(c.itemByItemId.name) like lower(concat(:searchTerm, '%')))" +
            "and c.locationByLocationId.id = :locationTerm") //
    List<StockEntity> searchByLocation(@Param("searchTerm") String searchTerm, @Param("locationTerm")int locationTerm); //

    @Query("select c from StockEntity c " +
            "where (concat(c.itemByItemId.id,'') like lower(concat(:searchTerm, '%')) " +
            "or lower(c.itemByItemId.name) like lower(concat(:searchTerm, '%')))" +
            "and c.locationByLocationId.id = :locationTerm") //
    StockEntity getByLocation(@Param("searchTerm") String searchTerm, @Param("locationTerm")int locationTerm); //



    @Query("select c from StockEntity c " +
            "where c.locationByLocationId.id = :locationTerm") //
    List<StockEntity> findAllByLocationId(@Param("locationTerm") int locationTerm); //


    @Query("select c from StockEntity c " +
            "where c.itemByItemId.id = :itemId and c.locationByLocationId.id = :locationId")//
    StockEntity getByItemIdAndLocation(@Param("itemId") int itemId, @Param("locationId") int locationId);//

}