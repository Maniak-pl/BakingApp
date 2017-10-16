package pl.maniak.udacity.bakingapp.db;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;

import static pl.maniak.udacity.bakingapp.utils.Constants.PREFS_RECIPES;
import static pl.maniak.udacity.bakingapp.utils.Constants.PREFS_RECIPE_CHOSEN;

public class PreferencesHelper {

    public void saveRecipeList(List<Recipe> recipeList) {
        Hawk.put(PREFS_RECIPES, recipeList);
    }

    public List<Recipe> getRecipeList() {
        return Hawk.get(PREFS_RECIPES, new ArrayList<>());
    }

    public Recipe getChosenRecipe(int suffix) {
        return Hawk.get(PREFS_RECIPE_CHOSEN + suffix, null);
    }

    public void saveChosenRecipe(int suffix, Recipe recipe) {
        Hawk.put(PREFS_RECIPE_CHOSEN + suffix, recipe);
    }

    public void deleteChosenRecipe(int suffix) {
        Hawk.delete(PREFS_RECIPE_CHOSEN + suffix);
    }
}