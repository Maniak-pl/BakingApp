package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.net.Uri;

import java.util.List;

import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.ui.BaseContract;

public interface RecipeDetailsStepFragmentContract {

    interface View extends BaseContract.View {
        void showTitleDetailStep(String title);
        void showDescriptionDetailStep(String description);
        void showNavigationButton(boolean showPreviousButton, boolean showNextButton);
        void showExoPlayer(boolean showPlayer);
        void initVideo(Uri mediaUri);
        void releasePlayer();
        void saveCurrentStep(int currentStep);
    }

    interface Router extends BaseContract.Router {
    }

    interface Presenter extends BaseContract.Presenter<View, Router> {
        void onNextButtonClicked();
        void onPreviousButtonClicked();
        void onFragmentReady(List<Step> list, int stepId);
        void onFragmentResume();
        void onFragmentPause();
    }
}