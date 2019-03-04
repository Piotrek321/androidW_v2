package pibesprojects.workouttracker;

import android.content.DialogInterface;
import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
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

    //@UiThreadTest
    @Test
    public void bodyPartNameLongClicked_ShouldBeAbleToChangeButtonName()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        LinearLayout linearLayout = chooserBodyPart.findViewById(R.id.linearLayout_);

        final Button button = (Button)linearLayout.getChildAt(0);
        chooserBodyPart.runOnUiThread(new Runnable() {
            public void run() {
                button.performLongClick();

            }
        });

//        chooserBodyPart.runOnUiThread(new Runnable() {
//            public void run() {
//                onView(withText("Change"))
//                        //.inRoot(isDialog()) // <---
//                        .perform(click());
//            }
//        });

//
//
//        // android.os.SystemClock.sleep(2000);
//        onView(withText("Change"))
//    //.inRoot(isDialog()) // <---
//            .perform(click());
      //  android.os.SystemClock.sleep(2000);

        // button.performClick();

     //   onView(withId(R.id.editTextDialogUserInput))
       //         .perform(click());

      //  android.os.SystemClock.sleep(2000);

        // .check(matches(isDisplayed()));
      //  assertTrue(chooserBodyPart.longClicked);
        //assertTrue(chooserBodyPart.shortClicked);
        //assertThat(linearLayout.getChildCount(), comparesEqualTo(workouts.length));
    }
    @UiThreadTest
    @Test
    public void testStartMyActivity()
    {
//      //  Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ChooserBodyPart.class.getName(), null, false);
//        final String newWorkout = "New workout";
        final ChooserBodyPart chooserBodyPart = rule.getActivity();
        Button a = new Button(chooserBodyPart);
        chooserBodyPart.FABClicked(a);

        AlertDialog dialog = chooserBodyPart.getLastDialog();
                if (dialog.isShowing()) {
try{
//                View promptsView = chooserBodyPart.li.inflate(R.layout.add_new_body_part_popup_window, null);
//                 EditText userInput = chooserBodyPart.findViewById(R.id.editTextDialogUserInput);
//               userInput.setText("XXXXX");


                    performClick(dialog.getButton(DialogInterface.BUTTON_POSITIVE));

                } catch (Throwable e){
    }
    }
                  android.os.SystemClock.sleep(2000);

        LinearLayout linearLayout = chooserBodyPart.findViewById(R.id.linearLayout_);

//        assertThat(7, comparesEqualTo( linearLayout.getChildCount()));

//
//        assertNotNull("MyActivity activity not started, activity is null", chooserBodyPart);
//
//        final Button button = (Button)linearLayout.getChildAt(0);
//        chooserBodyPart.runOnUiThread(new Runnable() {
//            public void run() {
//                button.performLongClick();
//            }
//        });
//          android.os.SystemClock.sleep(2000);
//
//        chooserBodyPart.runOnUiThread(new Runnable() {
//            public void run() {
//                AlertDialog dialog = chooserBodyPart.getLastDialog();
//                if (dialog.isShowing()) {
//                    try {
////                View promptsView = chooserBodyPart.li.inflate(R.layout.add_new_body_part_popup_window, null);
////                 EditText userInput = chooserBodyPart.findViewById(R.id.editTextDialogUserInput);
////               userInput.setText("XXXXX");
//
//                        chooserBodyPart.userInput.setText(newWorkout);
//                        android.os.SystemClock.sleep(2000);
//
//                        //performClick(dialog.getButton(DialogInterface.BUTTON_POSITIVE));
//                        android.os.SystemClock.sleep(2000);
//
//
//                    } catch (Throwable e) {
//                        Log.v("Debug", "ZLEEEEE");
//                        // e.printStackTrace();
//                    }
//                }            }
//        });
//
//        android.os.SystemClock.sleep(500);
//
//        String[] workouts = chooserBodyPart.getResources().getStringArray(R.array.workouts);
//        assertThat(newWorkout, comparesEqualTo(((Button) linearLayout.getChildAt(0)).getText().toString()));
//      //  chooserBodyPart.finish();
//       // getInstrumentation().removeMonitor(monitor);
    }

    private void performClick(final Button button) throws Throwable {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
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

    @UiThreadTest
    @Test
    public void handlePositiveButtonClicked_ChangingNameToNonExisting_ShouldReturnTrue()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();

        Button button = new Button(chooserBodyPart);
        IChoose.AlertDialogHandler alertDialogHandler = chooserBodyPart.new AlertDialogHandler();
        EditText editText = new EditText(chooserBodyPart);
        editText.setText("Non existing workout name");
        assertTrue(alertDialogHandler.handlePositiveButtonClicked(button, editText, true));
    }

    @UiThreadTest
    @Test
    public void handlePositiveButtonClicked_ChangingNameToExisting_ShouldReturnFalse()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();

        Button button = new Button(chooserBodyPart);
        IChoose.AlertDialogHandler alertDialogHandler = chooserBodyPart.new AlertDialogHandler();
        EditText editText = new EditText(chooserBodyPart);
        editText.setText(chooserBodyPart.getResources().getString(R.string.abs));
        assertFalse(alertDialogHandler.handlePositiveButtonClicked(button, editText, true));
    }

    @UiThreadTest
    @Test
    public void backPressed_DefaultSetup_ShouldNotCrash()
    {
        ChooserBodyPart chooserBodyPart = rule.getActivity();
        chooserBodyPart.onBackPressed();
    }
}
