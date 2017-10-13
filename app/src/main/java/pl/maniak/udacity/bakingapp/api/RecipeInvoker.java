package pl.maniak.udacity.bakingapp.api;


import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;

public class RecipeInvoker {

    private final RecipeInterface recipeInterface;

    public RecipeInvoker(RecipeInterface recipeInterface) {
        this.recipeInterface = recipeInterface;
    }

    public List<Recipe> getRecipe() {
        return recipeInterface.getRecipe();
    }
}
