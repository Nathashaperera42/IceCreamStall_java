package com.kingston.icecream.app;

import com.kingston.icecream.observer.K2533109_Observer;
import com.kingston.icecream.service.K2533109_Order;

import javax.swing.*;
import java.awt.*;

/**
 * GUI step 4 - Real-Time Order Tracking (req 2.3).
 *
 * This panel is itself a concrete {@link K2533109_Observer}: it is registered
 * on the order, so every STATE transition pushes a live update here without the
 * panel having to poll. A Swing Timer auto-advances the order to simulate the
 * kitchen working, and a button lets the user step it manually.
 */
public class K2533109_TrackingPanel extends JPanel implements K2533109_Observer {

    private static final String[] STAGES =
            {"NEW", "PREPARING", "READY", "OUT_FOR_DELIVERY", "DELIVERED"};

    private final K2533109_MainFrame app;
    private final JLabel[] chips = new JLabel[STAGES.length];
    private final JLabel statusLine = new JLabel("Waiting for order...");
    private final JTextArea log = new JTextArea();
    private final JButton advanceBtn = new JButton("Simulate Next Status \u2192");
    private final JButton feedbackBtn = new JButton("Leave Feedback \u2192");
    private Timer autoTimer;
    private K2533109_Order order;

    public K2533109_TrackingPanel(K2533109_MainFrame app) {
        this.app = app;
        setBackground(K2533109_MainFrame.CREAM);
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel heading = new JLabel("Live Order Tracking");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(K2533109_MainFrame.DARK);
        add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(K2533109_MainFrame.CREAM);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Status chips (the state machine, visualised)
        JPanel chipRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        chipRow.setBackground(K2533109_MainFrame.CREAM);
        chipRow.setAlignmentX(LEFT_ALIGNMENT);
        for (int i = 0; i < STAGES.length; i++) {
            chips[i] = makeChip(STAGES[i].replace('_', ' '));
            chipRow.add(chips[i]);
            if (i < STAGES.length - 1) {
                JLabel arrow = new JLabel("\u2192");
                arrow.setForeground(Color.GRAY);
                chipRow.add(arrow);
            }
        }
        center.add(chipRow);

        statusLine.setFont(new Font("SansSerif", Font.BOLD, 15));
        statusLine.setForeground(K2533109_MainFrame.PINK);
        statusLine.setAlignmentX(LEFT_ALIGNMENT);
        statusLine.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
        center.add(statusLine);

        JLabel logLbl = new JLabel("Notification feed:");
        logLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        logLbl.setForeground(K2533109_MainFrame.DARK);
        logLbl.setAlignmentX(LEFT_ALIGNMENT);
        center.add(logLbl);

        log.setEditable(false);
        log.setFont(new Font("Monospaced", Font.PLAIN, 12));
        log.setBackground(Color.WHITE);
        JScrollPane sp = new JScrollPane(log);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setPreferredSize(new Dimension(740, 220));
        center.add(sp);

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(K2533109_MainFrame.CREAM);
        K2533109_CustomizePanel.styleButton(advanceBtn, K2533109_MainFrame.DARK);
        advanceBtn.addActionListener(e -> advanceOnce());
        K2533109_CustomizePanel.styleButton(feedbackBtn, K2533109_MainFrame.PINK);
        feedbackBtn.setEnabled(false);
        feedbackBtn.addActionListener(e -> {
            stopTimer();
            app.goTo(K2533109_MainFrame.CARD_FEEDBACK);
        });
        buttons.add(advanceBtn, BorderLayout.WEST);
        buttons.add(feedbackBtn, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
    }

    /** Begin tracking a freshly placed order and start auto-advance. */
    public void bindTo(K2533109_Order order) {
        this.order = order;
        highlight(order.getState().getName());
        statusLine.setText("Current status: " + order.getState().getCustomerMessage());
        advanceBtn.setEnabled(true);
        feedbackBtn.setEnabled(false);
        startTimer();
    }

    private void startTimer() {
        stopTimer();
        autoTimer = new Timer(2500, e -> advanceOnce());
        autoTimer.start();
    }

    private void stopTimer() {
        if (autoTimer != null) autoTimer.stop();
    }

    /** Stop the auto-advance timer (used for deterministic demos/screenshots). */
    void pauseAuto() { stopTimer(); }

    /** Public action behind the "Simulate Next Status" button. */
    void simulateNextStatus() { advanceOnce(); }

    private void advanceOnce() {
        if (order == null) return;
        if (order.getState().isTerminal()) {
            onReachedEnd();
            return;
        }
        order.advance(); // triggers update(...) via Observer
        highlight(order.getState().getName());
        statusLine.setText("Current status: " + order.getState().getCustomerMessage());
        if (order.getState().isTerminal()) onReachedEnd();
    }

    private void onReachedEnd() {
        stopTimer();
        advanceBtn.setEnabled(false);
        feedbackBtn.setEnabled(true);
    }

    /** OBSERVER callback - the order pushes status updates straight into the feed. */
    @Override
    public void update(String source, String message) {
        SwingUtilities.invokeLater(() -> {
            log.append(source + "  |  " + message + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    private void highlight(String stateName) {
        for (int i = 0; i < STAGES.length; i++) {
            boolean active = STAGES[i].equals(stateName);
            chips[i].setOpaque(true);
            chips[i].setBackground(active ? K2533109_MainFrame.MINT : Color.WHITE);
            chips[i].setForeground(active ? Color.WHITE : Color.GRAY);
        }
    }

    private JLabel makeChip(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setOpaque(true);
        l.setBackground(Color.WHITE);
        l.setForeground(Color.GRAY);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDD, 0xDD, 0xDD)),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)));
        return l;
    }
}
