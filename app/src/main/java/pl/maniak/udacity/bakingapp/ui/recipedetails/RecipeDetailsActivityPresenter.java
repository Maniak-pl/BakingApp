package pl.maniak.udacity.bakingapp.ui.recipedetails;

import android.util.Log;

import pl.maniak.udacity.bakingapp.data.Recipe;

public class RecipeDetailsActivityPresenter implements RecipeDetailsActivityContract.Presenter {

    private RecipeDetailsActivityContract.View view;
    private RecipeDetailsActivityContract.Router router;

    @Override
    public void attachView(RecipeDetailsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(RecipeDetailsActivityContract.Router router) {
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
    public void onRecipeStepItemClicked(Recipe recipe) {
        Log.e("Maniak", "RecipeDetailsActivityPresenter.onRecipeStepItemClicked(): ");
    }

    @Override
    public void onActivityReady(Recipe recipe) {
        if(view != null) {
            view.setActivityTitle(recipe.getName());
            view.showDetailsFragment();
        }
    }
}