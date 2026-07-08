package com.kingston.icecream.service;

import com.kingston.icecream.builder.K2533109_IceCream;
import com.kingston.icecream.model.K2533109_Customer;
import com.kingston.icecream.model.K2533109_ServingMethod;
import com.kingston.icecream.observer.K2533109_Observer;
import com.kingston.icecream.observer.K2533109_Subject;
import com.kingston.icecream.state.K2533109_NewState;
import com.kingston.icecream.state.K2533109_OrderState;
import com.kingston.icecream.strategy.K2533109_DiscountStrategy;
import com.kingston.icecream.strategy.K2533109_NoDiscount;
import com.kingston.icecream.strategy.K2533109_PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Central domain entity. Plays two design-pattern roles at once:
 *  - STATE PATTERN context: delegates lifecycle behaviour to its current state.
 *  - OBSERVER PATTERN subject: notifies subscribers whenever its state changes.
 * It also holds the chosen STRATEGY objects for discount and payment.
 */
public class K2533109_Order implements K2533109_Subject {

    private String orderId;
    private final K2533109_Customer customer;
    private final K2533109_ServingMethod servingMethod;
    private final List<K2533109_IceCream> items = new ArrayList<>();
    private final List<K2533109_Observer> observers = new ArrayList<>();

    private K2533109_OrderState state = new K2533109_NewState();
    private K2533109_DiscountStrategy discountStrategy = new K2533109_NoDiscount();
    private K2533109_PaymentStrategy paymentStrategy;
    private boolean paid = false;

    public K2533109_Order(K2533109_Customer customer, K2533109_ServingMethod servingMethod) {
        this.customer = customer;
        this.servingMethod = servingMethod;
    }

    // ---- items & pricing -------------------------------------------------
    public void addItem(K2533109_IceCream item) { items.add(item); }
    public List<K2533109_IceCream> getItems() { return items; }

    public double getSubtotal() {
        double sum = 0;
        for (K2533109_IceCream item : items) sum += item.getLineTotal();
        return sum;
    }

    public void setDiscountStrategy(K2533109_DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public K2533109_DiscountStrategy getDiscountStrategy() { return discountStrategy; }

    /** Subtotal after the chosen promotional discount strategy is applied. */
    public double getTotal() {
        return discountStrategy.applyDiscount(getSubtotal());
    }

    // ---- payment (Strategy) ---------------------------------------------
    public void setPaymentStrategy(K2533109_PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("No payment method selected.");
        }
        paid = paymentStrategy.pay(getTotal());
        if (paid) {
            notifyObservers("Order " + orderId,
                    "Payment of $" + String.format("%.2f", getTotal())
                            + " received via " + paymentStrategy.getName() + ".");
        }
        return paid;
    }

    public boolean isPaid() { return paid; }

    // ---- state machine (State + Observer) -------------------------------
    public void setState(K2533109_OrderState state) {
        this.state = state;
        notifyObservers("Order " + orderId, state.getCustomerMessage() + " [" + state.getName() + "]");
    }

    public K2533109_OrderState getState() { return state; }

    /** Advance the order to its next lifecycle state. */
    public void advance() { state.next(this); }

    /** Broadcast the current state once (used right after the order is placed). */
    public void notifyCurrentState() {
        notifyObservers("Order " + orderId, state.getCustomerMessage() + " [" + state.getName() + "]");
    }

    // ---- Observer subject implementation --------------------------------
    @Override
    public void addObserver(K2533109_Observer observer) {
        if (observer != null && !observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void removeObserver(K2533109_Observer observer) { observers.remove(observer); }

    @Override
    public void notifyObservers(String source, String message) {
        for (K2533109_Observer o : observers) o.update(source, message);
    }

    // ---- accessors -------------------------------------------------------
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public K2533109_Customer getCustomer() { return customer; }
    public K2533109_ServingMethod getServingMethod() { return servingMethod; }
}
