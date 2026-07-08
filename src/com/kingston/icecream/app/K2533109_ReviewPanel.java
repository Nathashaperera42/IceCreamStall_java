package com.kingston.icecream.app;

import com.kingston.icecream.builder.K2533109_IceCream;
import com.kingston.icecream.strategy.K2533109_DiscountStrategy;
import com.kingston.icecream.strategy.K2533109_FlatDiscount;
import com.kingston.icecream.strategy.K2533109_NoDiscount;
import com.kingston.icecream.strategy.K2533109_PercentageDiscount;

import javax.swing.*;
import java.awt.*;

/**
 * GUI step 2 - Review the order and choose a promotional discount.
 * The promo dropdown is populated with {@link K2533109_DiscountStrategy}
 * objects, demonstrating the STRATEGY pattern for promotions.
 */
public class K2533109_ReviewPanel extends JPanel {

    private final K2533109_MainFrame app;
    private final JTextArea summary = new JTextArea();
    private final JComboBox<K2533109_DiscountStrategy> promoCombo = new JComboBox<>(new K2533109_DiscountStrategy[]{
            new K2533109_NoDiscount(),
            new K2533109_PercentageDiscount(10),
            new K2533109_PercentageDiscount(15),
            new K2533109_FlatDiscount(2.0)
    });
    private final JLabel totalsLabel = new JLabel();

    public K2533109_ReviewPanel(K2533109_MainFrame app) {
        this.app = app;
        setBackground(K2533109_MainFrame.CREAM);
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel heading = new JLabel("Review Your Order");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(K2533109_MainFrame.DARK);
        add(heading, BorderLayout.NORTH);

        summary.setEditable(false);
        summary.setFont(new Font("Monospaced", Font.PLAIN, 13));
        summary.setBackground(Color.WHITE);
        summary.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(summary), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(0, 10));
        south.setBackground(K2533109_MainFrame.CREAM);

        JPanel promoRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        promoRow.setBackground(K2533109_MainFrame.CREAM);
        JLabel promoLbl = new JLabel("Apply promotion:");
        promoLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        promoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof K2533109_DiscountStrategy d) setText(d.getName());
                return this;
            }
        });
        promoCombo.addActionListener(e -> recompute());
        promoRow.add(promoLbl);
        promoRow.add(promoCombo);

        totalsLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalsLabel.setForeground(K2533109_MainFrame.DARK);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(K2533109_MainFrame.CREAM);
        JButton back = new JButton("\u2190 Back");
        K2533109_CustomizePanel.styleButton(back, K2533109_MainFrame.DARK);
        back.addActionListener(e -> app.goTo(K2533109_MainFrame.CARD_CUSTOMISE));
        JButton pay = new JButton("Proceed to Payment \u2192");
        K2533109_CustomizePanel.styleButton(pay, K2533109_MainFrame.PINK);
        pay.addActionListener(e -> {
            app.setDiscountStrategy((K2533109_DiscountStrategy) promoCombo.getSelectedItem());
            app.goTo(K2533109_MainFrame.CARD_PAYMENT);
        });
        buttons.add(back, BorderLayout.WEST);
        buttons.add(pay, BorderLayout.EAST);

        south.add(promoRow, BorderLayout.NORTH);
        south.add(totalsLabel, BorderLayout.CENTER);
        south.add(buttons, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);
    }

    /** Called whenever this card is shown. */
    public void refresh() {
        K2533109_IceCream ic = app.getCurrentIceCream();
        if (ic == null) { summary.setText("(no item)"); return; }
        StringBuilder sb = new StringBuilder();
        sb.append("Item   : ").append(ic.getName()).append('\n');
        sb.append("Details: ").append(ic.getDescription()).append('\n');
        sb.append("Base   : ").append(ic.getBase().getLabel()).append('\n');
        sb.append("Flavour: ").append(ic.getFlavor().getLabel()).append('\n');
        sb.append("Serving: ").append(app.getServingMethod().getLabel()).append('\n');
        sb.append(String.format("Unit   : $%.2f%n", ic.getUnitPrice()));
        sb.append("Qty    : ").append(ic.getQuantity()).append('\n');
        summary.setText(sb.toString());
        recompute();
    }

    private void recompute() {
        K2533109_IceCream ic = app.getCurrentIceCream();
        if (ic == null) return;
        K2533109_DiscountStrategy d = (K2533109_DiscountStrategy) promoCombo.getSelectedItem();
        double subtotal = ic.getLineTotal();
        double total = d.applyDiscount(subtotal);
        totalsLabel.setText(String.format(
                "<html>Subtotal: $%.2f &nbsp;&nbsp; Promo: %s &nbsp;&nbsp; <span style='color:#E86A92'>Total: $%.2f</span></html>",
                subtotal, d.getName(), total));
    }
}
