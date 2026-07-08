package com.kingston.icecream.model;

/** Ice-cream flavours offered by the stall, each with a per-scoop price. */
public enum K2533109_Flavor {
    VANILLA("Vanilla", 2.00),
    CHOCOLATE("Chocolate", 2.20),
    STRAWBERRY("Strawberry", 2.20),
    MANGO("Mango", 2.50),
    PISTACHIO("Pistachio", 2.80),
    COOKIES_AND_CREAM("Cookies & Cream", 2.60);

    private final String label;
    private final double price;

    K2533109_Flavor(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() { return label; }
    public double getPrice() { return price; }
}
