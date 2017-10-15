package pl.maniak.udacity.bakingapp.ui.recipelist;


import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeListContract {

    interface View extends BaseContract.View {
        void showRecipeList(List<Recipe> recipes);
        void hideProgress();
    }

    interface Router extends BaseContract.Router {
        void navigateToRecipeDetail(Recipe recipe);
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onRecipeItemClicked(Recipe recipe);
        void getRecipeList();
    }
}
