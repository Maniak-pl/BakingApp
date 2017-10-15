package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.ui.BaseFragment;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_RECIPE;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP;

public class RecipeDetailsStepFragment extends BaseFragment {

    @BindView(R.id.recipe_details_step_title)
    TextView title;

    @BindView(R.id.recipe_details_step_description)
    TextView description;

    private Recipe recipe;
    private int stepId;

    public static RecipeDetailsStepFragment newInstance(Recipe recipe, int stepId) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_RECIPE, recipe);
        bundle.putInt(BUNDLE_KEY_STEP, stepId);
        RecipeDetailsStepFragment fragment = new RecipeDetailsStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = getArguments().getParcelable(BUNDLE_KEY_RECIPE);
        stepId = getArguments().getInt(BUNDLE_KEY_STEP);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    protected void initDaggerComponent() {

    }

    @Override
    protected void init() {

        showStep();
    }

    private void showStep() {
        title.setText(getStep().getShortDescription());
        description.setText(getStep().getDescription());
    }

    @Override
    protected void clear() {

    }

    private Step getStep() {
        return recipe.getSteps().get(stepId);
    }

    @OnClick(R.id.recipe_details_step_previous)
    void previousButton() {
        if (stepId > 0) {
            stepId--;
            showStep();
        } else {
            showToast("You already are in the first step of the recipe");
        }
    }

    @OnClick(R.id.recipe_details_step_next)
    void nextButton() {
        int lastStep = recipe.getSteps().size() - 1;
        if (stepId < lastStep) {
            stepId++;
            showStep();
        } else {
            showToast("You already are in the last step of the recipe");
        }
    }
}