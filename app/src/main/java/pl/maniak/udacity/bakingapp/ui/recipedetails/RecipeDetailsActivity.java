package pl.maniak.udacity.bakingapp.ui.recipedetails;

import android.content.Intent;
import android.support.v7.app.ActionBar;

import javax.inject.Inject;

import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
import pl.maniak.udacity.bakingapp.utils.Constants;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.DaggerRecipeDetailsActivityComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.RecipeDetailsActivityModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsActivityContract.View, RecipeDetailsActivityContract.Router {

    @Inject
    RecipeDetailsActivityContract.Presenter presenter;

    private Recipe recipe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipe_details;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerRecipeDetailsActivityComponent.builder()
                .appComponent(getAppComponent())
                .recipeDetailsActivityModule(new RecipeDetailsActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initPresenter();
        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(BUNDLE_KEY_RECIPE);
            presenter.onActivityReady(recipe);
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
    public void setActivityTitle(String name) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(name);
        }
    }

    @Override
    public void showDetailsFragment() {
        RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipe);
        fragment.setOnStepClickedCallback(presenter::onRecipeStepItemClicked);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, fragment)
                .commit();
    }

    @Override
    public void navigateToRecipeStep(Recipe recipe, int stepId) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_RECIPE, recipe);
        intent.putExtra(Constants.BUNDLE_KEY_STEP, stepId);
        startActivity(intent);
    }
}