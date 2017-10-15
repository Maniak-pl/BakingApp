package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Component;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;

@RecipeDetailsScope
@Component(
        dependencies = AppComponent.class,
        modules = RecipeDetailsFragmentModule.class
)
public interface RecipeDetailsFragmentComponent {
    void inject(RecipeDetailsFragment recipeDetailsFragment);
}