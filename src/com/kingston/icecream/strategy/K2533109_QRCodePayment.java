package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: QR-code based payment. */
public class K2533109_QRCodePayment implements K2533109_PaymentStrategy {

    private final String reference;

    public K2533109_QRCodePayment(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Generated QR code [%s]; received $%.2f%n", reference, amount);
        return true; // mocked
    }

    @Override
    public String getName() {
        return "QR Code (" + reference + ")";
    }
}
