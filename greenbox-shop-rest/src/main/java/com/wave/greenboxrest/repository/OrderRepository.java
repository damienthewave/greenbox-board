package com.wave.greenboxrest.repository;

import com.wave.greenboxrest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByIsCompleted(boolean completed);

}
