package pl.maniak.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import pl.maniak.udacity.bakingapp.App;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.db.PreferencesHelper;
import pl.maniak.udacity.bakingapp.utils.helpers.StringHelpers;

public class WidgetProvider extends AppWidgetProvider {

    PreferencesHelper helper;

    public static void updateWidgetContent(Context context, AppWidgetManager manager,
                                           int widgetId, Recipe recipe) {

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_ingredients_list);
        if (recipe != null) {
            views.setTextViewText(R.id.widget_ingredients_container,
                    StringHelpers.preparationIngredientsList(recipe.getName(),
                            recipe.getIngredients()));
        }
        manager.updateAppWidget(widgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        helper = App.getAppComponent().getPreferencesHelper();

        for (int widgetId : appWidgetIds) {
            Recipe recipe = helper.getChosenRecipe(widgetId);
            WidgetProvider.updateWidgetContent(context, appWidgetManager, widgetId, recipe);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        helper = App.getAppComponent().getPreferencesHelper();
        for (int widgetId : appWidgetIds) {
            helper.deleteChosenRecipe(widgetId);
        }
    }
}