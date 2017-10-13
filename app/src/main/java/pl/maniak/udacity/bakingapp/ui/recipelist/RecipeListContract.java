package pl.maniak.udacity.bakingapp.ui.recipelist;


import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeListContract {

    interface View extends BaseContract.View {
        void showRecipeList(List<Recipe> recipes);
    }

    interface Router extends BaseContract.Router {
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onRecipeItemClicked();
        void onActivityReady();
    }
}
