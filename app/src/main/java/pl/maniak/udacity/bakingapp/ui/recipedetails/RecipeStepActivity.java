package pl.maniak.udacity.bakingapp.ui.recipedetails;

import android.os.Bundle;

import java.util.List;

import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragment;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP_ID;

public class RecipeStepActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipe_step;
    }

    @Override
    protected void initDaggerComponent() {
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showDetailsStepFragment(bundle.getParcelableArrayList(BUNDLE_KEY_STEP), bundle.getInt(BUNDLE_KEY_STEP_ID));
        }
    }

    private void showDetailsStepFragment(List<Step> stepList, int currentId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailStepContainer,
                        RecipeDetailsStepFragment.newInstance(stepList, currentId))
                .commit();
    }

    @Override
    protected void clear() {
    }
}