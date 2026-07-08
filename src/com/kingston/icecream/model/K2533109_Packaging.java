package com.kingston.icecream.model;

/** Packaging options that wrap the finished product (used by the Decorator pattern). */
public enum K2533109_Packaging {
    STANDARD("Standard Wrap", 0.00),
    ECO_FRIENDLY("Eco-Friendly Box", 0.80),
    GIFT_BOX("Gift Box", 1.50);

    private final String label;
    private final double price;

    K2533109_Packaging(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() { return label; }
    public double getPrice() { return price; }
}
