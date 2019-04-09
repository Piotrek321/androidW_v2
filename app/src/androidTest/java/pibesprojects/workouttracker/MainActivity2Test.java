package pibesprojects.workouttracker;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

public class MainActivity2Test {
    MainActivity m_MainActivity;
    private Helpers helpers;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Throwable {
        m_MainActivity = rule.getActivity();
        helpers = new Helpers();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_MainActivity.resetApplication();
            }
        });
        Intents.init();
    }


    @After
    public void tearDown()
    {
        Intents.release();
    }

    @Test
    public void trashButtonClicked_DeleteProperLayout() throws Throwable {

        runOnUiThread(new Runnable()
        {
            public void run()
            {
                m_MainActivity.insertWorkoutDetailsEntityIntoMainLayout(helpers.createTestWorkoutDetailsEntity1().build());
                assertThat(m_MainActivity.m_tableLayout.getChildCount(), comparesEqualTo(2));

                WorkoutDataLayout workoutDataLayout = m_MainActivity.getWorkoutDataLayoutAt(0);
                workoutDataLayout.pressTrashBinButton();
                assertThat(m_MainActivity.m_tableLayout.getChildCount(), comparesEqualTo(1));

            }
        });

    }

}
