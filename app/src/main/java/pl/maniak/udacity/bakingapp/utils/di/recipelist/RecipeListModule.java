package pl.maniak.udacity.bakingapp.utils.di.recipelist;


import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.api.RecipeInterface;
import pl.maniak.udacity.bakingapp.ui.recipelist.RecipeListActivity;
import pl.maniak.udacity.bakingapp.ui.recipelist.RecipeListContract;
import pl.maniak.udacity.bakingapp.ui.recipelist.RecipeListPresenter;
import pl.maniak.udacity.bakingapp.ui.recipelist.recipe.RecipeAdapter;

@Module
@RequiredArgsConstructor
public class RecipeListModule {

    private final RecipeListActivity context;

    @Provides
    RecipeListContract.Presenter providePresenter(RecipeInterface recipeInterface) {
        return RecipeListPresenter.builder()
                .recipeInterface(recipeInterface)
                .build();
    }

    @Provides
    RecipeAdapter provideRecipeAdapter() {
        return new RecipeAdapter(context);
    }
}
