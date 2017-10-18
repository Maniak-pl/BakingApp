package pl.maniak.udacity.bakingapp.ui.recipedetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindBool;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.data.events.NavigateEvent;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragment;
import pl.maniak.udacity.bakingapp.utils.Constants;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.DaggerRecipeDetailsActivityComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.RecipeDetailsActivityModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsActivityContract.View, RecipeDetailsActivityContract.Router {

    @BindBool(R.bool.two_pane_mode)
    boolean twoPaneMode;

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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initPresenter();
        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(BUNDLE_KEY_RECIPE);
            if (savedInstanceState == null) {
                presenter.onActivityReady(recipe, twoPaneMode);
            }
        }
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRouter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, fragment)
                .commit();
    }

    @Override
    public void showDetailsStepFragment(List<Step> stepList, int stepId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailStepContainer,
                        RecipeDetailsStepFragment.newInstance(stepList, stepId))
                .commit();
    }

    @Override
    public void navigateToRecipeStep(Recipe recipe, int stepId) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putParcelableArrayListExtra(Constants.BUNDLE_KEY_STEP, (ArrayList<? extends Parcelable>) recipe.getSteps());
        intent.putExtra(Constants.BUNDLE_KEY_STEP_ID, stepId);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NavigateEvent event) {
        presenter.onRecipeStepItemClicked(event.getRecipe(),event.getStepId());
    }
}