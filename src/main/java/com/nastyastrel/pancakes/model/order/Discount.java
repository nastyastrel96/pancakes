package com.nastyastrel.pancakes.model.order;

public enum Discount {
    NO_DISCOUNT(1),
    FIVE_PERCENT(0.95),
    TEN_PERCENT(0.9),
    FIFTEEN_PERCENT_FOR_HEALTHY_PANCAKE(0.85);

    private final double value;

    Discount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
