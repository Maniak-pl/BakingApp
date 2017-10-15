package pl.maniak.udacity.bakingapp.ui.recipelist.recipe;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;

class RecipeHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_item_label)
    TextView label;

    private Recipe recipe;

    @Setter
    private RecipeAdapter.OnRecipeItemClickedListener onClickListener;

    RecipeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void setItem(Recipe recipe) {
        this.recipe = recipe;
        label.setText(recipe.getName());
    }

    @OnClick(R.id.recipe_item_root)
    void onItemClicked() {
        if (onClickListener != null) {
            onClickListener.onRecipeItemClicked(recipe);
        }
    }
}