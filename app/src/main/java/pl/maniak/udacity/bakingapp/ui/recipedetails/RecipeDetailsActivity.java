package pl.maniak.udacity.bakingapp.ui.recipedetails;

import javax.inject.Inject;

import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
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
        if(getIntent().getExtras() != null) {
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
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void showDetailsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.detailContainer, RecipeDetailsFragment.newInstance(recipe)).commit();
    }

    @Override
    public void navigateToRecipeStep(Recipe recipe) {
    }
}