package com.ues21.farmanet.service;

import com.ues21.farmanet.model.InputRequestEntity;
import com.ues21.farmanet.model.InputRequestItemEntity;
import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.model.StatusEntity;
import com.ues21.farmanet.repository.InputRequestItemRepository;
import com.ues21.farmanet.repository.InputRequestRepository;
import com.ues21.farmanet.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class InputRequestService {

    private InputRequestRepository inputRequestRepository;
    private InputRequestItemRepository inputRequestItemRepository;
    private StatusRepository statusRepository;

    @Autowired
    private StockService stockService;

    public InputRequestService(InputRequestRepository inputRequestRepository, InputRequestItemRepository inputRequestItemRepository, StatusRepository statusRepository) {
        this.inputRequestRepository = inputRequestRepository;
        this.inputRequestItemRepository =inputRequestItemRepository;
        this.statusRepository = statusRepository;
    }

    public List<InputRequestEntity> findAll() {
        return inputRequestRepository.findAll();
    }

    public List<InputRequestEntity> findAllbyLocation(LocationEntity location){

        return inputRequestRepository.findAllByLocation(location.getId());
    }


    public List<InputRequestEntity> findAllbyLocationAndId(LocationEntity location, Integer inputRequestId){

        if (inputRequestId == null) {
            return inputRequestRepository.findAllByLocation(location.getId());
        } else {
            return inputRequestRepository.findAllByLocationAndId(location.getId(), inputRequestId);
        }
    }

    public void save(InputRequestEntity input){
        InputRequestEntity inputRequestEntity = inputRequestRepository.saveAndFlush(input);
        for(InputRequestItemEntity item : inputRequestEntity.getInputRequestItemsById()){
            item.setInputRequestByInputRequestId(inputRequestEntity);
        }
        inputRequestItemRepository.saveAll(inputRequestEntity.getInputRequestItemsById());
        inputRequestItemRepository.flush();
    }

    public void updateStatus(InputRequestEntity input){

        input.setStatusByStatusId(setStatus(input.getInputRequestItemsById().stream().collect(toList())));
        inputRequestRepository.saveAndFlush(input);
        inputRequestItemRepository.saveAll(input.getInputRequestItemsById());
        inputRequestItemRepository.flush();

        //Add Delivered to Stock

        for(InputRequestItemEntity item : input.getInputRequestItemsById()){
            if(item.getStatusByStatusId().getId() == 4){
               stockService.addStock(item,input.getLocationByLocationId());
            }
        }

    }

    private StatusEntity setStatus(List<InputRequestItemEntity> inputList){

        StatusEntity selectStatus = null;

        Boolean equal = true;
        int index = 0;
        while(equal && index < inputList.size()){
            if(selectStatus == null){
                selectStatus = inputList.get(index).getStatusByStatusId();
            }else{
                if(selectStatus != inputList.get(index).getStatusByStatusId()){

                    switch (inputList.get(index).getStatusByStatusId().getId()){
                        case 3: //If at least one is Approved, is partially Aprovved
                            selectStatus = this.statusRepository.getById(5);
                            break;
                        case 4: //If at least one is Delivered, is partially delivered
                            selectStatus = this.statusRepository.getById(6);
                            equal = false;
                            break;
                        default:
                            if(selectStatus != this.statusRepository.getById(5))
                                     selectStatus = this.statusRepository.getById(1);
                            break;
                    }
                }
            }

            index++;
        }

        return selectStatus;
    }



}
