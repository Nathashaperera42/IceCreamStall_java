package com.kingston.icecream.decorator;

import com.kingston.icecream.model.K2533109_Packaging;

/** DECORATOR PATTERN - Concrete Decorator: wraps the product in packaging. */
public class K2533109_PackagingDecorator extends K2533109_IceCreamDecorator {

    private final K2533109_Packaging packaging;

    public K2533109_PackagingDecorator(K2533109_IceCreamComponent inner, K2533109_Packaging packaging) {
        super(inner);
        this.packaging = packaging;
    }

    @Override
    public String getDescription() {
        if (packaging.getPrice() == 0.0) {
            return inner.getDescription();
        }
        return inner.getDescription() + " [" + packaging.getLabel() + "]";
    }

    @Override
    public double getCost() {
        return inner.getCost() + packaging.getPrice();
    }
}
