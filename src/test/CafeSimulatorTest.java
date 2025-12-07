import CoffeeShop.CoffeeShop;
import CoffeeShop.Order;
import drinks.Drink;
import drinks.DrinkSize;
import drinks.DrinkType;
import drinks.Ingredients;
import entities.Barista;
import entities.Customer;
import mechanics.RecipeBook;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CafeSimulatorTest {

    private static class FixedCustomer extends Customer {
        private final Drink drink;

        FixedCustomer(String name, Drink drink) {
            this.name = name;
            this.dialogue = "Testing";
            this.drink = drink;
        }

        @Override
        public Drink orderDrink() {
            return drink;
        }

        @Override
        public int reactToDrink(Drink drink, String wanted) {
            return 0;
        }

        @Override
        public String getHappyReaction() {
            return "Great";
        }

        @Override
        public String getSadReaction() {
            return "Sad";
        }
    }

    @Test
    void drinkReflectsSizeForPriceAndCalories() {
        Drink base = new Drink(DrinkType.LATTE, DrinkSize.MEDIUM);
        Drink large = new Drink(DrinkType.LATTE, DrinkSize.LARGE);

        assertEquals(DrinkType.LATTE.getPrice(), base.getPrice());
        assertTrue(large.getPrice() > base.getPrice());
        assertTrue(large.getCalorie() > base.getCalorie());
    }

    @Test
    void recipeBookIdentifiesLatte() {
        List<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(Ingredients.COFFEE);
        ingredients.add(Ingredients.MILK);

        assertEquals(DrinkType.LATTE, RecipeBook.identify(ingredients));
    }

    @Test
    void spawnCustomerAddsOrderToQueue() {
        CoffeeShop shop = new CoffeeShop("Test");
        int startingQueue = shop.getOrders().size();

        Order created = shop.spawnCustomer();

        assertNotNull(created);
        assertEquals(startingQueue + 1, shop.getOrders().size());
    }

    @Test
    void baristaConsumesInventoryWhenAddingIngredients() {
        Barista barista = new Barista("Test");
        HashMap<String, Integer> inventory = new HashMap<>();
        for (Ingredients ing : Ingredients.values()) {
            inventory.put(ing.name(), 1);
        }

        barista.addIngredient(Ingredients.COFFEE, inventory);

        assertEquals(0, inventory.get("COFFEE"));
    }

    @Test
    void servingOrderCompletesAndDequeues() {
        CoffeeShop shop = new CoffeeShop("Test");
        Drink latte = new Drink(DrinkType.LATTE, DrinkSize.MEDIUM);
        FixedCustomer customer = new FixedCustomer("Tester", latte);
        Order order = new Order(1, customer);

        shop.manageOrder("Enqueue", order);

        shop.getBarista().addIngredient(Ingredients.COFFEE, shop.getInventory());
        shop.getBarista().addIngredient(Ingredients.MILK, shop.getInventory());
        shop.serveDrink();

        assertEquals("Completed", order.getStatus());
        assertEquals(0, shop.getOrders().size());
    }
}