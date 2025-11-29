
import java.model.Drink;
import java.util.ArrayList;
import java.util.List;
public class MixingGlass {
    private List<Ingredients> currentIngredients = new ArrayList<>();

    // call this when user clicks a button
    public void addIngredient(Ingredients ing) {
        currentIngredients.add(ing);
    }

    // call this when user clicks finish
    public Drink finishDrink(DrinkSize selectedSize) {
        DrinkType identifiedType = RecipeBook.identify(currentIngredients);
        Drink finalDrink = new Drink(identifiedType, selectedSize);
        currentIngredients.clear();
        return finalDrink;
    }
    
}