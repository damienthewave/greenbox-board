package com.wave.greenboxrest.service;

import com.wave.greenboxrest.dto.OrderCreateDto;
import com.wave.greenboxrest.dto.PositionCreateDto;
import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.model.Position;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    public List<Order> getNotCompleted(){
        return orderRepository.getAllByIsCompleted(false);
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order createOrder(OrderCreateDto orderDto){
        Set<Position> positions = new HashSet<>();
        var order = Order.builder()
                .personName(orderDto.personName)
                .address(orderDto.address)
                .phoneNumber(orderDto.phoneNumber)
                .orderComment(orderDto.orderComment)
                .build();
        for(PositionCreateDto positionDto: orderDto.positions){
            Item item = itemRepository.findById(positionDto.itemId)
                    .orElseThrow(EntityNotFoundException::new);
            if(!item.isAvailable()){
                throw new EntityNotFoundException("Item with id " +
                        positionDto.itemId + " is not available");
            }
            var position = new Position(order, item, positionDto.amount);
            positions.add(position);
        }
        order.setPositions(positions);
        return orderRepository.saveAndFlush(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public Order completeOrder(Long id){
        var order = orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        order.setCompleted(true);
        return orderRepository.saveAndFlush(order);
    }

}
