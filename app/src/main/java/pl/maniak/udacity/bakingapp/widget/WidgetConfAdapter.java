package pl.maniak.udacity.bakingapp.widget;

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

public class WidgetConfAdapter extends RecyclerView.Adapter<RecipeNameHolder> {

    private final LayoutInflater inflater;
    private List<Recipe> recipeList;

    @Setter
    private OnRecipeNameItemClickedListener onClickListener;

    public WidgetConfAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.recipeList = new ArrayList<>();
    }

    @Override
    public RecipeNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.widget_conf_item, parent, false);
        RecipeNameHolder holder = new RecipeNameHolder(view);
        holder.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecipeNameHolder holder, int position) {
        holder.setItem(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateRecipe(List<Recipe> list) {
        recipeList = list;
        notifyDataSetChanged();
    }

    public interface OnRecipeNameItemClickedListener {
        void onRecipeNameItemClicked(Recipe recipe);
    }
}