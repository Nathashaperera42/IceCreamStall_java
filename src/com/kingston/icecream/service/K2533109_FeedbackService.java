package com.kingston.icecream.service;

import com.kingston.icecream.model.K2533109_Feedback;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores customer feedback (requirement 2.5) and derives simple analytics.
 * DATA STRUCTURE: a List that grows as ratings are submitted.
 */
public class K2533109_FeedbackService {

    private final List<K2533109_Feedback> feedbackList = new ArrayList<>();

    public void addFeedback(K2533109_Feedback feedback) {
        feedbackList.add(feedback);
    }

    public List<K2533109_Feedback> getAllFeedback() {
        return new ArrayList<>(feedbackList);
    }

    public int getCount() { return feedbackList.size(); }

    /** @return average rating across all feedback, or 0 if none. */
    public double getAverageRating() {
        if (feedbackList.isEmpty()) return 0.0;
        int total = 0;
        for (K2533109_Feedback f : feedbackList) total += f.getRating();
        return (double) total / feedbackList.size();
    }
}
