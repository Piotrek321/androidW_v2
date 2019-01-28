package pibesprojects.workouttracker;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageButton;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {
    SimpleDateFormat sdf;

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureGoToPreviousDayButtonIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        ImageButton previousDayButton = activity.getGoToPreviousDayButton();
        assertThat(previousDayButton,notNullValue());
    }

    @Test
    public void ensureGoToNextDayButtonIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        ImageButton nextDayButton = activity.getGoToNextDayButton();
        assertThat(nextDayButton,notNullValue());
    }

    @UiThreadTest
    @Test
    public void ensureGoToPreviousDayButtonSetsProperDate() throws Exception {
        MainActivity activity = rule.getActivity();
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        String currentDate = sdf.format(new Date());
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate) );

        ImageButton previousDayButton = activity.getGoToPreviousDayButton();
        assertThat(previousDayButton.callOnClick(), comparesEqualTo(true));

        currentDate = changeDate(currentDate, Calendar.DATE, -1);
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate) );

    }

    @UiThreadTest
    @Test
    public void ensureGoToNextDayButtonSetsProperDate() throws Exception {
        MainActivity activity = rule.getActivity();
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        String currentDate = sdf.format(new Date());
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate) );

        ImageButton nextDayButton = activity.getGoToNextDayButton();
        assertThat(nextDayButton.callOnClick(), comparesEqualTo(true));

        currentDate = changeDate(currentDate, Calendar.DATE, 1);
        assertThat(activity.getCurrentDate(), comparesEqualTo(currentDate) );

    }

    String changeDate(String currentDate, int value, int amount)throws ParseException
    {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDate));

        c.add(value, amount);  // number of days to add
        return sdf.format(c.getTime());  // dt is now the new date

    }
}
//getRightArrowButton

//        View viewById = activity.findViewById(R.id.listview);
//        assertThat(viewById,notNullValue());
//        assertThat(viewById, instanceOf(ListView.class));
//        ListView listView = (ListView) viewById;
//        ListAdapter adapter = listView.getAdapter();
//        assertThat(adapter, instanceOf(ArrayAdapter.class));
//        assertThat(adapter.getCount(), greaterThan(5));