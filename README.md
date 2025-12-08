# Cafe-Simulator-Project

A friendly desktop cafe simulator where customers line up, you mix drinks in a simple UI, and receipts/sales grow as you serve them.

Features

- Mix coffee, milk, water, sugar, chocolate, and syrup to match recipes or experiment.
- Watch different customer types (regular, student, rich, karen) queue, chat, and order.
- See prices adjust if a drink doesn’t match the recipe, then view receipts and daily totals.
- Light JUnit checks keep the barista and drink logic behaving.

Install / run

- This project targets **Java 17** for both compilation and runtime. Make sure your `java -version` reports 17+ before running.
- Windows package manager: `winget install OpenJDK17` then `winget install Apache.Maven`.
- macOS Homebrew: `brew install openjdk@17 maven`; Ubuntu/Debian: `sudo apt install openjdk-17-jdk maven`.
- Build from source (repo root): `mvn -q clean package` (first run downloads Maven deps to `~/.m2`).
- Run the app directly from Maven with the bundled exec plugin: `mvn -q exec:java` (uses the configured `Main` entry point).
- Or run the compiled classes with a Java 17+ runtime: `java -cp target/classes Main`.
- Run checks anytime with `mvn -q test`.

resources

- UML DIAGRAM: https://drive.google.com/file/d/1KDfn-AAJAlGAdKw20MNL4D0bn6htUuTp/view?usp=sharing
