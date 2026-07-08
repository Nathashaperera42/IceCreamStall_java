package com.kingston.icecream.app;

import com.kingston.icecream.builder.K2533109_IceCream;
import com.kingston.icecream.builder.K2533109_IceCreamBuilder;
import com.kingston.icecream.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * GUI step 1 - Ice Cream Customisation (req 2.1) and the guided Ordering
 * Process (req 2.2). The form drives a {@link K2533109_IceCreamBuilder} and
 * shows a live price preview computed through the decorator chain.
 */
public class K2533109_CustomizePanel extends JPanel {

    private final K2533109_MainFrame app;

    private final JTextField nameField = new JTextField("My Creation", 18);
    private final JComboBox<K2533109_BaseType> baseCombo = new JComboBox<>(K2533109_BaseType.values());
    private final JComboBox<K2533109_Flavor> flavorCombo = new JComboBox<>(K2533109_Flavor.values());
    private final JComboBox<K2533109_Packaging> packagingCombo = new JComboBox<>(K2533109_Packaging.values());
    private final JComboBox<K2533109_ServingMethod> servingCombo = new JComboBox<>(K2533109_ServingMethod.values());
    private final JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
    private final JCheckBox[] toppingBoxes = new JCheckBox[K2533109_Topping.values().length];
    private final JCheckBox[] sauceBoxes = new JCheckBox[K2533109_Sauce.values().length];
    private final JLabel previewLabel = new JLabel();

