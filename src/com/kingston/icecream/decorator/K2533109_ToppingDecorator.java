package com.kingston.icecream.decorator;

import com.kingston.icecream.model.K2533109_Topping;

/** DECORATOR PATTERN - Concrete Decorator: adds a solid topping. */
public class K2533109_ToppingDecorator extends K2533109_IceCreamDecorator {

    private final K2533109_Topping topping;

    public K2533109_ToppingDecorator(K2533109_IceCreamComponent inner, K2533109_Topping topping) {
        super(inner);
        this.topping = topping;
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + " + " + topping.getLabel();
    }

    @Override
    public double getCost() {
        return inner.getCost() + topping.getPrice();
    }
}
