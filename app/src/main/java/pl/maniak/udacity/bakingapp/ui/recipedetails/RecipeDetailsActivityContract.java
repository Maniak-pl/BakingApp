package pl.maniak.udacity.bakingapp.ui.recipedetails;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeDetailsActivityContract {

    interface View extends BaseContract.View {
        void setActivityTitle(String name);
        void showDetailsFragment();
    }

    interface Router extends BaseContract.Router {
        void navigateToRecipeStep(Recipe recipe, int stepId);
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onRecipeStepItemClicked(Recipe recipe, int stepId);
        void onActivityReady(Recipe recipe);
    }
}