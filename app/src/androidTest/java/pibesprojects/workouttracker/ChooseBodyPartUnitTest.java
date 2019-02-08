package pibesprojects.workouttracker;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ChooseBodyPartUnitTest {

    @Rule
    public ActivityTestRule<ChooserBodyPart> rule = new ActivityTestRule<>(ChooserBodyPart.class);

    @Test
    public void onCreate_DefaultSetup_EnsureProperNumberOfRow()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        LinearLayout linearLayout = chooserBodyPart.findViewById(R.id.linearLayout_);
        String[] workouts = chooserBodyPart.getResources().getStringArray(R.array.workouts);
        assertThat(linearLayout.getChildCount(), comparesEqualTo(workouts.length));
    }

    @UiThreadTest
    @Test
    public void backgroundClicked_EnsureFinishIsCalled()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        RelativeLayout backgroundLayout = chooserBodyPart.findViewById(R.id.backgroundLayout);
        assertFalse(chooserBodyPart.isFinishing());
        backgroundLayout.performClick();
        assertTrue(chooserBodyPart.isFinishing());
    }

    @UiThreadTest
    @Test
    public void leftLayoutClicked_EnsureFinishIsCalled()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        RelativeLayout leftLayout = chooserBodyPart.findViewById(R.id.leftLayout);
        assertFalse(chooserBodyPart.isFinishing());
        leftLayout.performClick();
        assertTrue(chooserBodyPart.isFinishing());
    }

    @UiThreadTest
    @Test
    public void rightLayoutClicked_EnsureFinishIsCalled()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        RelativeLayout rightLayout = chooserBodyPart.findViewById(R.id.rightLayout);
        assertFalse(chooserBodyPart.isFinishing());
        rightLayout.performClick();
        assertTrue(chooserBodyPart.isFinishing());
    }

    @UiThreadTest
    @Test
    public void bodyPartNameLongClicked_ShouldBeAbleToChangeButtonName()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        LinearLayout linearLayout = chooserBodyPart.findViewById(R.id.linearLayout_);

        Button button = (Button)linearLayout.getChildAt(0);
        button.performLongClick();
        button.performClick();

//        onView(withId(R.id.ABC))
//                .perform(click());
                //.check(matches(isDisplayed()));
      //  assertTrue(chooserBodyPart.longClicked);
        //assertTrue(chooserBodyPart.shortClicked);
        //assertThat(linearLayout.getChildCount(), comparesEqualTo(workouts.length));
    }

    @UiThreadTest
    @Test
    public void isButtonTextValid_ChangingNameToExistingAndNonExistingName()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();

        assertFalse(chooserBodyPart.isButtonTextValid(chooserBodyPart.getResources().getString(R.string.abs)));

        assertTrue(chooserBodyPart.isButtonTextValid("New workout name"));
        assertFalse(chooserBodyPart.isButtonTextValid(chooserBodyPart.getResources().getString(R.string.abs).toLowerCase()));

        assertFalse(chooserBodyPart.isButtonTextValid(chooserBodyPart.getResources().getString(R.string.abs) + " "));
        assertFalse(chooserBodyPart.isButtonTextValid(" " + chooserBodyPart.getResources().getString(R.string.abs)));

    }

}
