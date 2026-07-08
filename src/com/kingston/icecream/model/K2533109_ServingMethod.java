package com.kingston.icecream.model;

/** How the customer wants to receive the order. Drives the order state machine. */
public enum K2533109_ServingMethod {
    DINE_IN("Dine In"),
    PICKUP("Pickup"),
    DELIVERY("Delivery");

    private final String label;

    K2533109_ServingMethod(String label) { this.label = label; }

    public String getLabel() { return label; }
}
