package mechanics;

import drinks.*;
import java.util.List;

public class  RecipeBook {
    public static DrinkType identify(List<Ingredients> ingredients) {
        boolean hasCoffee = ingredients.contains(Ingredients.COFFEE);
        boolean hasMilk = ingredients.contains(Ingredients.MILK);
        boolean hasWater = ingredients.contains(Ingredients.WATER);
        boolean hasSugar = ingredients.contains(Ingredients.SUGAR);
        boolean hasChocolate = ingredients.contains(Ingredients.CHOCOLATE);
        boolean hasSyrup = ingredients.contains(Ingredients.SYRUP);
        boolean hasCaramel = ingredients.contains(Ingredients.CARAMEL);

        if(hasMilk && hasCoffee && ingredients.size() == 2){
            return DrinkType.LATTE;
        } else if (hasCoffee && hasWater && ingredients.size() == 2){
            return DrinkType.AMERICANO;
        } else if (hasMilk && hasCoffee && hasWater && ingredients.size() == 3){
            return DrinkType.CAPPUCCINO;
        } else if (hasCoffee && hasMilk && hasSugar && hasChocolate && ingredients.size() == 4){
            return DrinkType.MOCHA;
        } else {
            return DrinkType.COFFEEofSADNESSandGRIEF;
        }
    }
}