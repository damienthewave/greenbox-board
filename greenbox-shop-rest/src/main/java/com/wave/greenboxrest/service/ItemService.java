package com.wave.greenboxrest.service;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public List<Item> getAvailableItems(){
        return itemRepository.getAllByIsAvailable(true);
    }

    public Item getItem(Long id){
        return itemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Item createItem(Item item){
        return itemRepository.saveAndFlush(item);
    }

    public Item changeAvailability(Long id, Boolean isAvailable){
        Item item = itemRepository.findById(id).
                orElseThrow(EntityNotFoundException::new);
        item.setAvailable(isAvailable);
        return itemRepository.saveAndFlush(item);
    }
}
