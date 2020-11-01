package com.wave.greenboxrest.model.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.model.Position;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SessionSummary {

    private final Collection<SummaryElement> collectedItems;

    private SessionSummary(Collection<SummaryElement> collectedItems) {
        this.collectedItems = collectedItems;
    }

    public static SessionSummary from(Collection<Order> orders) {
        final Map<Item, SummaryElement> itemMap = new HashMap<>();
        for (Order order : orders) {
            for (Position position : order.getPositions()) {
                Item item = position.getItem();
                if (itemMap.containsKey(item)) {
                    var element = itemMap.get(item);
                    element.add(position);
                } else {
                    var element = new SummaryElement(position);
                    itemMap.put(item, element);
                }
            }
        }
        return new SessionSummary(itemMap.values());
    }

    @JsonProperty("totalPrice")
    public double calculateTotalPrice(){
        return collectedItems
                .stream()
                .map(SummaryElement::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow()
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}