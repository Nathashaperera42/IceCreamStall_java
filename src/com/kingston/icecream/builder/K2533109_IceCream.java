package com.kingston.icecream.builder;

import com.kingston.icecream.decorator.K2533109_IceCreamComponent;
import com.kingston.icecream.model.K2533109_BaseType;
import com.kingston.icecream.model.K2533109_Flavor;
import com.kingston.icecream.model.K2533109_Packaging;
import com.kingston.icecream.model.K2533109_Sauce;
import com.kingston.icecream.model.K2533109_Topping;

import java.util.Collections;
import java.util.List;

/**
 * BUILDER PATTERN - Product.
 *
 * The complex object the builder produces: a fully customised, named ice cream
 * with a quantity. Its price/description come from the decorated component
 * assembled by the builder, so this class stays a simple immutable value object.
 */
public class K2533109_IceCream {

    private final String name;
    private final K2533109_BaseType base;
    private final K2533109_Flavor flavor;
    private final List<K2533109_Topping> toppings;
    private final List<K2533109_Sauce> sauces;
    private final K2533109_Packaging packaging;
    private final int quantity;
    private final K2533109_IceCreamComponent component; // decorated price/description engine

    K2533109_IceCream(String name, K2533109_BaseType base, K2533109_Flavor flavor,
                      List<K2533109_Topping> toppings, List<K2533109_Sauce> sauces,
                      K2533109_Packaging packaging, int quantity,
                      K2533109_IceCreamComponent component) {
        this.name = name;
        this.base = base;
        this.flavor = flavor;
        this.toppings = toppings;
        this.sauces = sauces;
        this.packaging = packaging;
        this.quantity = quantity;
        this.component = component;
    }

    public String getName() { return name; }
    public K2533109_BaseType getBase() { return base; }
    public K2533109_Flavor getFlavor() { return flavor; }
    public List<K2533109_Topping> getToppings() { return Collections.unmodifiableList(toppings); }
    public List<K2533109_Sauce> getSauces() { return Collections.unmodifiableList(sauces); }
    public K2533109_Packaging getPackaging() { return packaging; }
    public int getQuantity() { return quantity; }

    /** Price of a single unit, computed by the decorator chain. */
    public double getUnitPrice() { return component.getCost(); }

    /** Unit price multiplied by quantity. */
    public double getLineTotal() { return getUnitPrice() * quantity; }

    /** Full description, built up by the decorator chain. */
    public String getDescription() { return component.getDescription(); }

    @Override
    public String toString() {
        return String.format("%dx \"%s\" - %s ($%.2f each)",
                quantity, name, getDescription(), getUnitPrice());
    }
}
