package pibesprojects.workouttracker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static pibesprojects.workouttracker.CustomCalendar.EXTRA_DATE;
import static pibesprojects.workouttracker.CustomCalendar.dateFormat;

public class MainActivity extends AppCompatActivity {
    public static final String GET_EDIT_DATA = "message";
    private final int GET_DATE_FROM_CALENDAR= 10;
    private final int ACTION_ADD = 20;
    private ImageButton m_goToPreviousDayButton;
    private ImageButton m_goToNextDayButton;
    private DateHandler m_DateHandler;
    public Menu m_menu;
    public TableLayout m_tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_DateHandler = new DateHandler();
        m_tableLayout = findViewById(R.id.tableLayout);
        m_goToPreviousDayButton = findViewById(R.id.goToPreviousDayButton);
        m_goToNextDayButton = findViewById(R.id.goToNextDayButton);
        TextView dateText = findViewById(R.id.currentDateText);
        dateText.setText(m_DateHandler.getCurrentDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.v("Debug", "R.id.action_add");
                Intent chooseBodyPartIntent = new Intent(this, ChooseBodyPart.class);
                startActivityForResult(chooseBodyPartIntent, ACTION_ADD);
                break;
            case R.id.restoreFactorySettings:
                Log.v("Debug", "R.id.restoreFactorySettings");
                break;
            case R.id.action_calendar:
                Log.v("Debug", "R.id.action_calendar");
                startCalendar();
                break;
            case R.id.action_showProgress:
                Log.v("Debug", "R.id.action_showProgress");
                Intent showProgressIntent = new Intent(this, ShowProgress.class);
                startActivity(showProgressIntent);
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        m_menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    ImageButton getGoToPreviousDayButton()
    {
        return m_goToPreviousDayButton;
    }

    ImageButton getGoToNextDayButton()
    {
        return m_goToNextDayButton;
    }

    public void changeDateButtonClicked(View view) throws ParseException
    {
        m_DateHandler.changeDateButtonClicked(view);
    }

    public String getCurrentDate()
    {
        return m_DateHandler.getCurrentDate();
    }

    private void startCalendar()
    {
        Intent intent = new Intent(this, CustomCalendar.class);
        startActivityForResult(intent, GET_DATE_FROM_CALENDAR);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (data == null || data.getExtras() == null)
        {
            return;
        }
        if (requestCode == GET_DATE_FROM_CALENDAR) {
            String date = data.getStringExtra(EXTRA_DATE);
            m_DateHandler.setCurrentDate(date);
            TextView dateText = findViewById(R.id.currentDateText);
            dateText.setText(m_DateHandler.getCurrentDate());
            Log.v("Debug", "onActivityResult date: " + date);
            //m_tableLayout.removeAllViews();
            //generateActivityViewForDate();
        }
        else {
            WorkoutDetailsEntity wd = data.getParcelableExtra("message");
            wd.setDate(m_DateHandler.m_currentDate);
            Log.v("Debug", "getWorkoutName " + wd.getWorkoutName());
            Log.v("Debug", "getSetsWeight " + wd.getWeights());

            String reps = wd.getRepetitions().toString().substring(1, wd.getRepetitions().toString().length() - 1);
            reps = reps.replace(",", "");
            String weight = wd.getWeights().toString().substring(1, wd.getWeights().toString().length() - 1);
            weight = weight.replace(",", "");
            WorkoutEntryList workoutEntryList = new WorkoutEntryList(this, null);
            workoutEntryList.createEntry(
                    wd.getWorkoutName(),
                    getString(R.string.SetsInteger, wd.getRepetitions().size()),
                    getString(R.string.Reps, reps),
                    getString(R.string.Weight, weight),
                    BitmapFactory.decodeResource(getResources(), R.drawable.benchpress), //m_workoutImageMap.get(wd.getWorkoutName())),
                    wd.getBodyPart());

            Log.v("Debug", "onActivityResult getBodyPart: " + wd.getBodyPart());

            Log.v("Debug", "onActivityResult before m_tableLayout.add m_tableLayout.childCount" + m_tableLayout.getChildCount());
//        m_tableLayout.addView(findViewById(R.id.activity_label));

            m_tableLayout.addView(workoutEntryList);

            List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
            for (int i = 1; i < m_tableLayout.getChildCount(); ++i) {
//                WorkoutDetailsEntity workoutDetailsEntity = convertWorkoutEntryListToWorkoutDetails((WorkoutEntryList) m_tableLayout.getChildAt(i));
//                Log.v("Debug", "onActivityResult workoutDetailsEntity getBodyPart: " + workoutDetailsEntity.getBodyPart());
//
//                workoutList.add(workoutDetailsEntity);
            }

           // m_databaseHandler.populateDataBase(workoutList);
            Log.v("Debug", "onActivityResult after m_tableLayout.add m_tableLayout.childCount" + m_tableLayout.getChildCount());
        }
    }


    private class DateHandler
    {
        private SimpleDateFormat sdf;
        private String m_currentDate;
        DateHandler()
        {
            sdf = new SimpleDateFormat(dateFormat, Locale.US);
            m_currentDate = sdf.format(new Date());
        }

        public String getCurrentDate()
        {
            return m_currentDate;
        }

        public void setCurrentDate(String newDate)
        {
            m_currentDate = newDate;
        }

        public void changeDateButtonClicked(View view) throws ParseException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(m_currentDate));
            ImageButton imageButton = (ImageButton)view;
            if(imageButton == getGoToNextDayButton())
            {
                calendar.add(Calendar.DATE, 1);  // number of days to add
            }
            else if(imageButton == getGoToPreviousDayButton())
            {
                calendar.add(Calendar.DATE, -1);  // number of days to add
            }
            m_currentDate = sdf.format(calendar.getTime());  // dt is now the new date
            TextView dateText = findViewById(R.id.currentDateText);
            dateText.setText(m_currentDate);
            //generateActivityViewForDate();
        }
    }

}
