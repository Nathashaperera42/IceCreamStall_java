package com.kingston.icecream.observer;

/**
 * OBSERVER PATTERN - Observer.
 *
 * Anything that wants to be told when a subject changes implements this.
 * Used by order tracking (order status changes) and by the promotion
 * service (new promotions broadcast to subscribers).
 */
public interface K2533109_Observer {
    /**
     * @param source  who raised the event (e.g. "Order ORD-1001" or "Promotions")
     * @param message the human-readable update
     */
    void update(String source, String message);
}
