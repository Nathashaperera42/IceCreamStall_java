package com.kingston.icecream.builder;

import com.kingston.icecream.decorator.K2533109_BaseIceCream;
import com.kingston.icecream.decorator.K2533109_IceCreamComponent;
import com.kingston.icecream.decorator.K2533109_PackagingDecorator;
import com.kingston.icecream.decorator.K2533109_SauceDecorator;
import com.kingston.icecream.decorator.K2533109_ToppingDecorator;
import com.kingston.icecream.model.K2533109_BaseType;
import com.kingston.icecream.model.K2533109_Flavor;
import com.kingston.icecream.model.K2533109_Packaging;
import com.kingston.icecream.model.K2533109_Sauce;
import com.kingston.icecream.model.K2533109_Topping;

import java.util.ArrayList;
import java.util.List;

/**
 * BUILDER PATTERN - Builder.
 *
 * Provides a fluent, step-by-step way to assemble a complex {@link K2533109_IceCream}.
 * The customer chooses a base and flavour, then adds any number of toppings and
 * sauces, picks packaging and a quantity, and names the creation. On build() the
 * builder wires up the DECORATOR chain so the product's price and description are
 * computed correctly - a clean example of two patterns co-operating.
 */
public class K2533109_IceCreamBuilder {

    private String name = "Custom Creation";
    private K2533109_BaseType base;
    private K2533109_Flavor flavor;
    private final List<K2533109_Topping> toppings = new ArrayList<>();
    private final List<K2533109_Sauce> sauces = new ArrayList<>();
    private K2533109_Packaging packaging = K2533109_Packaging.STANDARD;
    private int quantity = 1;

    public K2533109_IceCreamBuilder named(String name) {
        if (name != null && !name.isBlank()) this.name = name;
        return this;
    }

    public K2533109_IceCreamBuilder withBase(K2533109_BaseType base) {
        this.base = base;
        return this;
    }

    public K2533109_IceCreamBuilder withFlavor(K2533109_Flavor flavor) {
        this.flavor = flavor;
        return this;
    }

    public K2533109_IceCreamBuilder addTopping(K2533109_Topping topping) {
        if (topping != null) this.toppings.add(topping);
        return this;
    }

    public K2533109_IceCreamBuilder addSauce(K2533109_Sauce sauce) {
        if (sauce != null) this.sauces.add(sauce);
        return this;
    }

    public K2533109_IceCreamBuilder withPackaging(K2533109_Packaging packaging) {
        if (packaging != null) this.packaging = packaging;
        return this;
    }

    public K2533109_IceCreamBuilder quantity(int quantity) {
        if (quantity >= 1) this.quantity = quantity;
        return this;
    }

    /**
     * Validates the configuration and assembles the product, building the
     * decorator chain: base -> toppings -> sauces -> packaging.
     */
    public K2533109_IceCream build() {
        if (base == null) throw new IllegalStateException("A base (cone or cup) must be chosen.");
        if (flavor == null) throw new IllegalStateException("A flavour must be chosen.");

        K2533109_IceCreamComponent component = new K2533109_BaseIceCream(base, flavor);
        for (K2533109_Topping t : toppings) {
            component = new K2533109_ToppingDecorator(component, t);
        }
        for (K2533109_Sauce s : sauces) {
            component = new K2533109_SauceDecorator(component, s);
        }
        component = new K2533109_PackagingDecorator(component, packaging);

        return new K2533109_IceCream(name, base, flavor,
                new ArrayList<>(toppings), new ArrayList<>(sauces),
                packaging, quantity, component);
    }
}
