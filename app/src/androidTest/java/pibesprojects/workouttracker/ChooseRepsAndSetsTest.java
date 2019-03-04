package pibesprojects.workouttracker;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.ImageButton;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

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
