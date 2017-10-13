package pl.maniak.udacity.bakingapp.api;

import java.util.List;

import pl.maniak.udacity.bakingapp.data.Recipe;
import retrofit.http.GET;

public interface RecipeInterface {

    @GET("/baking.json")
    List<Recipe> getRecipe();
}
