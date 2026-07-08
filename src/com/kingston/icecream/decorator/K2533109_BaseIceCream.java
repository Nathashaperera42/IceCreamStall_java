package com.kingston.icecream.decorator;

import com.kingston.icecream.model.K2533109_BaseType;
import com.kingston.icecream.model.K2533109_Flavor;

/**
 * DECORATOR PATTERN - Concrete Component.
 * The plain ice cream (container base + flavour scoop) before extras are added.
 */
public class K2533109_BaseIceCream implements K2533109_IceCreamComponent {

    private final K2533109_BaseType base;
    private final K2533109_Flavor flavor;

    public K2533109_BaseIceCream(K2533109_BaseType base, K2533109_Flavor flavor) {
        this.base = base;
        this.flavor = flavor;
    }

    @Override
    public String getDescription() {
        return flavor.getLabel() + " in a " + base.getLabel();
    }

    @Override
    public double getCost() {
        return base.getPrice() + flavor.getPrice();
    }
}
