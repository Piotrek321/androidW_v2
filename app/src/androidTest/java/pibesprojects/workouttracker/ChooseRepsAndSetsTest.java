package pibesprojects.workouttracker;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static pibesprojects.workouttracker.Helpers.withIndex;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ChooseRepsAndSetsTest {
    @Rule
    public ActivityTestRule<ChooseRepsAndSets> rule = new ActivityTestRule<>(ChooseRepsAndSets.class);

    @Test
    public void onCreate_DefaultSetup_EnsureSpinnersValuesAreSetToDefaultValues()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();

        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));
        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
    }

    @UiThreadTest
    @Test
    public void onCreate_TestPlusAndMinusButtons_EnsureSpinnersReactsToButtonsProperly()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();
        ImageButton minusButtonReps = chooseRepsAndSets.findViewById(R.id.minusButtonReps);
        ImageButton plusButtonReps = chooseRepsAndSets.findViewById(R.id.plusButtonReps);
        ImageButton minusButtonWeight = chooseRepsAndSets.findViewById(R.id.minusButtonWeight);
        ImageButton plusButtonWeight = chooseRepsAndSets.findViewById(R.id.plusButtonWeight);

        //Reps should remain 1
        minusButtonReps.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));

        //Reps should rise to 2
        plusButtonReps.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(2, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));

        //Reps should remain 1
        minusButtonReps.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));


        //Weight should remain 0
        minusButtonWeight.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));

        //Weight should raise to 1
        plusButtonWeight.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(chooseRepsAndSets.spinnerWeight.getSelectedItem().toString(), equalTo("0.5"));
    }

    @UiThreadTest
    @Test
    public void onCreate_ClearButton_EnsureSpinnersReactsToButtonProperly()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();
        ImageButton plusButtonReps = chooseRepsAndSets.findViewById(R.id.plusButtonReps);
        ImageButton plusButtonWeight = chooseRepsAndSets.findViewById(R.id.plusButtonWeight);

        //Reps should rise to 1
        plusButtonReps.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(2, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));

        //Weight should raise to 1
        plusButtonWeight.performClick();
        android.os.SystemClock.sleep(100);

        assertThat(2, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
        assertThat(chooseRepsAndSets.spinnerWeight.getSelectedItem().toString(), equalTo("0.5"));

        Button clearButton = chooseRepsAndSets.findViewById(R.id.ClearButton);
        clearButton.performClick();

        assertThat(0, comparesEqualTo( chooseRepsAndSets.spinnerWeight.getSelectedItem().hashCode()));
        assertThat(1, comparesEqualTo( chooseRepsAndSets.spinnerReps.getSelectedItem().hashCode()));
    }

    @Test
    public void addEntryUsingSpinners_EnsureEntryHasProperValuesSet()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        //Verify
        onView(withId(R.id.setView)).check(matches(withText("1")));
        onView(withId(R.id.repsView)).check(matches(withText("2 reps")));
        onView(withId(R.id.weightView)).check(matches(withText("5.0 kg")));

//        LinearLayout layout = chooseRepsAndSets.findViewById(R.id.linearLayoutForExercises);
//        RelativeLayout relativeLayout = (RelativeLayout)layout.getChildAt(0);
//        LinearLayout linearLayout = (LinearLayout)relativeLayout.getChildAt(0);
//        assertThat("1", Matchers.comparesEqualTo((TextView)linearLayout.getChildAt(0)).getText()));
//        LinearLayout linearLayout2 = (LinearLayout)relativeLayout.getChildAt(1);
    }


    @Test
    public void add2EntriesUsingSpinners_EnsureEntriesHaveProperValuesSet()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("4"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(12).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("6.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("2 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("5.0 kg")));

        onView(withIndex(withId(R.id.setView), 1)).check(matches(withText("2")));
        onView(withIndex(withId(R.id.repsView), 1)).check(matches(withText("4 reps")));
        onView(withIndex(withId(R.id.weightView), 1)).check(matches(withText("6.0 kg")));
    }

    @Test
    public void changeEntry_EnsureEntryProperValuesSet()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("2 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("5.0 kg")));

        onView(withId(R.id.linearLayoutForExercises)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("3"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("2.5"))));
        onView(withText(R.string.ChangeButton)).perform(click());

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("3 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("2.5 kg")));
    }

    @Test
    public void add2Entries_DeleteFirstOneTwice()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();
        LinearLayout layout = chooseRepsAndSets.findViewById(R.id.linearLayoutForExercises);
        assertThat(layout.getChildCount(), equalTo(0));

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("4"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(12).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("6.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("2 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("5.0 kg")));

        onView(withIndex(withId(R.id.setView), 1)).check(matches(withText("2")));
        onView(withIndex(withId(R.id.repsView), 1)).check(matches(withText("4 reps")));
        onView(withIndex(withId(R.id.weightView), 1)).check(matches(withText("6.0 kg")));

        assertThat(layout.getChildCount(), equalTo(2));

        onView(withIndex(withId(R.id.exerciseDetailsBackground), 0)).perform(click());
        onView(withId(R.id.ClearButton)).perform(click());
        assertThat(layout.getChildCount(), equalTo(1));

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("4 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("6.0 kg")));

        onView(withId(R.id.ClearButton)).perform(click());
        assertThat(layout.getChildCount(), equalTo(0));
    }

    @Test
    public void add2Entries_DeleteSecond()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();
        LinearLayout layout = chooseRepsAndSets.findViewById(R.id.linearLayoutForExercises);
        assertThat(layout.getChildCount(), equalTo(0));

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("2"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("5.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        //Set reps
        onView(withId(R.id.spinnerReps)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.spinnerReps)).check(matches(withSpinnerText(containsString("4"))));

        //Set weight
        onView(withId(R.id.spinnerWeight)).perform(click());
        onData(anything()).atPosition(12).perform(click());
        onView(withId(R.id.spinnerWeight)).check(matches(withSpinnerText(containsString("6.0"))));

        onView(withId(R.id.SaveButton)).perform(click());

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("2 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("5.0 kg")));

        onView(withIndex(withId(R.id.setView), 1)).check(matches(withText("2")));
        onView(withIndex(withId(R.id.repsView), 1)).check(matches(withText("4 reps")));
        onView(withIndex(withId(R.id.weightView), 1)).check(matches(withText("6.0 kg")));

        assertThat(layout.getChildCount(), equalTo(2));

        onView(withIndex(withId(R.id.exerciseDetailsBackground), 1)).perform(click());
        onView(withId(R.id.ClearButton)).perform(click());
        assertThat(layout.getChildCount(), equalTo(1));

        onView(withIndex(withId(R.id.setView), 0)).check(matches(withText("1")));
        onView(withIndex(withId(R.id.repsView), 0)).check(matches(withText("2 reps")));
        onView(withIndex(withId(R.id.weightView), 0)).check(matches(withText("5.0 kg")));

        onView(withId(R.id.ClearButton)).perform(click());
        assertThat(layout.getChildCount(), equalTo(0));
    }
    @UiThreadTest
    @Test
    public void onCreate_ClearButtonClickedTwoTimes_ShouldNotThrow()
    {
        ChooseRepsAndSets chooseRepsAndSets = rule.getActivity();

        Button clearButton = chooseRepsAndSets.findViewById(R.id.ClearButton);
        clearButton.performClick();
        clearButton.performClick();
    }

}
