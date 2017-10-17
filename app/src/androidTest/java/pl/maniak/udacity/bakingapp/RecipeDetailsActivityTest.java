package pl.maniak.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.maniak.udacity.bakingapp.db.PreferencesHelper;
import pl.maniak.udacity.bakingapp.ui.recipedetails.RecipeDetailsActivity;
import pl.maniak.udacity.bakingapp.utils.Constants;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityTest {

    private static final int STEP_WITH_VIDEO = 0;
    private static final int STEP_WITHOUT_VIDEO = 1;

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> activityActivityTestRule =
            new ActivityTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context context = InstrumentationRegistry.getInstrumentation().getContext();
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    PreferencesHelper helper = new PreferencesHelper();
                    intent.putExtra(Constants.BUNDLE_KEY_RECIPE, helper.getRecipeList().get(0));

                    return intent;
                }
            };


    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {
        onView(withId(R.id.recipe_details_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_details_step_title))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithVideo_showsVideoPlayerView() {
        onView(withId(R.id.recipe_details_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITH_VIDEO, click()));

        onView(allOf(withId(R.id.recipe_details_step_player),
                withParent(withParent(withId(R.id.fragment_recipe_step_root))),
                isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithOutVideo_hidesVideoPlayerView() {
        onView(withId(R.id.recipe_details_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITHOUT_VIDEO, click()));

        onView(allOf(withId(R.id.recipe_details_step_player),
                withParent(withParent(withId(R.id.fragment_recipe_step_root))),
                isDisplayed()))
                .check(doesNotExist());
    }
}