    public K2533109_CustomizePanel(K2533109_MainFrame app) {
        this.app = app;
        setBackground(K2533109_MainFrame.CREAM);
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel heading = new JLabel("Build Your Ice Cream");
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setForeground(K2533109_MainFrame.DARK);
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(K2533109_MainFrame.CREAM);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        baseCombo.setRenderer(labelRenderer());
        flavorCombo.setRenderer(labelRenderer());
        packagingCombo.setRenderer(labelRenderer());
        servingCombo.setRenderer(labelRenderer());

        addRow(form, g, row++, "Name your creation:", nameField);
        addRow(form, g, row++, "Base:", baseCombo);
        addRow(form, g, row++, "Flavour:", flavorCombo);

        // Toppings (checkboxes)
        JPanel toppingsPanel = new JPanel(new GridLayout(0, 2));
        toppingsPanel.setBackground(K2533109_MainFrame.CREAM);
        K2533109_Topping[] toppings = K2533109_Topping.values();
        for (int i = 0; i < toppings.length; i++) {
            toppingBoxes[i] = new JCheckBox(toppings[i].getLabel() + " (+$" + toppings[i].getPrice() + ")");
            toppingBoxes[i].setBackground(K2533109_MainFrame.CREAM);
            toppingBoxes[i].addActionListener(e -> updatePreview());
            toppingsPanel.add(toppingBoxes[i]);
        }
        addRow(form, g, row++, "Toppings:", toppingsPanel);

        // Sauces (checkboxes)
        JPanel saucesPanel = new JPanel(new GridLayout(0, 2));
        saucesPanel.setBackground(K2533109_MainFrame.CREAM);
        K2533109_Sauce[] sauces = K2533109_Sauce.values();
        for (int i = 0; i < sauces.length; i++) {
            sauceBoxes[i] = new JCheckBox(sauces[i].getLabel() + " (+$" + sauces[i].getPrice() + ")");
            sauceBoxes[i].setBackground(K2533109_MainFrame.CREAM);
            sauceBoxes[i].addActionListener(e -> updatePreview());
            saucesPanel.add(sauceBoxes[i]);
        }
        addRow(form, g, row++, "Sauces:", saucesPanel);

        addRow(form, g, row++, "Packaging:", packagingCombo);
        addRow(form, g, row++, "Quantity:", qtySpinner);
        addRow(form, g, row++, "Serving method:", servingCombo);

        add(form, BorderLayout.CENTER);

        // Footer: live preview + continue
        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setBackground(K2533109_MainFrame.CREAM);
        previewLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        previewLabel.setForeground(K2533109_MainFrame.DARK);
        previewLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        JButton review = new JButton("Review Order \u2192");
        styleButton(review, K2533109_MainFrame.PINK);
        review.addActionListener(e -> onReview());
        footer.add(previewLabel, BorderLayout.CENTER);
        footer.add(review, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        // Listeners for live preview
        baseCombo.addActionListener(e -> updatePreview());
        flavorCombo.addActionListener(e -> updatePreview());
        packagingCombo.addActionListener(e -> updatePreview());
        qtySpinner.addChangeListener(e -> updatePreview());
        updatePreview();
    }

    private K2533109_IceCream buildFromForm() {
        K2533109_IceCreamBuilder builder = new K2533109_IceCreamBuilder()
                .named(nameField.getText())
                .withBase((K2533109_BaseType) baseCombo.getSelectedItem())
                .withFlavor((K2533109_Flavor) flavorCombo.getSelectedItem())
                .withPackaging((K2533109_Packaging) packagingCombo.getSelectedItem())
                .quantity((Integer) qtySpinner.getValue());
        K2533109_Topping[] toppings = K2533109_Topping.values();
        for (int i = 0; i < toppingBoxes.length; i++) {
            if (toppingBoxes[i].isSelected()) builder.addTopping(toppings[i]);
        }
        K2533109_Sauce[] sauces = K2533109_Sauce.values();
        for (int i = 0; i < sauceBoxes.length; i++) {
            if (sauceBoxes[i].isSelected()) builder.addSauce(sauces[i]);
        }
        return builder.build();
    }

    private void updatePreview() {
        K2533109_IceCream ic = buildFromForm();
        previewLabel.setText(String.format(
                "<html><b>Preview:</b> %s<br>Unit $%.2f &nbsp;|&nbsp; Qty %d &nbsp;|&nbsp; <b>Line total $%.2f</b></html>",
                ic.getDescription(), ic.getUnitPrice(), ic.getQuantity(), ic.getLineTotal()));
    }

    private void onReview() {
        K2533109_IceCream ic = buildFromForm();
        app.setCurrentIceCream(ic);
        app.setServingMethod((K2533109_ServingMethod) servingCombo.getSelectedItem());
        app.goTo(K2533109_MainFrame.CARD_REVIEW);
    }

    /** Pre-fills the form with a representative selection (used for demos/screenshots). */
    void prefillDemo() {
        nameField.setText("Aisha's Mega Sundae");
        baseCombo.setSelectedItem(K2533109_BaseType.CUP);
        flavorCombo.setSelectedItem(K2533109_Flavor.COOKIES_AND_CREAM);
        packagingCombo.setSelectedItem(K2533109_Packaging.GIFT_BOX);
        servingCombo.setSelectedItem(K2533109_ServingMethod.DELIVERY);
        qtySpinner.setValue(1);
        K2533109_Topping[] toppings = K2533109_Topping.values();
        for (int i = 0; i < toppings.length; i++) {
            toppingBoxes[i].setSelected(
                    toppings[i] == K2533109_Topping.OREO || toppings[i] == K2533109_Topping.WHIPPED_CREAM);
        }
        K2533109_Sauce[] sauces = K2533109_Sauce.values();
        for (int i = 0; i < sauces.length; i++) {
            sauceBoxes[i].setSelected(sauces[i] == K2533109_Sauce.CHOCOLATE);
        }
        updatePreview();
    }

    public void resetForm() {
        nameField.setText("My Creation");
        baseCombo.setSelectedIndex(0);
        flavorCombo.setSelectedIndex(0);
        packagingCombo.setSelectedIndex(0);
        servingCombo.setSelectedIndex(0);
        qtySpinner.setValue(1);
        for (JCheckBox b : toppingBoxes) b.setSelected(false);
        for (JCheckBox b : sauceBoxes) b.setSelected(false);
        updatePreview();
    }

    // ---- small UI helpers -----------------------------------------------
    private void addRow(JPanel form, GridBagConstraints g, int row, String label, JComponent field) {
        g.gridx = 0; g.gridy = row; g.weightx = 0;
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(K2533109_MainFrame.DARK);
        form.add(l, g);
        g.gridx = 1; g.weightx = 1;
        form.add(field, g);
    }

    private DefaultListCellRenderer labelRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean sel, boolean focus) {
                super.getListCellRendererComponent(list, value, index, sel, focus);
                if (value instanceof K2533109_BaseType b) setText(b.getLabel() + "  ($" + b.getPrice() + ")");
                else if (value instanceof K2533109_Flavor f) setText(f.getLabel() + "  ($" + f.getPrice() + ")");
                else if (value instanceof K2533109_Packaging p) setText(p.getLabel() + (p.getPrice() > 0 ? "  (+$" + p.getPrice() + ")" : ""));
                else if (value instanceof K2533109_ServingMethod s) setText(s.getLabel());
                return this;
            }
        };
    }

    static void styleButton(JButton b, Color bg) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
