package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: percentage-off promotion. */
public class K2533109_PercentageDiscount implements K2533109_DiscountStrategy {

    private final double percent; // e.g. 10 means 10% off

    public K2533109_PercentageDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal - (subtotal * percent / 100.0);
    }

    @Override
    public String getName() {
        return percent + "% Off";
    }
}
