package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivity;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivityContract;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivityPresenter;

@Module
@RequiredArgsConstructor
public class RecipeDetailsActivityModule {

    private final RecipeDetailsActivity context;

    @Provides
    RecipeDetailsActivityContract.Presenter providePresenter() {
        return new RecipeDetailsActivityPresenter();
    }
}