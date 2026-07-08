package com.kingston.icecream.state;

import com.kingston.icecream.service.K2533109_Order;

/** STATE PATTERN - Concrete State: the kitchen is making the ice cream. */
public class K2533109_PreparingState implements K2533109_OrderState {

    @Override
    public void next(K2533109_Order order) {
        order.setState(new K2533109_ReadyState());
    }

    @Override
    public String getName() { return "PREPARING"; }

    @Override
    public String getCustomerMessage() { return "Your ice cream is being prepared."; }

    @Override
    public boolean isTerminal() { return false; }
}
