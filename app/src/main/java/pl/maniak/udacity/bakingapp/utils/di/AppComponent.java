package pl.maniak.udacity.bakingapp.utils.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.maniak.udacity.bakingapp.api.RecipeInterface;
import pl.maniak.udacity.bakingapp.utils.di.network.NetworkModule;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {
    RecipeInterface getRecipeInterface();
}
