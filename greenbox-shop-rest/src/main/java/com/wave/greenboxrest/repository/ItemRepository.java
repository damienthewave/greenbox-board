package com.wave.greenboxrest.repository;

import com.wave.greenboxrest.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> getAllByIsAvailable(boolean isAvailable);
}
