package com.kingston.icecream.decorator;

/**
 * DECORATOR PATTERN - Base Decorator (abstract).
 * Holds a wrapped component and delegates to it. Concrete decorators extend
 * this and add their own contribution to the description and cost.
 */
public abstract class K2533109_IceCreamDecorator implements K2533109_IceCreamComponent {

    protected final K2533109_IceCreamComponent inner;

    protected K2533109_IceCreamDecorator(K2533109_IceCreamComponent inner) {
        this.inner = inner;
    }

    @Override
    public String getDescription() {
        return inner.getDescription();
    }

    @Override
    public double getCost() {
        return inner.getCost();
    }
}
