package mechanics;

import drinks.*;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class  RecipeBook {
    public static DrinkType identify(List<Ingredients> ingredients) {
        Map<String, Integer> counts = new HashMap<>();
        for (Ingredients ing : Ingredients.values()) {
            counts.put(ing.name(), 0);
        }
        for (Ingredients ing : ingredients) {
            counts.put(ing.name(), counts.getOrDefault(ing.name(), 0) + 1);
        }

        for (DrinkType type : DrinkType.values()) {
            if (matchesRecipe(counts, type.getRecipe())) {
                return type;
            }
        }
        return DrinkType.COFFEEofSADNESSandGRIEF;
    }

    private static boolean matchesRecipe(Map<String, Integer> counts, HashMap<String, Integer> recipe) {
        Set<String> keys = new HashSet<>(counts.keySet());
        keys.addAll(recipe.keySet());

        for (String key : keys) {
            int expected = recipe.getOrDefault(key, 0);
            int actual = counts.getOrDefault(key, 0);
            if (actual != expected) {
                return false;
            }
        }
        return true;
    }
}
