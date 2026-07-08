package com.kingston.icecream.service;

import com.kingston.icecream.observer.K2533109_Observer;
import com.kingston.icecream.observer.K2533109_Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - second Subject.
 *
 * Customers can subscribe to promotions; when the stall launches a promotion
 * every subscriber is notified in real time - the same mechanism used for
 * order tracking, reused for marketing.
 */
public class K2533109_PromotionService implements K2533109_Subject {

    private final List<K2533109_Observer> subscribers = new ArrayList<>();

    @Override
    public void addObserver(K2533109_Observer observer) {
        if (observer != null && !subscribers.contains(observer)) subscribers.add(observer);
    }

    @Override
    public void removeObserver(K2533109_Observer observer) { subscribers.remove(observer); }

    @Override
    public void notifyObservers(String source, String message) {
        for (K2533109_Observer o : subscribers) o.update(source, message);
    }

    /** Launch a promotion - pushed live to every subscriber. */
    public void broadcastPromotion(String message) {
        notifyObservers("Promotions", message);
    }
}
