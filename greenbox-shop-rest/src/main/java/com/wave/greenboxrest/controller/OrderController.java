package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.dto.OrderCreateDto;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.model.summary.SessionSummary;
import com.wave.greenboxrest.model.summary.SummaryElement;
import com.wave.greenboxrest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    final String BASE_URI = "http://localhost:8080/orders";

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @GetMapping
    public ResponseEntity<?> getNotCompletedOrders() {
        return ResponseEntity.ok(
                orderService.getNotCompleted());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try {
            var order = orderService.getOrder(id);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order with a given id was not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDto orderDto) {
        try {
            var order = orderService.createOrder(orderDto);
            String uri = String.format(BASE_URI + "/%d", order.getId());
            return ResponseEntity.created(URI.create(uri)).body(order);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable("id") Long id) {
        try {
            Order order = orderService.completeOrder(id);
            String uri = String.format(BASE_URI + "/%d", id);
            return ResponseEntity.created(URI.create(uri)).body(order);
        } catch (EntityNotFoundException e) {
            var message = String.format("Order with given id: %d was not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(message);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("The order has been deleted.");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Order with a given id was not found.");
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getOrdersSummary() {
        var orders = orderService.getNotCompleted();
        if (orders.isEmpty()) {
            return ResponseEntity.ok("No new orders.");
        }
        var summary = SessionSummary.from(orders);
        return ResponseEntity.ok(summary);
    }

}
