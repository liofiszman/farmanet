package com.ues21.farmanet.service;

import com.ues21.farmanet.model.OutputEntity;
import com.ues21.farmanet.model.OutputItemEntity;
import com.ues21.farmanet.model.ReasonEntity;
import com.ues21.farmanet.repository.OutputItemRepository;
import com.ues21.farmanet.repository.OutputRepository;
import com.ues21.farmanet.repository.ReasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutputService {

    private OutputRepository outputRepository;
    private ReasonRepository reasonRepository;
    private OutputItemRepository outputItemRepository;
    public OutputService(OutputRepository outputRepository, ReasonRepository reasonRepository, OutputItemRepository outputItemRepository) {
        this.outputRepository = outputRepository;
        this.reasonRepository = reasonRepository;
        this.outputItemRepository = outputItemRepository;

    }

    public List<OutputEntity> findAll() {
        return outputRepository.findAll();
    }

    public List<ReasonEntity> getReasons() {
        return reasonRepository.findAll();
    }

    public void save(OutputEntity output){
      OutputEntity outputEntity = outputRepository.saveAndFlush(output);
      for(OutputItemEntity item : outputEntity.getOutputItemsById()){
          item.setOutputByOutputId(outputEntity);
      }
      outputItemRepository.saveAll(outputEntity.getOutputItemsById());
      outputItemRepository.flush();
    }
}
