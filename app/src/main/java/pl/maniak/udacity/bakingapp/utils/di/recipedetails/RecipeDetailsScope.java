package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface RecipeDetailsScope {
}