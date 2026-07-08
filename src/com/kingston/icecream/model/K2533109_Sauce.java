package com.kingston.icecream.model;

/** Liquid sauces that can be drizzled on an ice cream (used by the Decorator pattern). */
public enum K2533109_Sauce {
    CHOCOLATE("Chocolate Sauce", 0.60),
    CARAMEL("Caramel Sauce", 0.60),
    STRAWBERRY("Strawberry Sauce", 0.50),
    MANGO("Mango Sauce", 0.70);

    private final String label;
    private final double price;

    K2533109_Sauce(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() { return label; }
    public double getPrice() { return price; }
}
