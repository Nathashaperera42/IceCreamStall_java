package com.kingston.icecream.state;

import com.kingston.icecream.service.K2533109_Order;

/** STATE PATTERN - Concrete State: order placed but not yet started. */
public class K2533109_NewState implements K2533109_OrderState {

    @Override
    public void next(K2533109_Order order) {
        order.setState(new K2533109_PreparingState());
    }

    @Override
    public String getName() { return "NEW"; }

    @Override
    public String getCustomerMessage() { return "Order received and confirmed."; }

    @Override
    public boolean isTerminal() { return false; }
}
