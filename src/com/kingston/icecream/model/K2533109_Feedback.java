package com.kingston.icecream.model;

import java.time.LocalDateTime;

/**
 * A rating (1-5) and free-text comment left by a customer for a completed order.
 * Supports requirement 2.5 (Feedback and Ratings).
 */
public class K2533109_Feedback {
    private final String orderId;
    private final String customerName;
    private final int rating;          // 1..5
    private final String comment;
    private final LocalDateTime createdAt;

    public K2533109_Feedback(String orderId, String customerName, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.orderId = orderId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public String stars() {
        return "\u2605".repeat(rating) + "\u2606".repeat(5 - rating);
    }
}
