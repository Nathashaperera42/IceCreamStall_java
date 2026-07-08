package com.kingston.icecream.state;

import com.kingston.icecream.service.K2533109_Order;

/** STATE PATTERN - Concrete State: terminal. Order delivered / collected. */
public class K2533109_DeliveredState implements K2533109_OrderState {

    @Override
    public void next(K2533109_Order order) {
        // Terminal state: no further transition. Calling next() is a safe no-op.
    }

    @Override
    public String getName() { return "DELIVERED"; }

    @Override
    public String getCustomerMessage() { return "Delivered. Enjoy your ice cream!"; }

    @Override
    public boolean isTerminal() { return true; }
}
