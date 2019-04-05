package pibesprojects.workouttracker;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.Menu;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsAnything.anything;
import static pibesprojects.workouttracker.ChooseRepsAndSetsTest.withIndex;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class SWITS {
//TODO on some devices tests may fail because Espresso perform longClick instead of clikc...
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createOneWorkoutEntryWithOneSet_ChecksIfProperEntryWasAdded() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Context context = activity.getApplicationContext();
        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_add));
        intended(hasComponent(pibesprojects.workouttracker.ChooseBodyPart.class.getName()));

        onView(withId(R.id.absButton)).perform(click());
        onView(withText(R.string.CableChops)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());
        pressBack();

        onView(withIndex(withId(R.id.numberOfSets), 0)).check(matches(withSubstring("1")));
        onView(withIndex(withId(R.id.numberOfReps), 0)).check(matches(withSubstring("2")));
        onView(withIndex(withId(R.id.weight), 0)).check(matches(withSubstring("5.0")));
        onView(withIndex(withId(R.id.workoutName), 0)).check(matches(withSubstring(context.getString(R.string.CableChops))));


        Intents.release();
    }

    @Test
    public void createOneWorkoutEntryWithTwoSets_ChecksIfProperEntryWasAdded() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Context context = activity.getApplicationContext();

        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_add));
        intended(hasComponent(pibesprojects.workouttracker.ChooseBodyPart.class.getName()));

        onView(withId(R.id.absButton)).perform(click());
        onView(withText(R.string.CableChops)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.SaveButton)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(5).perform(click());

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(20).perform(click());
        onView(withId(R.id.SaveButton)).perform(click());

        pressBack();

        onView(withIndex(withId(R.id.numberOfSets), 0)).check(matches(withSubstring("2")));
        onView(withIndex(withId(R.id.numberOfReps), 0)).check(matches(withSubstring("2 6")));
        onView(withIndex(withId(R.id.weight), 0)).check(matches(withSubstring("5.0 10.0")));
        onView(withIndex(withId(R.id.workoutName), 0)).check(matches(withSubstring(context.getString(R.string.CableChops))));

        Intents.release();
    }

    @Test
    public void createTwoWorkoutEntryWithOneSets_ChecksIfProperEntryWasAdded() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Context context = activity.getApplicationContext();

        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_add));
        onView(withId(R.id.absButton)).perform(click());
        onView(withText(R.string.TRXPushup)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(9).perform(click());

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.SaveButton)).perform(click());
        pressBack();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_add));

        onView(withId(R.id.absButton)).perform(click());
        onView(withText(R.string.CableChops)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.SaveButton)).perform(click());
        pressBack();
        onView(withIndex(withId(R.id.numberOfSets), 1)).check(matches(withSubstring("1")));
        onView(withIndex(withId(R.id.numberOfReps), 1)).check(matches(withSubstring("2")));
        onView(withIndex(withId(R.id.weight), 1)).check(matches(withSubstring("5.0")));
        onView(withIndex(withId(R.id.workoutName), 1)).check(matches(withSubstring(context.getString(R.string.CableChops))));

        onView(withIndex(withId(R.id.numberOfSets), 0)).check(matches(withSubstring("1")));
        onView(withIndex(withId(R.id.numberOfReps), 0)).check(matches(withSubstring("10")));
        onView(withIndex(withId(R.id.weight), 0)).check(matches(withSubstring("0.0")));
        Log.v("Debug", "onActivityResult date: ");
        onView(withIndex(withId(R.id.workoutName), 0)).check(matches(withSubstring(context.getString(R.string.TRXPushup))));

        Intents.release();
    }
}
