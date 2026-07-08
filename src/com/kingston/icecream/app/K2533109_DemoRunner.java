package com.kingston.icecream.app;

import com.kingston.icecream.builder.K2533109_IceCream;
import com.kingston.icecream.builder.K2533109_IceCreamBuilder;
import com.kingston.icecream.model.*;
import com.kingston.icecream.observer.K2533109_CustomerNotifier;
import com.kingston.icecream.observer.K2533109_KitchenDisplay;
import com.kingston.icecream.service.K2533109_FeedbackService;
import com.kingston.icecream.service.K2533109_Order;
import com.kingston.icecream.service.K2533109_OrderService;
import com.kingston.icecream.service.K2533109_PromotionService;
import com.kingston.icecream.strategy.*;

/**
 * Headless end-to-end demonstration of the whole system. Running this prints
 * console evidence that every functional module and every design pattern works
 * together. (The GUI in {@link K2533109_Main} exercises the same services.)
 */
public class K2533109_DemoRunner {

    public static void main(String[] args) {
        line("CUSTOM ICE CREAM STALL - SYSTEM DEMONSTRATION");

        // --- actors & shared services ---
        K2533109_Customer customer = new K2533109_Customer("CUST-01", "Aisha Perera", "aisha@example.com");
        K2533109_CustomerNotifier phone = new K2533109_CustomerNotifier(customer.getName());
        K2533109_KitchenDisplay kitchen = new K2533109_KitchenDisplay();
        K2533109_OrderService orderService = new K2533109_OrderService();
        K2533109_FeedbackService feedbackService = new K2533109_FeedbackService();
        K2533109_PromotionService promotions = new K2533109_PromotionService();

        // --- OBSERVER: subscribe to marketing promotions ---
        line("OBSERVER PATTERN - promotion broadcast");
        promotions.addObserver(phone);
        promotions.broadcastPromotion("Happy Hour: 10% off all orders this afternoon!");

        // --- BUILDER + DECORATOR: customise two ice creams ---
        line("BUILDER + DECORATOR PATTERN - customising ice creams");
        K2533109_IceCream sundae = new K2533109_IceCreamBuilder()
                .named("Aisha's Mega Sundae")
                .withBase(K2533109_BaseType.CUP)
                .withFlavor(K2533109_Flavor.COOKIES_AND_CREAM)
                .addTopping(K2533109_Topping.OREO)
                .addTopping(K2533109_Topping.WHIPPED_CREAM)
                .addSauce(K2533109_Sauce.CHOCOLATE)
                .withPackaging(K2533109_Packaging.GIFT_BOX)
                .quantity(1)
                .build();

        K2533109_IceCream coneForKids = new K2533109_IceCreamBuilder()
                .named("Mango Mini")
                .withBase(K2533109_BaseType.CONE)
                .withFlavor(K2533109_Flavor.MANGO)
                .addTopping(K2533109_Topping.SPRINKLES)
                .quantity(2)
                .build();

        System.out.println("Item 1: " + sundae.getDescription());
        System.out.printf("        unit $%.2f%n", sundae.getUnitPrice());
        System.out.println("Item 2: " + coneForKids.getDescription());
        System.out.printf("        unit $%.2f  x%d = $%.2f%n",
                coneForKids.getUnitPrice(), coneForKids.getQuantity(), coneForKids.getLineTotal());

        // --- build the order ---
        K2533109_Order order = new K2533109_Order(customer, K2533109_ServingMethod.DELIVERY);
        order.addItem(sundae);
        order.addItem(coneForKids);
        order.addObserver(phone);    // OBSERVER: customer hears every status change
        order.addObserver(kitchen);  // OBSERVER: kitchen hears every status change

        // --- STRATEGY: promotional discount ---
        line("STRATEGY PATTERN - promotional discount");
        order.setDiscountStrategy(new K2533109_PercentageDiscount(10));
        System.out.printf("Subtotal: $%.2f | Discount: %s | Total: $%.2f%n",
                order.getSubtotal(), order.getDiscountStrategy().getName(), order.getTotal());

        // --- place order (enters NEW state, observers notified) ---
        line("STATE + OBSERVER PATTERN - placing order & live tracking");
        orderService.placeOrder(order);
        System.out.println("Kitchen queue size: " + orderService.kitchenQueueSize());

        // --- STRATEGY: payment ---
        line("STRATEGY PATTERN - payment");
        order.setPaymentStrategy(new K2533109_QRCodePayment("QR-7741"));
        order.processPayment();

        // --- STATE: walk the lifecycle ---
        line("STATE PATTERN - lifecycle transitions");
        while (!order.getState().isTerminal()) {
            order.advance();
        }

        // --- FEEDBACK ---
        line("FEEDBACK & RATINGS");
        feedbackService.addFeedback(
                new K2533109_Feedback(order.getOrderId(), customer.getName(), 5, "Delicious and fast!"));
        feedbackService.addFeedback(
                new K2533109_Feedback("ORD-1000", "Ben", 4, "Great cone."));
        System.out.printf("Feedback count: %d | Average rating: %.1f / 5%n",
                feedbackService.getCount(), feedbackService.getAverageRating());

        line("DEMONSTRATION COMPLETE");
    }

    private static void line(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(" " + title);
        System.out.println("==================================================");
    }
}
