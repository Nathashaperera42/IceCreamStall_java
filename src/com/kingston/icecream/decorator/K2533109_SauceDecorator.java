package com.kingston.icecream.decorator;

import com.kingston.icecream.model.K2533109_Sauce;

/** DECORATOR PATTERN - Concrete Decorator: drizzles a sauce. */
public class K2533109_SauceDecorator extends K2533109_IceCreamDecorator {

    private final K2533109_Sauce sauce;

    public K2533109_SauceDecorator(K2533109_IceCreamComponent inner, K2533109_Sauce sauce) {
        super(inner);
        this.sauce = sauce;
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + " + " + sauce.getLabel();
    }

    @Override
    public double getCost() {
        return inner.getCost() + sauce.getPrice();
    }
}
