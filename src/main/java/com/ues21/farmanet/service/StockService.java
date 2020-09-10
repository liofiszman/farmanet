package com.ues21.farmanet.service;

import com.ues21.farmanet.model.InputRequestItemEntity;
import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.model.OutputItemEntity;
import com.ues21.farmanet.model.StockEntity;
import com.ues21.farmanet.repository.StockRepository;
import com.ues21.farmanet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class StockService {

    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockEntity> findAll() {
        return stockRepository.findAll();
    }


    public List<StockEntity> findAll(String serchFilter) {
        if (serchFilter == null || serchFilter.isEmpty()) {
            return stockRepository.findAll();
        } else {
            return stockRepository.search(serchFilter);
        }
    }

    public List<StockEntity> findAll(String searchFilter, LocationEntity locationEntity) {
        if (searchFilter == null || searchFilter.isEmpty()) {
            return stockRepository.findAllByLocationId(locationEntity.getId());
        } else {
            return stockRepository.searchByLocation(searchFilter,locationEntity.getId());
        }
    }

    public StockEntity findById(String serchFilter) {
            return stockRepository.searchById(serchFilter);
    }

    public StockEntity getByLocation(String searchFilter,LocationEntity location) {
        return stockRepository.getByLocation(searchFilter,location.getId());
    }

    public void discountStocks(List<OutputItemEntity> outputItemEntityList, LocationEntity location){

        for(OutputItemEntity outputItem : outputItemEntityList){
            StockEntity stockEntity = this.stockRepository.getByItemIdAndLocation(outputItem.getItemByItemId().getId(),location.getId());
            stockEntity.setCurrentStock(stockEntity.getCurrentStock() - outputItem.getQuantity());
            this.stockRepository.save(stockEntity);
        }

        this.stockRepository.flush();

    }

    public void addStock(InputRequestItemEntity input,LocationEntity location){


       StockEntity stock =  stockRepository.getByItemIdAndLocation(input.getItemByItemId().getId(), location.getId());
       if(stock != null){
           stock.setCurrentStock(stock.getCurrentStock() + input.getQuantity());
           stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
           stockRepository.saveAndFlush(stock);
       }else{
           StockEntity stockEntity = new StockEntity();
           stockEntity.setCurrentStock(input.getQuantity());
           stockEntity.setItemByItemId(input.getItemByItemId());
           stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
           stock.setBatchNumber("");
           stock.setExpirationDate(new Date(System.currentTimeMillis()));
           stockRepository.saveAndFlush(stock);
       }


    }
}
