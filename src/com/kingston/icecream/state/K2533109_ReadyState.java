package com.kingston.icecream.state;

import com.kingston.icecream.model.K2533109_ServingMethod;
import com.kingston.icecream.service.K2533109_Order;


public class K2533109_ReadyState implements K2533109_OrderState {

    @Override
    public void next(K2533109_Order order) {
        if (order.getServingMethod() == K2533109_ServingMethod.DELIVERY) {
            order.setState(new K2533109_OutForDeliveryState());
        } else {
            order.setState(new K2533109_DeliveredState());
        }
    }

    @Override
    public String getName() { return "READY"; }

    @Override
    public String getCustomerMessage() { return "Ready for pickup / collection."; }

    @Override
    public boolean isTerminal() { return false; }
}
