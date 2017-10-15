package pl.maniak.udacity.bakingapp.utils.di.recipedetails;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragment;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragmentContract;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsFragmentPresenter;
import pl.maniak.udacity.bakingapp.ui.recipedetails.fragment.RecipeDetailsStepAdapter;

@Module
@RequiredArgsConstructor
public class RecipeDetailsFragmentModule {

    private final RecipeDetailsFragment fragment;

    @Provides
    RecipeDetailsFragmentContract.Presenter providePresenter() {
        return new RecipeDetailsFragmentPresenter();
    }

    @Provides
    RecipeDetailsStepAdapter provideRecipeDetailsStepAdapter() {
        return new RecipeDetailsStepAdapter(fragment.getContext());
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(fragment.getContext());
    }
}