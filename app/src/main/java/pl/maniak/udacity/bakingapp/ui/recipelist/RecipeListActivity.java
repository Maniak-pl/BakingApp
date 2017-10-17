package pl.maniak.udacity.bakingapp.ui.recipelist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.data.RecipesIdlingResource;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivity;
import pl.maniak.udacity.bakingapp.ui.recipelist.recipe.RecipeAdapter;
import pl.maniak.udacity.bakingapp.utils.di.recipelist.DaggerRecipeListComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipelist.RecipeListModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;

public class RecipeListActivity extends BaseActivity implements RecipeListContract.View, RecipeListContract.Router {

    @BindView(R.id.recipe_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recipe_list_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.recipe_list_download_completed)
    String downloadCompletedMessage;

    @Inject
    RecipeAdapter adapter;

    @Inject
    RecipeListContract.Presenter presenter;

    @Nullable
    private RecipesIdlingResource idlingResource;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipe_list;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerRecipeListComponent.builder()
                .appComponent(getAppComponent())
                .recipeListModule(new RecipeListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initPresenter();
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnClickListener(recipe -> presenter.onRecipeItemClicked(recipe));
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getRecipeList());
        recyclerView.setAdapter(adapter);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 800;
        int column = displayMetrics.widthPixels / widthDivider;
        return column < 1 ? 1 : column;
    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityReady();
    }

    private void onActivityReady() {
        if (isOnline()) {
            showProgress();
            presenter.getRecipeList();
        } else {
            showToast(getString(R.string.toast_connection_error));
        }
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRouter(this);
    }

    @Override
    protected void clear() {
        presenter.clear();
    }

    @Override
    public void navigateToRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(BUNDLE_KEY_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void showRecipeList(List<Recipe> recipes) {
        updateRecipes(recipes);
    }

    @Override
    public void showDownloadCompleted() {
        showToast(downloadCompletedMessage);
    }

    private void showProgress() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void updateRecipes(List<Recipe> recipes) {
        if (recipes != null && adapter != null) {
            adapter.updateRecipe(recipes);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new RecipesIdlingResource();
        }
        return idlingResource;
    }
}
