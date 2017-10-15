package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.App;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseFragment;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.DaggerRecipeDetailsFragmentComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.RecipeDetailsFragmentModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;

public class RecipeDetailsFragment extends BaseFragment implements RecipeDetailsFragmentContract.View, RecipeDetailsFragmentContract.Router {

    @BindView(R.id.recipe_details_ingredients)
    TextView ingredientsLabel;

    @BindView(R.id.recipe_details_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RecipeDetailsFragmentContract.Presenter presenter;

    @Inject
    RecipeDetailsStepAdapter adapter;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    private Recipe recipe;

    @Setter
    private OnStepClickedCallback onStepClickedCallback;

    public RecipeDetailsFragment() {

    }

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_RECIPE, recipe);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = getArguments().getParcelable(BUNDLE_KEY_RECIPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recipe_details;
    }

    @Override
    protected void init() {
        initPresenter();
        initRecycler();
        presenter.onFragmentReady(recipe);
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnClickListener(step -> presenter.onStepItemClicked(recipe, step.getId()));
        recyclerView.setAdapter(adapter);
        adapter.updateStep(recipe.getSteps());
    }

    @Override
    protected void initDaggerComponent() {
        DaggerRecipeDetailsFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .recipeDetailsFragmentModule(new RecipeDetailsFragmentModule(this))
                .build()
                .inject(this);
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
    public void showIngredients(String ingredients) {
        ingredientsLabel.setText(ingredients);
    }

    @Override
    public void navigateToRecipeStep(Recipe recipe, int stepId) {
        if (onStepClickedCallback != null) {
            onStepClickedCallback.onStepClicked(recipe, stepId);
        }
    }

    public interface OnStepClickedCallback {
        void onStepClicked(Recipe recipe, int stepId);
    }
}