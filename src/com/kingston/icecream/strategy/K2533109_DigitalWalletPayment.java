package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: digital wallet (e.g. PayPal / Apple Pay). */
public class K2533109_DigitalWalletPayment implements K2533109_PaymentStrategy {

    private final String walletId;

    public K2533109_DigitalWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing wallet payment of $%.2f via %s%n", amount, walletId);
        return true; // mocked
    }

    @Override
    public String getName() {
        return "Digital Wallet (" + walletId + ")";
    }
}
