package pl.maniak.udacity.bakingapp.ui.recipelist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipelist.recipe.RecipeAdapter;

import pl.maniak.udacity.bakingapp.utils.di.recipelist.DaggerRecipeListComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipelist.RecipeListModule;

public class RecipeListActivity extends BaseActivity implements RecipeListContract.View, RecipeListContract.Router {

    @BindView(R.id.recipe_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recipe_list_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    RecipeAdapter adapter;

    @Inject
    RecipeListContract.Presenter presenter;

    private List<Recipe> list;

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
        adapter.setOnClickListener(recipe -> presenter.onRecipeItemClicked());
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getRecipeList());
        recyclerView.setAdapter(adapter);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 800;
        int width = displayMetrics.widthPixels;
        return width / widthDivider;
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
    public void navigateToRecipeDetail() {

    }

    @Override
    public void showRecipeList(List<Recipe> recipes) {
        updateRecipes(recipes);
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
        if (recipes != null) {
            list = recipes;
            if (adapter != null) {
                adapter.updateRecipe(list);
            }
        }
    }
}
