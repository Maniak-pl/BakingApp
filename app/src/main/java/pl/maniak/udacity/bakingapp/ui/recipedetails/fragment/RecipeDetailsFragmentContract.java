package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;


import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeDetailsFragmentContract {

    interface View extends BaseContract.View {
        void showIngredients(String ingredients);
    }

    interface Router extends BaseContract.Router {
        void navigateToRecipeStep(Recipe recipe, int stepId);
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onFragmentReady(Recipe recipe);

        void onStepItemClicked(Recipe recipe, int stepId);
    }
}