package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Module;
import dagger.Provides;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragmentContract;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragmentPresenter;

@Module
public class RecipeDetailsStepFragmentModule {

    @Provides
    RecipeDetailsStepFragmentContract.Presenter providePresenter() {
        return new RecipeDetailsStepFragmentPresenter();
    }
}