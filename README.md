# Custom Ice Cream Stall Management System (CI6115)

A Java **Swing GUI** application demonstrating five design patterns:
**Builder, Observer, Strategy, State, Decorator**.

> IMPORTANT: every class is prefixed with the placeholder student ID `K2533109`.
> Before submitting, do a global find-and-replace of `K2533109` with your real
> Kingston University ID (e.g. `K1234567`) across all `.java` files AND rename
> the files to match. Also regenerate the diagrams (see `diagrams/`) so the class
> names in the images show your ID.

## Project structure

```
src/com/kingston/icecream/
  model/        enums (base, flavor, topping, sauce, packaging, serving) + Customer, Feedback
  decorator/    Decorator pattern  - pricing/description engine
  builder/      Builder pattern    - assembles a custom IceCream
  state/        State pattern       - order lifecycle states
  observer/     Observer pattern    - Observer/Subject + notifiers
  strategy/     Strategy pattern    - payment methods + discounts
  service/      Order (State context + Observer subject), OrderService (Map + FIFO Queue),
                FeedbackService, PromotionService
  app/          Swing GUI (MainFrame controller + 5 step panels) + Main + DemoRunner
diagrams/       class diagram + flowchart (Graphviz .dot sources and rendered .png)
screenshots/    GUI screenshots and console output evidence
```

## Requirements
- JDK 17 or newer (developed and tested on OpenJDK 21).

## Compile

From the project root:

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

## Run the GUI

```bash
java -cp out com.kingston.icecream.app.K2533109_Main
```

The app walks through: Customise -> Review -> Payment -> Tracking -> Feedback.

## Run the headless demonstration (console evidence)

```bash
java -cp out com.kingston.icecream.app.K2533109_DemoRunner
```

This exercises all five patterns end to end and prints the output used as
evidence in the report.

## Pattern map (where to look)

| Pattern   | Key classes |
|-----------|-------------|
| Builder   | builder/K2533109_IceCreamBuilder, builder/K2533109_IceCream |
| Decorator | decorator/K2533109_IceCreamComponent + *Decorator classes |
| Observer  | observer/K2533109_Observer, K2533109_Subject; service/K2533109_Order, K2533109_PromotionService; app/K2533109_TrackingPanel |
| Strategy  | strategy/K2533109_PaymentStrategy (+Card/Wallet/QR), K2533109_DiscountStrategy (+None/Percentage/Flat) |
| State     | state/K2533109_OrderState + New/Preparing/Ready/OutForDelivery/Delivered |
