package com.kingston.icecream.app;

import com.kingston.icecream.builder.K2533109_IceCream;
import com.kingston.icecream.model.K2533109_Customer;
import com.kingston.icecream.model.K2533109_ServingMethod;
import com.kingston.icecream.observer.K2533109_CustomerNotifier;
import com.kingston.icecream.service.K2533109_FeedbackService;
import com.kingston.icecream.service.K2533109_Order;
import com.kingston.icecream.service.K2533109_OrderService;
import com.kingston.icecream.service.K2533109_PromotionService;
import com.kingston.icecream.strategy.K2533109_DiscountStrategy;
import com.kingston.icecream.strategy.K2533109_NoDiscount;
import com.kingston.icecream.strategy.K2533109_PaymentStrategy;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window AND lightweight controller for the GUI.
 *
 * It owns the shared application services, the in-progress "session" (the ice
 * cream currently being customised, the chosen serving method, discount, and
 * the live order), and a CardLayout that steps the customer through the
 * ordering wizard: CUSTOMISE -> REVIEW -> PAYMENT -> TRACKING -> FEEDBACK.
 */
public class K2533109_MainFrame extends JFrame {

    // Card names
    public static final String CARD_CUSTOMISE = "customise";
    public static final String CARD_REVIEW = "review";
    public static final String CARD_PAYMENT = "payment";
    public static final String CARD_TRACKING = "tracking";
    public static final String CARD_FEEDBACK = "feedback";

    // Theme colours (ice-cream parlour palette)
    public static final Color PINK = new Color(0xE8, 0x6A, 0x92);
    public static final Color CREAM = new Color(0xFF, 0xF5, 0xE9);
    public static final Color DARK = new Color(0x3A, 0x2E, 0x39);
    public static final Color MINT = new Color(0x6F, 0xC2, 0xA6);

    // Shared services
    private final K2533109_OrderService orderService = new K2533109_OrderService();
    private final K2533109_FeedbackService feedbackService = new K2533109_FeedbackService();
    private final K2533109_PromotionService promotionService = new K2533109_PromotionService();

    // Session state (current customer journey)
    private final K2533109_Customer customer = new K2533109_Customer("CUST-01", "Guest", "guest@stall.lk");
    private final K2533109_CustomerNotifier notifier = new K2533109_CustomerNotifier(customer.getName());
    private K2533109_IceCream currentIceCream;
    private K2533109_ServingMethod servingMethod = K2533109_ServingMethod.PICKUP;
    private K2533109_DiscountStrategy discountStrategy = new K2533109_NoDiscount();
    private K2533109_Order currentOrder;

    // UI
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final JLabel stepLabel = new JLabel();

    private final K2533109_CustomizePanel customizePanel;
    private final K2533109_ReviewPanel reviewPanel;
    private final K2533109_PaymentPanel paymentPanel;
    private final K2533109_TrackingPanel trackingPanel;
    private final K2533109_FeedbackPanel feedbackPanel;

    public K2533109_MainFrame() {
        super("Custom Ice Cream Stall Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 640);
        setLocationRelativeTo(null);

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PINK);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel title = new JLabel("\uD83C\uDF66  Custom Ice Cream Stall");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        stepLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        stepLabel.setForeground(Color.WHITE);
        stepLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(title, BorderLayout.WEST);
        header.add(stepLabel, BorderLayout.EAST);

        // Panels
        customizePanel = new K2533109_CustomizePanel(this);
        reviewPanel = new K2533109_ReviewPanel(this);
        paymentPanel = new K2533109_PaymentPanel(this);
        trackingPanel = new K2533109_TrackingPanel(this);
        feedbackPanel = new K2533109_FeedbackPanel(this);

        cards.add(customizePanel, CARD_CUSTOMISE);
        cards.add(reviewPanel, CARD_REVIEW);
        cards.add(paymentPanel, CARD_PAYMENT);
        cards.add(trackingPanel, CARD_TRACKING);
        cards.add(feedbackPanel, CARD_FEEDBACK);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(cards, BorderLayout.CENTER);

        // A standing promotion the customer is subscribed to
        promotionService.addObserver(notifier);

        goTo(CARD_CUSTOMISE);
    }

    // ---- navigation ------------------------------------------------------
    public void goTo(String card) {
        switch (card) {
            case CARD_CUSTOMISE -> stepLabel.setText("Step 1 of 5  \u2022  Customise");
            case CARD_REVIEW -> { stepLabel.setText("Step 2 of 5  \u2022  Review"); reviewPanel.refresh(); }
            case CARD_PAYMENT -> { stepLabel.setText("Step 3 of 5  \u2022  Payment"); paymentPanel.refresh(); }
            case CARD_TRACKING -> stepLabel.setText("Step 4 of 5  \u2022  Tracking");
            case CARD_FEEDBACK -> { stepLabel.setText("Step 5 of 5  \u2022  Feedback"); feedbackPanel.refresh(); }
            default -> { }
        }
        cardLayout.show(cards, card);
    }

    /** Begin a brand new order, clearing the previous session. */
    public void startNewOrder() {
        currentIceCream = null;
        currentOrder = null;
        discountStrategy = new K2533109_NoDiscount();
        customizePanel.resetForm();
        goTo(CARD_CUSTOMISE);
    }

    /**
     * Creates the order, registers observers (the customer's phone + the live
     * tracking panel), places it (entering the NEW state) and processes payment
     * with the chosen STRATEGY.
     */
    public boolean placeAndPay(K2533109_PaymentStrategy paymentStrategy) {
        currentOrder = new K2533109_Order(customer, servingMethod);
        currentOrder.addItem(currentIceCream);
        currentOrder.setDiscountStrategy(discountStrategy);
        currentOrder.addObserver(notifier);
        currentOrder.addObserver(trackingPanel); // tracking panel is an Observer
        orderService.placeOrder(currentOrder);
        currentOrder.setPaymentStrategy(paymentStrategy);
        boolean ok = currentOrder.processPayment();
        if (ok) {
            trackingPanel.bindTo(currentOrder);
        }
        return ok;
    }

    // ---- session accessors ----------------------------------------------
    public K2533109_OrderService getOrderService() { return orderService; }
    public K2533109_FeedbackService getFeedbackService() { return feedbackService; }
    public K2533109_PromotionService getPromotionService() { return promotionService; }
    public K2533109_Customer getCustomer() { return customer; }

    public K2533109_IceCream getCurrentIceCream() { return currentIceCream; }
    public void setCurrentIceCream(K2533109_IceCream ic) { this.currentIceCream = ic; }

    public K2533109_ServingMethod getServingMethod() { return servingMethod; }
    public void setServingMethod(K2533109_ServingMethod m) { this.servingMethod = m; }

    public K2533109_DiscountStrategy getDiscountStrategy() { return discountStrategy; }
    public void setDiscountStrategy(K2533109_DiscountStrategy d) { this.discountStrategy = d; }

    public K2533109_Order getCurrentOrder() { return currentOrder; }

    // ---- demo / screenshot helpers (used by the demo tooling) -----------
    void demoPrefillCustomise() { customizePanel.prefillDemo(); }
    void demoPauseTracking() { trackingPanel.pauseAuto(); }
    void demoNextStatus() { trackingPanel.simulateNextStatus(); }
}
