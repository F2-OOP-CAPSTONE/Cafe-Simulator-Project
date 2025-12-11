# Cafe-Simulator-Project

A friendly desktop cafe simulator with a day/night loop, fame system, and inventory pressure where customers line up, you mix drinks in a simple UI, and receipts/sales grow as you serve them.

Features

- Fame now swings based on your service: correct drink + size grants +5 fame, wrong size/drink -10, running out of cups -20, and letting patience hit zero -10. Fame also scales your daily customer cap (min 1, max 15).
- Day cycle with taxes and risk: serve through the quota to trigger a day-end overlay, pay rising taxes, and continue until you either profit or go bankrupt (bankruptcy shows a game-over modal).
- Ingredient + cup inventory: every ingredient and cup size is tracked; each button press consumes stock and patience. Restock all items for a flat $5 to add 10 units apiece.
- Customers: regular, student, rich, and karen visitors arrive with different patience/dialogue, tip differently, and react to bad orders by refusing to pay.
- UI helpers: a shop status dashboard shows fame rank, balance, taxes, day progress, and overlays mark day start/completion. Receipts include price + tip breakdowns.
- Mixing: build drinks by selecting cup size then adding coffee, milk, water, sugar, chocolate, and syrup to match recipes or experiment.

Gameplay loop

- Pick a cup size, assemble the drink with the ingredient buttons (each costs 2 patience), and serve.
- Check inventory/restock from the prep screen; monitor patience/fame in the header; open Shop Stats anytime.
- Finish the daily customer quota to pay taxes and roll into the next day; avoid negative balance to keep playing.

Install / run

- This project targets **Java 17** for both compilation and runtime. Make sure your `java -version` reports 17+ before running.
- Windows package manager: `winget install OpenJDK17` or `winget install EclipseAdoptium.Temurin.17.JDK` then `winget install Apache.Maven`.
- macOS Homebrew: `brew install openjdk@17 maven`; Ubuntu/Debian: `sudo apt install openjdk-17-jdk maven`.
- Build from source (repo root): `mvn -q clean package` (first run downloads Maven deps to `~/.m2`).
- Run the app directly from Maven with the bundled exec plugin: `mvn -q exec:java` (uses the configured `Main` entry point).
- Or run the compiled classes with a Java 17+ runtime: `java -cp target/classes Main`.
- Run checks anytime with `mvn -q test`.

resources

- UML DIAGRAM: https://drive.google.com/file/d/1KDfn-AAJAlGAdKw20MNL4D0bn6htUuTp/view?usp=sharing
