package com.kingston.icecream.model;

/**
 * The container the ice cream is served in. Each base carries its own price.
 * Modelled as an enum because the set of containers is fixed and known at
 * compile time, which is a clean, type-safe Java idiom.
 */
public enum K2533109_BaseType {
    CONE("Waffle Cone", 1.50),
    CUP("Paper Cup", 1.20);

    private final String label;
    private final double price;

    K2533109_BaseType(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public double getPrice() {
        return price;
    }
}
