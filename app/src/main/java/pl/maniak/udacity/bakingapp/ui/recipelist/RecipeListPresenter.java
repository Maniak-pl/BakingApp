package pl.maniak.udacity.bakingapp.ui.recipelist;


import android.os.AsyncTask;

import java.util.List;

import lombok.Builder;
import pl.maniak.udacity.bakingapp.api.RecipeInterface;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.db.PreferencesHelper;

@Builder
public class RecipeListPresenter implements RecipeListContract.Presenter {

    private RecipeInterface recipeInterface;
    private PreferencesHelper helper;
    private RecipeListContract.View view;
    private RecipeListContract.Router router;

    @Override
    public void attachView(RecipeListContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(RecipeListContract.Router router) {
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
    public void onRecipeItemClicked(Recipe recipe) {
        if (router != null) {
            router.navigateToRecipeDetail(recipe);
        }
    }

    @Override
    public void getRecipeList() {
        new RecipeTask().execute();
    }

    private class RecipeTask extends AsyncTask<Void, Void, List<Recipe>> {
        @Override
        protected List<Recipe> doInBackground(Void... params) {
            return recipeInterface.getRecipe();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            if (recipes != null && view != null) {
                helper.saveRecipeList(recipes);
                view.showDownloadCompleted();
                view.showRecipeList(recipes);
                view.hideProgress();
            }
        }
    }
}
