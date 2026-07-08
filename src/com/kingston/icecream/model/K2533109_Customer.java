package com.kingston.icecream.model;

/** A stall customer. Demonstrates encapsulation: all fields are private. */
public class K2533109_Customer {
    private final String customerId;
    private final String name;
    private final String contact;

    public K2533109_Customer(String customerId, String name, String contact) {
        this.customerId = customerId;
        this.name = name;
        this.contact = contact;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}
