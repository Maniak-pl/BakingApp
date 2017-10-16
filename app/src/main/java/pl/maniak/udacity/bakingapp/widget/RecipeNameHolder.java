package pl.maniak.udacity.bakingapp.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;

public class RecipeNameHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.widget_conf_label)
    TextView labelTv;

    private Recipe recipe;

    @Setter
    private WidgetConfAdapter.OnRecipeNameItemClickedListener onClickListener;

    RecipeNameHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    void setItem(Recipe recipe) {
        this.recipe = recipe;
        labelTv.setText(recipe.getName());
    }

    @OnClick(R.id.widget_conf_root)
    void onItemClicked() {
        if (onClickListener != null) {
            onClickListener.onRecipeNameItemClicked(recipe);
        }
    }
}