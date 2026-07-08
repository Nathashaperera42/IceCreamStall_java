package com.kingston.icecream.app;

import com.kingston.icecream.model.K2533109_Feedback;
import com.kingston.icecream.service.K2533109_FeedbackService;
import com.kingston.icecream.service.K2533109_Order;

import javax.swing.*;
import java.awt.*;

/**
 * GUI step 5 - Feedback and Ratings (req 2.5). The customer rates the completed
 * order (1-5) and leaves a comment; the {@link K2533109_FeedbackService} stores
 * it and reports the running average.
 */
public class K2533109_FeedbackPanel extends JPanel {

    private final K2533109_MainFrame app;
    private final JComboBox<Integer> ratingCombo = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
    private final JTextArea comment = new JTextArea(4, 30);
    private final JLabel resultLabel = new JLabel(" ");
    private final JButton submitBtn = new JButton("Submit Feedback");
    private final JButton newOrderBtn = new JButton("Start New Order \u21BB");

    public K2533109_FeedbackPanel(K2533109_MainFrame app) {
        this.app = app;
        setBackground(K2533109_MainFrame.CREAM);
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel heading = new JLabel("Rate Your Experience");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(K2533109_MainFrame.DARK);
        add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(K2533109_MainFrame.CREAM);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0;
        center.add(bold("Rating (1-5):"), g);
        g.gridx = 1; center.add(ratingCombo, g);

        g.gridx = 0; g.gridy = 1; g.anchor = GridBagConstraints.NORTHWEST;
        center.add(bold("Comment:"), g);
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setBorder(BorderFactory.createLineBorder(new Color(0xCC, 0xCC, 0xCC)));
        g.gridx = 1; center.add(new JScrollPane(comment), g);

        g.gridx = 1; g.gridy = 2;
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        resultLabel.setForeground(K2533109_MainFrame.MINT.darker());
        center.add(resultLabel, g);

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(K2533109_MainFrame.CREAM);
        K2533109_CustomizePanel.styleButton(submitBtn, K2533109_MainFrame.PINK);
        submitBtn.addActionListener(e -> onSubmit());
        K2533109_CustomizePanel.styleButton(newOrderBtn, K2533109_MainFrame.DARK);
        newOrderBtn.addActionListener(e -> app.startNewOrder());
        buttons.add(submitBtn, BorderLayout.WEST);
        buttons.add(newOrderBtn, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
    }

    private void onSubmit() {
        K2533109_Order order = app.getCurrentOrder();
        if (order == null) return;
        int rating = (Integer) ratingCombo.getSelectedItem();
        K2533109_FeedbackService fs = app.getFeedbackService();
        fs.addFeedback(new K2533109_Feedback(
                order.getOrderId(), app.getCustomer().getName(), rating, comment.getText()));
        resultLabel.setText(String.format(
                "Thank you! %d feedback(s) recorded. Average rating: %.1f / 5",
                fs.getCount(), fs.getAverageRating()));
        submitBtn.setEnabled(false);
    }

    /** Reset for a new order. */
    public void refresh() {
        ratingCombo.setSelectedItem(5);
        comment.setText("");
        resultLabel.setText(" ");
        submitBtn.setEnabled(true);
    }

    private JLabel bold(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(K2533109_MainFrame.DARK);
        return l;
    }
}
