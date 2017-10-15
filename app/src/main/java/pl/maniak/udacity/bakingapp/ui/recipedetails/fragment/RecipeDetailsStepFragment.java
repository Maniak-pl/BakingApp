package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pl.maniak.udacity.bakingapp.App;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.ui.BaseFragment;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.DaggerRecipeDetailsStepFragmentComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.RecipeDetailsStepFragmentModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP_ID;

public class RecipeDetailsStepFragment extends BaseFragment implements RecipeDetailsStepFragmentContract.View, RecipeDetailsStepFragmentContract.Router {

    @BindView(R.id.recipe_details_step_title)
    TextView titleTv;

    @BindView(R.id.recipe_details_step_description)
    TextView descriptionTv;

    @BindView(R.id.recipe_details_step_next)
    ImageView nextButton;

    @BindView(R.id.recipe_details_step_previous)
    ImageView previousButton;

    @Inject
    RecipeDetailsStepFragmentContract.Presenter presenter;

    public static RecipeDetailsStepFragment newInstance(List<Step> stepList, int stepId) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_KEY_STEP, (ArrayList<? extends Parcelable>) stepList);
        bundle.putInt(BUNDLE_KEY_STEP_ID, stepId);
        RecipeDetailsStepFragment fragment = new RecipeDetailsStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerRecipeDetailsStepFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .recipeDetailsStepFragmentModule(new RecipeDetailsStepFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initPresenter();

        List<Step> stepList = getArguments().getParcelableArrayList(BUNDLE_KEY_STEP);
        presenter.onFragmentReady(stepList, getArguments().getInt(BUNDLE_KEY_STEP_ID));
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRouter(this);
    }

    @Override
    public void showDescriptionDetailStep(String description) {
        descriptionTv.setText(description);
    }

    @Override
    public void showNavigationButton(boolean showPreviousButton, boolean showNextButton) {
        previousButton.setVisibility(showPreviousButton ? View.VISIBLE : View.INVISIBLE);
        nextButton.setVisibility(showNextButton ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showRecipeVideo() {

    }

    @Override
    public void showTitleDetailStep(String title) {
        titleTv.setText(title);
    }

    @Override
    protected void clear() {
        presenter.clear();
    }

    @OnClick(R.id.recipe_details_step_previous)
    void previousButton() {
        presenter.onPreviousButtonClicked();
    }

    @OnClick(R.id.recipe_details_step_next)
    void nextButton() {
        presenter.onNextButtonClicked();
    }
}