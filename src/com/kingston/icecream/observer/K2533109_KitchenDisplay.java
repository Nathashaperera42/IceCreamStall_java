package com.kingston.icecream.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - Concrete Observer.
 *
 * The kitchen / preparation screen. It also subscribes to order updates so the
 * staff see the same state transitions the customer sees.
 */
public class K2533109_KitchenDisplay implements K2533109_Observer {

    private final List<String> feed = new ArrayList<>();

    @Override
    public void update(String source, String message) {
        String entry = source + ": " + message;
        feed.add(entry);
        System.out.println("[KITCHEN] " + entry);
    }

    public List<String> getFeed() { return feed; }
}
