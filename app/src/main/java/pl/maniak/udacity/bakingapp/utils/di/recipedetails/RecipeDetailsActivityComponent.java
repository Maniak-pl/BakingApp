package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Component;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivity;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;

@RecipeDetailsScope
@Component(
        dependencies = AppComponent.class,
        modules = RecipeDetailsActivityModule.class
)
public interface RecipeDetailsActivityComponent {
    void inject(RecipeDetailsActivity recipeDetailsActivity);
}