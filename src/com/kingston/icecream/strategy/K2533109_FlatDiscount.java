package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: flat amount-off promotion. */
public class K2533109_FlatDiscount implements K2533109_DiscountStrategy {

    private final double amountOff;

    public K2533109_FlatDiscount(double amountOff) {
        this.amountOff = amountOff;
    }

    @Override
    public double applyDiscount(double subtotal) {
        return Math.max(0, subtotal - amountOff);
    }

    @Override
    public String getName() {
        return "$" + amountOff + " Off";
    }
}
