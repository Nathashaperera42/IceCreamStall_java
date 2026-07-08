package com.kingston.icecream.strategy;

/** STRATEGY PATTERN - Concrete Strategy: credit / debit card. */
public class K2533109_CardPayment implements K2533109_PaymentStrategy {

    private final String cardHolder;
    private final String maskedNumber;

    public K2533109_CardPayment(String cardHolder, String cardNumber) {
        this.cardHolder = cardHolder;
        this.maskedNumber = mask(cardNumber);
    }

    private String mask(String number) {
        String digits = number.replaceAll("\\s", "");
        if (digits.length() < 4) return "****";
        return "**** **** **** " + digits.substring(digits.length() - 4);
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing card payment of $%.2f for %s (%s)%n",
                amount, cardHolder, maskedNumber);
        return true; // mocked authorisation
    }

    @Override
    public String getName() {
        return "Card (" + maskedNumber + ")";
    }
}
