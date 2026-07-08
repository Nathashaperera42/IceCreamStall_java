package com.kingston.icecream.state;

import com.kingston.icecream.service.K2533109_Order;

/**
 * STATE PATTERN - State.
 *
 * Each concrete state knows how to move the order to the NEXT legal state and
 * what status text to show. The Order (context) delegates behaviour to its
 * current state object, so the transition rules live in one place per state
 * instead of in a sprawling switch statement.
 */
public interface K2533109_OrderState {

    /** Advance the order to its next legal state (and notify observers). */
    void next(K2533109_Order order);

    /** Short status name, e.g. "PREPARING". */
    String getName();

    /** Customer-facing message for this state. */
    String getCustomerMessage();

    /** True once the order can progress no further. */
    boolean isTerminal();
}
