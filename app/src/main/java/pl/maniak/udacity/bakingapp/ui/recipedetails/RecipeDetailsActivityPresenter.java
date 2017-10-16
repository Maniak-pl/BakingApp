package pl.maniak.udacity.bakingapp.ui.recipedetails;

import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.data.Step;

public class RecipeDetailsActivityPresenter implements RecipeDetailsActivityContract.Presenter {

    private RecipeDetailsActivityContract.View view;
    private RecipeDetailsActivityContract.Router router;
    private boolean twoPaneMode;
    private Recipe recipe;

    @Override
    public void attachView(RecipeDetailsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(RecipeDetailsActivityContract.Router router) {
        this.router = router;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void detachRouter() {
        router = null;
    }

    @Override
    public void clear() {
        detachView();
        detachRouter();
    }

    @Override
    public void onRecipeStepItemClicked(Recipe recipe, int stepId) {
        if (twoPaneMode) {
            if (view != null) {
                view.showDetailsStepFragment(recipe.getSteps(), stepId);
            }
        } else {
            if (router != null) {
                router.navigateToRecipeStep(recipe, stepId);
            }
        }
    }

    @Override
    public void onActivityReady(Recipe recipe, boolean twoPaneMode) {
        this.twoPaneMode = twoPaneMode;
        this.recipe = recipe;

        if (view != null) {
            view.setActivityTitle(getName());
            view.showDetailsFragment();
            showDefaultDetailsStep();
        }
    }

    private void showDefaultDetailsStep() {
        if (twoPaneMode) {
            List<Step> stepList = getSteps();
            if (stepList != null && !stepList.isEmpty()) {
                view.showDetailsStepFragment(stepList, stepList.get(0).getId());
            }
        }
    }

    private String getName() {
        return recipe.getName();
    }

    private List<Step> getSteps() {
        return recipe.getSteps();
    }
}