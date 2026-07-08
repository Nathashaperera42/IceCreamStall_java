package com.kingston.icecream.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Application service that registers orders and models the kitchen workflow.
 *
 * DATA STRUCTURES:
 *  - a Map keyed by order id for O(1) lookup of any order;
 *  - a FIFO Queue (the kitchen line) so orders are prepared in the sequence
 *    they were placed - first in, first served.
 */
public class K2533109_OrderService {

    private final Map<String, K2533109_Order> orders = new LinkedHashMap<>();
    private final Queue<K2533109_Order> kitchenQueue = new LinkedList<>();
    private int sequence = 1000;

    /** Registers a new order, assigns it an id, queues it for the kitchen. */
    public K2533109_Order placeOrder(K2533109_Order order) {
        String id = "ORD-" + (++sequence);
        order.setOrderId(id);
        orders.put(id, order);
        kitchenQueue.offer(order);
        order.notifyCurrentState();
        return order;
    }

    /** Pull the next waiting order off the kitchen queue (FIFO). */
    public K2533109_Order takeNextForKitchen() {
        return kitchenQueue.poll();
    }

    public int kitchenQueueSize() { return kitchenQueue.size(); }

    public K2533109_Order findOrder(String id) { return orders.get(id); }

    public Map<String, K2533109_Order> getOrders() { return orders; }
}
