package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Step;

public class StepHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_step_item_label)
    TextView label;

    private Step step;

    @Setter
    private RecipeDetailsStepAdapter.OnStepItemClickedListener onClickListener;

    public StepHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void setItem(Step step) {
        this.step = step;
        label.setText(step.getShortDescription());
    }

    @OnClick(R.id.recipe_step_item_root)
    void onItemClicked() {
        if (onClickListener != null) {
            onClickListener.onStepItemClicked(step);
        }
    }
}