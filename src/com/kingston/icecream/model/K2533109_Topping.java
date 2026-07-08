package com.kingston.icecream.model;

/** Solid extras that can be added to an ice cream (used by the Decorator pattern). */
public enum K2533109_Topping {
    SPRINKLES("Sprinkles", 0.50),
    NUTS("Crushed Nuts", 0.80),
    CHOC_CHIPS("Chocolate Chips", 0.70),
    GUMMY_BEARS("Gummy Bears", 0.60),
    WHIPPED_CREAM("Whipped Cream", 0.90),
    OREO("Oreo Crumbs", 0.80);

    private final String label;
    private final double price;

    K2533109_Topping(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() { return label; }
    public double getPrice() { return price; }
}
