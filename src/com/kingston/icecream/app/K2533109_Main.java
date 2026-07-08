package com.kingston.icecream.app;

import javax.swing.*;

/**
 * Application entry point. Launches the Custom Ice Cream Stall GUI on the Swing
 * Event Dispatch Thread, as required for thread-safe Swing applications.
 */
public class K2533109_Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // fall back to default look and feel
        }
        SwingUtilities.invokeLater(() -> new K2533109_MainFrame().setVisible(true));
    }
}
