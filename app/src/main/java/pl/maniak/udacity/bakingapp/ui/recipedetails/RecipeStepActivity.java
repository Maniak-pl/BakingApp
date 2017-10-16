package pl.maniak.udacity.bakingapp.ui.recipedetails;

import android.os.Bundle;
import android.view.MenuItem;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showDetailsStepFragment(bundle.getParcelableArrayList(BUNDLE_KEY_STEP), bundle.getInt(BUNDLE_KEY_STEP_ID));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDetailsStepFragment(List<Step> stepList, int stepId) {
        RecipeDetailsStepFragment fragment = (RecipeDetailsStepFragment) getSupportFragmentManager().findFragmentByTag(RecipeDetailsStepFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = RecipeDetailsStepFragment.newInstance(stepList, stepId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailStepContainer,
                            fragment,
                            RecipeDetailsStepFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void clear() {
    }
}