package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.utils.helpers.StringHelpers;

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
            view.showIngredients(StringHelpers.preparationIngredientsList("Ingredients list: ", recipe.getIngredients()));
        }
    }

    @Override
    public void onStepItemClicked(Recipe recipe, int stepId) {
        if (router != null) {
            router.navigateToRecipeStep(recipe, stepId);
        }
    }
}