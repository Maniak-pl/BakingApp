package pl.maniak.udacity.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.maniak.udacity.bakingapp.data.Recipe;
import pl.maniak.udacity.bakingapp.db.PreferencesHelper;
import pl.maniak.udacity.bakingapp.ui.recipelist.RecipeListActivity;
import pl.maniak.udacity.bakingapp.utils.Constants;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityIntentTest {

    private Recipe EXTRA_RECIPE;

 @Rule
    public IntentsTestRule<RecipeListActivity> intentsTestRule
         = new IntentsTestRule<>(RecipeListActivity.class);

    @Before
    public void setup() {
        PreferencesHelper helper = new PreferencesHelper();
        EXTRA_RECIPE = helper.getRecipeList().get(0);
    }

    @Test
    public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {
        onView(ViewMatchers.withId(R.id.recipe_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(
                hasExtra(Constants.BUNDLE_KEY_RECIPE, EXTRA_RECIPE)
        );
    }
}