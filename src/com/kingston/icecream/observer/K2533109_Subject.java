package com.kingston.icecream.observer;

/**
 * OBSERVER PATTERN - Subject.
 *
 * Anything observers can subscribe to. The Order and the PromotionService are
 * the two subjects in this system.
 */
public interface K2533109_Subject {
    void addObserver(K2533109_Observer observer);
    void removeObserver(K2533109_Observer observer);
    void notifyObservers(String source, String message);
}
