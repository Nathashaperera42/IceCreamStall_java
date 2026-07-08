package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: no promotion. */
public class K2533109_NoDiscount implements K2533109_DiscountStrategy {

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal;
    }

    @Override
    public String getName() {
        return "No Discount";
    }
}
