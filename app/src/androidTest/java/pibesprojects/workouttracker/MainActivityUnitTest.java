package pibesprojects.workouttracker;

import android.app.Activity;
import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.Menu;
import android.widget.ImageButton;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {
    private SimpleDateFormat sdf;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureGoToPreviousDayButtonIsPresent() {
        MainActivity activity = rule.getActivity();
        ImageButton previousDayButton = activity.getGoToPreviousDayButton();
        onView(withId(R.id.goToPreviousDayButton)).check(matches((isDisplayed())));
        assertThat(previousDayButton, notNullValue());
    }

    @Test
    public void ensureGoToNextDayButtonIsPresent() {
        MainActivity activity = rule.getActivity();
        ImageButton nextDayButton = activity.getGoToNextDayButton();
        assertThat(nextDayButton, notNullValue());
    }

    @UiThreadTest
    @Test
    public void ensureGoToPreviousDayButtonSetsProperDate() throws Exception {
        MainActivity activity = rule.getActivity();
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        String currentDate = sdf.format(new Date());
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate));

        ImageButton previousDayButton = activity.getGoToPreviousDayButton();
        assertThat(previousDayButton.callOnClick(), comparesEqualTo(true));

        currentDate = changeDate(currentDate, Calendar.DATE, -1);
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate));

    }

    @UiThreadTest
    @Test
    public void ensureGoToNextDayButtonSetsProperDate() throws Exception {
        MainActivity activity = rule.getActivity();
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        String currentDate = sdf.format(new Date());
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate));

        ImageButton nextDayButton = activity.getGoToNextDayButton();
        assertThat(nextDayButton.callOnClick(), comparesEqualTo(true));

        currentDate = changeDate(currentDate, Calendar.DATE, 1);
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate));
    }

    @Test
    public void ensureMenuExists() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_calendar));
        android.os.SystemClock.sleep(1000);
        intended(hasComponent(CustomCalendar.class.getName()));
        Intents.release();
    }

    @Test
    public void ensureAddWorkoutButtonStartsProperClass() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_add));
        android.os.SystemClock.sleep(1000);
        intended(hasComponent(ChooserBodyPart.class.getName()));
        Intents.release();
    }

    @Test
    public void ensureShowProgressButtonStartsProperClass() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_showProgress));
        android.os.SystemClock.sleep(1000);
        intended(hasComponent(ShowProgress.class.getName()));
        Intents.release();
    }

    @Test
    public void ensureCustomCalendarIsCalledAndProperDateIsSet(){
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;

        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        String currentDate = sdf.format(new Date());

        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_calendar));
        intended(hasComponent(CustomCalendar.class.getName()));
        Activity calendarActivity = getActivityInstance();
        CustomCalendar calendar = (CustomCalendar) calendarActivity;

        assertThat(calendar.getCurrentDate(), comparesEqualTo(currentDate));

        Intents.release();
    }

    @Test
    public void ensureCustomCalendarIsCalledAndProperDateIsChosen_CheckIfMainActivityUpdatedItsDate() {
        MainActivity activity = rule.getActivity();
        Menu menu = activity.m_menu;
        String currentDate = "2000/05/10";
        Intents.init();

        activity.onOptionsItemSelected(menu.findItem(R.id.action_calendar));
        intended(hasComponent(CustomCalendar.class.getName()));
        Activity calendarActivity = getActivityInstance();
        final CustomCalendar calendar = (CustomCalendar) calendarActivity;

        calendar.runOnUiThread(new Runnable() {
            public void run() {
                calendar.m_listener.onSelectedDayChange(calendar.m_calendarView, 2000, 4,10);
            }
        });
        android.os.SystemClock.sleep(1000);

        assertThat(calendar.getCurrentDate(), comparesEqualTo(currentDate));
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate));

        Intents.release();
    }

    private Activity getActivityInstance() {
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

    private String changeDate(String currentDate, int value, int amount) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDate));

        c.add(value, amount);  // number of days to add
        return sdf.format(c.getTime());  // dt is now the new date

    }

}
//openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
///onView(withText("Copy workout")).perform(click());
// onView(withId(R.id.action_add)).perform(click());
//        DateTime dateTime = new DateTime(2014, 10, 15, 0, 0, 0);
//        Intent intent = new Intent();
//        intent.putExtra(MainActivity.KEY_MILLIS, dateTime.getMillis());
//
//        activity.launchActivity(intent);
//
//        onView(withId(R.id.date))
//                .check(matches(withText("2014-10-15")));