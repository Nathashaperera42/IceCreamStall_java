package com.kingston.icecream.decorator;

/**
 * DECORATOR PATTERN - Component.
 *
 * Common interface shared by the concrete base ice cream and every decorator
 * (toppings, sauces, packaging). Because decorators implement the SAME
 * interface as the thing they wrap, any number of them can be stacked and the
 * client still sees a single IceCreamComponent.
 */
public interface K2533109_IceCreamComponent {
    String getDescription();
    double getCost();
}
