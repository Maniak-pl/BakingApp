package pl.maniak.udacity.bakingapp.ui.recipelist.recipe;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;

class RecipeHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_item_icon)
    ImageView image;

    @BindView(R.id.recipe_item_label)
    TextView label;

    private final Context context;
    private Recipe recipe;

    @Setter
    private RecipeAdapter.OnRecipeItemClickedListener onClickListener;

    RecipeHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    void setItem(Recipe recipe) {
        this.recipe = recipe;
        label.setText(recipe.getName());
        setImage(recipe.getImage());
    }

    private void setImage(String imageUrl) {
        if(imageUrl != null && !imageUrl.isEmpty()){
            Glide.clear(image);
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
        }
    }

    @OnClick(R.id.recipe_item_root)
    void onItemClicked() {
        if (onClickListener != null) {
            onClickListener.onRecipeItemClicked(recipe);
        }
    }
}