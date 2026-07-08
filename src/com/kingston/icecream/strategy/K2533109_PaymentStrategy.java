package com.kingston.icecream.strategy;

/**
 * STRATEGY PATTERN - Strategy (payment).
 *
 * Each payment method is an interchangeable algorithm. The order holds a
 * reference to whichever strategy the customer chose and simply calls pay();
 * it never needs to know HOW the payment is processed.
 */
public interface K2533109_PaymentStrategy {
    /** Mock-processes the payment. Returns true on success. */
    boolean pay(double amount);

    String getName();
}
