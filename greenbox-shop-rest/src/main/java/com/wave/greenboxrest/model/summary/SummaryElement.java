package com.wave.greenboxrest.model.summary;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.model.Position;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SummaryElement {

    private BigDecimal amount;
    private BigDecimal price;
    private final Item item;

    SummaryElement(Position position) {
        this.amount = BigDecimal.valueOf(position.getAmount());
        this.price = BigDecimal.valueOf(position.calculateSubtotal());
        this.item = position.getItem();
    }

    void add(Position position) {
        amount = amount.add(BigDecimal.valueOf(position.getAmount()));
        price = price.add(BigDecimal.valueOf(position.calculateSubtotal()));
    }

}
