package com.kingston.icecream.state;

import com.kingston.icecream.service.K2533109_Order;

/** STATE PATTERN - Concrete State: a rider is delivering the order. */
public class K2533109_OutForDeliveryState implements K2533109_OrderState {

    @Override
    public void next(K2533109_Order order) {
        order.setState(new K2533109_DeliveredState());
    }

    @Override
    public String getName() { return "OUT_FOR_DELIVERY"; }

    @Override
    public String getCustomerMessage() { return "Your order is out for delivery."; }

    @Override
    public boolean isTerminal() { return false; }
}
