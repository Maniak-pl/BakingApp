package pl.maniak.udacity.bakingapp.ui.recipelist.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
    private final LayoutInflater inflater;
    private List<Recipe> recipeList;

    @Setter
    private OnRecipeItemClickedListener onClickListener;

    public RecipeAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.recipeList = new ArrayList<>();
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        RecipeHolder viewHolder = new RecipeHolder(view);
        viewHolder.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.setItem(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateRecipe(List<Recipe> list) {
        recipeList = list;
        notifyDataSetChanged();
    }

    public interface OnRecipeItemClickedListener {
        void onRecipeItemClicked(Recipe recipe);
    }
}