package com.ues21.farmanet.service;

import com.ues21.farmanet.model.ItemEntity;
import com.ues21.farmanet.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;


    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemEntity> findAll() {
        return itemRepository.findAll();
    }

    public ItemEntity get(Integer id) {
        return itemRepository.getById(id);
    }


}
