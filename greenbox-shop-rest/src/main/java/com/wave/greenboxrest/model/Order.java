package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50, message = "Must be 3-50 characters long")
    private String personName;

    @Size(min = 5, message = "Your address is too short")
    private String address;

    @Size(min = 9, max = 9, message = "Must be 9 digits long")
    private String phoneNumber;

    private String orderComment;

    @Temporal(TemporalType.TIMESTAMP)
    private final Date createdOn = new Date();

    private boolean isCompleted = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Position> positions = new HashSet<>();

    @JsonProperty("totalPrice")
    public Double calculateTotalPrice(){
        return positions.stream()
                .map(Position::calculateSubtotal)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .orElseThrow()
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}

