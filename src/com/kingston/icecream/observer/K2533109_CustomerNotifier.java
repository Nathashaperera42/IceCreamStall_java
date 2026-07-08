package com.kingston.icecream.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - Concrete Observer.
 *
 * Represents the channel through which a customer is notified (SMS / app push,
 * mocked here). Every message received is timestamped in an in-memory log so
 * the GUI and the test harness can display the notification history.
 */
public class K2533109_CustomerNotifier implements K2533109_Observer {

    private final String customerName;
    private final List<String> inbox = new ArrayList<>();

    public K2533109_CustomerNotifier(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void update(String source, String message) {
        String entry = "[" + source + "] " + message;
        inbox.add(entry);
        System.out.println("[NOTIFY -> " + customerName + "] " + entry);
    }

    public List<String> getInbox() { return inbox; }
    public String getCustomerName() { return customerName; }
}
