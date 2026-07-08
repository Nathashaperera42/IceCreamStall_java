package com.kingston.icecream.app;

import com.kingston.icecream.strategy.K2533109_CardPayment;
import com.kingston.icecream.strategy.K2533109_DigitalWalletPayment;
import com.kingston.icecream.strategy.K2533109_PaymentStrategy;
import com.kingston.icecream.strategy.K2533109_QRCodePayment;

import javax.swing.*;
import java.awt.*;

/**
 * GUI step 3 - Payment Handling (req 2.4). The three payment methods are
 * concrete {@link K2533109_PaymentStrategy} objects; choosing one swaps the
 * algorithm the order uses to take payment (STRATEGY pattern). All processing
 * is mocked.
 */
public class K2533109_PaymentPanel extends JPanel {

    private final K2533109_MainFrame app;

    private final JRadioButton cardBtn = new JRadioButton("Credit / Debit Card", true);
    private final JRadioButton walletBtn = new JRadioButton("Digital Wallet");
    private final JRadioButton qrBtn = new JRadioButton("QR Code");

    private final JTextField cardName = new JTextField("Guest User", 16);
    private final JTextField cardNumber = new JTextField("4111 1111 1111 1234", 16);
    private final JTextField walletId = new JTextField("guest@wallet", 16);
    private final JTextField qrRef = new JTextField("QR-7741", 16);

    private final JPanel detailCards = new JPanel(new CardLayout());
    private final JLabel amountLabel = new JLabel();

    public K2533109_PaymentPanel(K2533109_MainFrame app) {
        this.app = app;
        setBackground(K2533109_MainFrame.CREAM);
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel heading = new JLabel("Payment");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(K2533109_MainFrame.DARK);
        add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(K2533109_MainFrame.CREAM);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        amountLabel.setForeground(K2533109_MainFrame.PINK);
        amountLabel.setAlignmentX(LEFT_ALIGNMENT);
        center.add(amountLabel);
        center.add(Box.createVerticalStrut(12));

        ButtonGroup group = new ButtonGroup();
        for (JRadioButton b : new JRadioButton[]{cardBtn, walletBtn, qrBtn}) {
            b.setBackground(K2533109_MainFrame.CREAM);
            b.setFont(new Font("SansSerif", Font.PLAIN, 14));
            b.setAlignmentX(LEFT_ALIGNMENT);
            group.add(b);
            center.add(b);
        }

        // Conditional detail fields per method
        detailCards.setBackground(K2533109_MainFrame.CREAM);
        detailCards.setAlignmentX(LEFT_ALIGNMENT);
        detailCards.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel cardDetail = field2("Name on card:", cardName, "Card number:", cardNumber);
        JPanel walletDetail = field1("Wallet ID:", walletId);
        JPanel qrDetail = field1("QR reference:", qrRef);
        detailCards.add(cardDetail, "card");
        detailCards.add(walletDetail, "wallet");
        detailCards.add(qrDetail, "qr");
        center.add(detailCards);

        cardBtn.addActionListener(e -> showDetail("card"));
        walletBtn.addActionListener(e -> showDetail("wallet"));
        qrBtn.addActionListener(e -> showDetail("qr"));

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(K2533109_MainFrame.CREAM);
        JButton back = new JButton("\u2190 Back");
        K2533109_CustomizePanel.styleButton(back, K2533109_MainFrame.DARK);
        back.addActionListener(e -> app.goTo(K2533109_MainFrame.CARD_REVIEW));
        JButton pay = new JButton("Pay Now \u2192");
        K2533109_CustomizePanel.styleButton(pay, K2533109_MainFrame.MINT);
        pay.addActionListener(e -> onPay());
        buttons.add(back, BorderLayout.WEST);
        buttons.add(pay, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
    }

    private void showDetail(String key) {
        ((CardLayout) detailCards.getLayout()).show(detailCards, key);
    }

    private K2533109_PaymentStrategy selectedStrategy() {
        if (walletBtn.isSelected()) {
            return new K2533109_DigitalWalletPayment(walletId.getText());
        } else if (qrBtn.isSelected()) {
            return new K2533109_QRCodePayment(qrRef.getText());
        }
        return new K2533109_CardPayment(cardName.getText(), cardNumber.getText());
    }

    private void onPay() {
        boolean ok = app.placeAndPay(selectedStrategy());
        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Payment successful!\nOrder " + app.getCurrentOrder().getOrderId() + " placed.",
                    "Payment", JOptionPane.INFORMATION_MESSAGE);
            app.goTo(K2533109_MainFrame.CARD_TRACKING);
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed. Please try again.",
                    "Payment", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Called when this card is shown - refreshes the amount due. */
    public void refresh() {
        if (app.getCurrentIceCream() == null) return;
        double total = app.getDiscountStrategy().applyDiscount(app.getCurrentIceCream().getLineTotal());
        amountLabel.setText(String.format("Amount due: $%.2f", total));
        showDetail("card");
        cardBtn.setSelected(true);
    }

    // ---- helpers ---------------------------------------------------------
    private JPanel field1(String l1, JTextField f1) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(K2533109_MainFrame.CREAM);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        g.gridx = 0; g.gridy = 0; p.add(boldLabel(l1), g);
        g.gridx = 1; p.add(f1, g);
        return p;
    }

    private JPanel field2(String l1, JTextField f1, String l2, JTextField f2) {
        JPanel p = field1(l1, f1);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        g.gridx = 0; g.gridy = 1; p.add(boldLabel(l2), g);
        g.gridx = 1; p.add(f2, g);
        return p;
    }

    private JLabel boldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(K2533109_MainFrame.DARK);
        return l;
    }
}
