package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragmentContract;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragmentPresenter;

@Module
@RequiredArgsConstructor
public class RecipeDetailsFragmentModule {

    private final RecipeDetailsFragment context;

    @Provides
    RecipeDetailsFragmentContract.Presenter providePresenter() {
        return new RecipeDetailsFragmentPresenter();
    }
}