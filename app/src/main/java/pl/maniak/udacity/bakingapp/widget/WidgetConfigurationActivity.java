package pl.maniak.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.db.PreferencesHelper;
import pl.maniak.udacity.bakingapp.ui.BaseActivity;
import pl.maniak.udacity.bakingapp.utils.di.widget.DaggerWidgetConfigurationComponent;
import pl.maniak.udacity.bakingapp.utils.di.widget.WidgetConfigurationModule;

public class WidgetConfigurationActivity extends BaseActivity {

    @Inject
    PreferencesHelper helper;

    @Inject
    WidgetConfAdapter adapter;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.configuration_widget_recycler_view)
    RecyclerView recyclerView;

    @BindString(R.string.widget_config_no_data)
    String noDataErrorMessage;


    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_configuration_widget;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerWidgetConfigurationComponent.builder()
                .appComponent(getAppComponent())
                .widgetConfigurationModule(new WidgetConfigurationModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }

        initRecycler(getRecipeList());
    }

    private List<Recipe> getRecipeList() {
        List<Recipe> recipeList = helper.getRecipeList();
        if (recipeList != null && recipeList.size() == 0) {
            Toast.makeText(this, noDataErrorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
        return recipeList;
    }

    private void initRecycler(List<Recipe> list) {
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnClickListener(this::selectedRecipe);
        recyclerView.setAdapter(adapter);
        adapter.updateRecipe(list);
    }

    private void selectedRecipe(Recipe recipe) {
        helper.saveChosenRecipe(widgetId, recipe);

        Context context = getApplicationContext();
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        WidgetProvider.updateWidgetContent(context, manager, widgetId, recipe);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void clear() {
    }
}