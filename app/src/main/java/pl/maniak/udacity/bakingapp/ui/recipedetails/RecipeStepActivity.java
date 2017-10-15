package pl.maniak.udacity.bakingapp.ui.recipedetails;

import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragment;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP;

public class RecipeStepActivity extends BaseActivity {

    private Recipe recipe;
    private int stepId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipe_step;
    }

    @Override
    protected void initDaggerComponent() { }

    @Override
    protected void init() {
        if (getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(BUNDLE_KEY_RECIPE);
            stepId = getIntent().getExtras().getInt(BUNDLE_KEY_STEP);
        }
        showDetailsStepFragment();
    }

    private void showDetailsStepFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailStepContainer,
                        RecipeDetailsStepFragment.newInstance(recipe, stepId))
                .commit();
    }

    @Override
    protected void clear() { }
}