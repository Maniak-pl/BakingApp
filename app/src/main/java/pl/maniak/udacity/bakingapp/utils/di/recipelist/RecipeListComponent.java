package pl.maniak.udacity.bakingapp.utils.di.recipelist;

import dagger.Component;
import pl.maniak.udacity.bakingapp.ui.recipelist.RecipeListActivity;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;

@RecipeListScope
@Component(
        dependencies = AppComponent.class,
        modules = RecipeListModule.class
)
public interface RecipeListComponent {
    void inject(RecipeListActivity recipeListActivity);
}
