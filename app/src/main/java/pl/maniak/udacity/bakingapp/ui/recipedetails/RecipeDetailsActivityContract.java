package pl.maniak.udacity.bakingapp.ui.recipedetails;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeDetailsActivityContract {

    interface View extends BaseContract.View {
        void setActivityTitle(String name);
        void showDetailsFragment();
    }

    interface Router extends BaseContract.Router {
        void navigateToRecipeStep(Recipe recipe);
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onRecipeStepItemClicked(Recipe recipe);
        void onActivityReady(Recipe recipe);
    }
}