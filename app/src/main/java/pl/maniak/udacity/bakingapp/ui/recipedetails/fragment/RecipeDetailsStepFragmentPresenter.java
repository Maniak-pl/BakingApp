package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.net.Uri;

import java.util.List;

import pl.maniak.udacity.bakingapp.data.Step;

public class RecipeDetailsStepFragmentPresenter implements RecipeDetailsStepFragmentContract.Presenter {

    private RecipeDetailsStepFragmentContract.View view;
    private RecipeDetailsStepFragmentContract.Router router;

    private List<Step> stepList;
    private int currentStep;

    @Override
    public void attachView(RecipeDetailsStepFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRouter(RecipeDetailsStepFragmentContract.Router router) {
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
        detachRouter();
        detachView();
    }

    @Override
    public void onNextButtonClicked() {
        currentStep++;
        showDetailStep();
    }

    @Override
    public void onPreviousButtonClicked() {
        currentStep--;
        showDetailStep();
    }

    @Override
    public void onFragmentReady(List<Step> stepList, int currentStep) {
        this.stepList = stepList;
        this.currentStep = currentStep;
        showDetailStep();
    }

    @Override
    public void onFragmentPause() {
        if(view != null) {
            view.releasePlayer();
            view.saveCurrentStep(currentStep);
        }
    }

    private void showDetailStep() {
        if (view != null) {
            showNavigationButton();
            view.releasePlayer();
            showRecipeStepViedo();
            view.showTitleDetailStep(getStep().getShortDescription());
            view.showDescriptionDetailStep(getStep().getDescription());
        }
    }

    private void showRecipeStepViedo() {
        String video = getStep().getVideoURL();
        if(video != null && !video.isEmpty()) {
            view.showExoPlayer(true);
            view.initVideo(Uri.parse(video));
        } else {
            view.showExoPlayer(false);
        }
    }

    private void showNavigationButton() {
        int lastStep = stepList.size() - 1;
        if (currentStep == 0) {
            view.showNavigationButton(false, true);
        } else if (currentStep == lastStep) {
            view.showNavigationButton(true, false);
        } else {
            view.showNavigationButton(true, true);
        }
    }

    private Step getStep() {
        return stepList.get(currentStep);
    }
}
