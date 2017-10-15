package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import dagger.Component;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepFragment;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;

@RecipeDetailsScope
@Component(
        dependencies = AppComponent.class,
        modules = RecipeDetailsStepFragmentModule.class
)
public interface RecipeDetailsStepFragmentComponent {
    void inject(RecipeDetailsStepFragment recipeDetailsStepFragment);
}