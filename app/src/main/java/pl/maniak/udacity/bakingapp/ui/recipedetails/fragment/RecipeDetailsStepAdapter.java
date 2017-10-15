package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Step;

public class RecipeDetailsStepAdapter extends RecyclerView.Adapter<StepHolder> {

    private final LayoutInflater inflater;
    private List<Step> stepList;

    @Setter
    OnStepItemClickedListener onClickListener;

    public RecipeDetailsStepAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.stepList = new ArrayList<>();
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_details_item, parent, false);
        StepHolder viewHolder = new StepHolder(view);
        viewHolder.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step step = stepList.get(position);
        holder.setItem(step);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public void updateStep(List<Step> list) {
        stepList = list;
        notifyDataSetChanged();
    }

    interface OnStepItemClickedListener {
        void onStepItemClicked(Step step);
    }
}