package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import java.util.List;

import pl.maniak.udacity.bakingapp.data.Ingredient;
import pl.maniak.udacity.bakingapp.data.Recipe;

public class RecipeDetailsFragmentPresenter implements RecipeDetailsFragmentContract.Presenter {

    private RecipeDetailsFragmentContract.View view;
    private RecipeDetailsFragmentContract.Router router;

    @Override
    public void attachView(RecipeDetailsFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(RecipeDetailsFragmentContract.Router router) {
        this.router = router;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void detachRouter() {
        router = null;
    }

    @Override
    public void clear() {
        detachView();
        detachRouter();
    }

    @Override
    public void onFragmentReady(Recipe recipe) {
        if (view != null) {
            view.showIngredients(preparationIngredientsList(recipe.getIngredients()));
        }
    }

    private String preparationIngredientsList(List<Ingredient> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients list: \n");
        for (Ingredient ingredient : list) {
            sb.append("\n\t* ")
                    .append(ingredient.getIngredient())
                    .append(" (")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(")");
        }
        return sb.toString();
    }
}