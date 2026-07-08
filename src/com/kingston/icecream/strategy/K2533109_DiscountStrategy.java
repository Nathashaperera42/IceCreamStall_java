package com.kingston.icecream.strategy;

/**
 * STRATEGY PATTERN - Strategy (promotional discount).
 *
 * A second, independent family of interchangeable algorithms used to apply
 * promotions. Keeping discounts as strategies means new promotions can be
 * added without touching the Order class (Open/Closed Principle).
 */
public interface K2533109_DiscountStrategy {
    /** @return the discounted total for the given subtotal. */
    double applyDiscount(double subtotal);

    String getName();
}
