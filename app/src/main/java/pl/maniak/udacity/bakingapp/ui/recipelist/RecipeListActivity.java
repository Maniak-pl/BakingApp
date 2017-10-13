package pl.maniak.udacity.bakingapp.ui.recipelist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.utils.di.recipelist.DaggerRecipeListComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipelist.RecipeListModule;

public class RecipeListActivity extends BaseActivity implements RecipeListContract.View, RecipeListContract.Router {

    @BindView(R.id.recipe_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recipe_list_progress_bar)
    SmoothProgressBar progressBar;

    @Inject
    RecipeListContract.Presenter presenter;

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


    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityReady();
    }

    private void onActivityReady() {
        if(isOnline()) {
            presenter.onActivityReady();
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
        Log.e("Maniak", "RecipeListActivity.showRecipeList: "+recipes);
    }


}
