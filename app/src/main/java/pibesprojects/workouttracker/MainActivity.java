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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static pibesprojects.workouttracker.CommonData.*;

public class MainActivity extends AppCompatActivity {

    private ImageButton m_goToPreviousDayButton;
    private ImageButton m_goToNextDayButton;
    private DateHandler m_DateHandler;
    public Menu m_menu;
    public TableLayout m_tableLayout;
    public AppDatabase m_AppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_AppDatabase = AppDatabase.getAppDatabase(this);
        m_DateHandler = new DateHandler();
        m_tableLayout = findViewById(R.id.tableLayout);
        m_goToPreviousDayButton = findViewById(R.id.goToPreviousDayButton);
        m_goToNextDayButton = findViewById(R.id.goToNextDayButton);
        TextView dateText = findViewById(R.id.currentDateText);
        dateText.setText(m_DateHandler.getCurrentDate());

        insertWorkoutDataToLayout();
    }

    public void insertWorkoutDataToLayout()
    {
        WorkoutsForDay workoutDetailsEntities = m_AppDatabase.workoutDetailsDao().getWorkoutForGivenDate(m_DateHandler.m_currentDate);
        if(workoutDetailsEntities != null)
        {
            for (WorkoutDetailsEntity workoutDetailsEntity : workoutDetailsEntities.getWorkoutDetailsEntityList())
            {
                m_tableLayout.addView(convertWorkoutDetailsEntityToWorkoutDataLayout(workoutDetailsEntity));
            }
        }
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
                resetApplication();
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

    public void removeWorkoutDataLayouts()
    {
        int childCount = m_tableLayout.getChildCount();
        for(int i =1; i <childCount; ++i)
        {
            m_tableLayout.removeViewAt(1);
        }
    }
    public void changeDateButtonClicked(View view) throws ParseException
    {
        removeWorkoutDataLayouts();
        m_DateHandler.changeDateButtonClicked(view);
        insertWorkoutDataToLayout();
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

    public void resetApplication()
    {
        removeWorkoutDataLayouts();
        m_AppDatabase.workoutDetailsDao().deleteAll();
        m_AppDatabase.workoutNamesDao().deleteAll();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (data == null || data.getExtras() == null)
        {
            return;
        }
        switch(requestCode) {
            case GET_DATE_FROM_CALENDAR:
            {
                String date = data.getStringExtra(EXTRA_DATE);
                m_DateHandler.setCurrentDate(date);
                TextView dateText = findViewById(R.id.currentDateText);
                dateText.setText(m_DateHandler.getCurrentDate());
                //m_tableLayout.removeAllViews();
                //generateActivityViewForDate();
                break;
            }
            case ACTION_ADD:
            {
                WorkoutDetailsEntity workoutDetailsEntity = data.getParcelableExtra(GET_EDIT_DATA);
                workoutDetailsEntity.setDate(m_DateHandler.m_currentDate);

                WorkoutDataLayout workoutDataLayout = convertWorkoutDetailsEntityToWorkoutDataLayout(workoutDetailsEntity);
                m_tableLayout.addView(workoutDataLayout);


//        m_tableLayout.addView(findViewById(R.id.activity_label));



                List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
                for (int i = 1; i < m_tableLayout.getChildCount(); ++i) {
                    WorkoutDetailsEntity workoutDetailsEntity2 = convertWorkoutEntryListToWorkoutDetails((WorkoutDataLayout) m_tableLayout.getChildAt(i));
                    workoutList.add(workoutDetailsEntity2);
//                WorkoutDetailsEntity workoutDetailsEntity = convertWorkoutEntryListToWorkoutDetails((WorkoutEntryList) m_tableLayout.getChildAt(i));
//                Log.v("Debug", "onActivityResult workoutDetailsEntity getBodyPart: " + workoutDetailsEntity.getBodyPart());
//
//                workoutList.add(workoutDetailsEntity);
                }

                if(m_tableLayout.getChildCount() != 2)
                {
                    WorkoutsForDay w = new WorkoutsForDay(m_DateHandler.m_currentDate, workoutList);
                    m_AppDatabase.workoutDetailsDao().update(w);
                }
                else {
                    WorkoutsForDay w = new WorkoutsForDay(m_DateHandler.m_currentDate, workoutList);
                    m_AppDatabase.workoutDetailsDao().deleteForGivenDate(m_DateHandler.m_currentDate);
                    m_AppDatabase.workoutDetailsDao().insertAll(w);
                }

                // m_databaseHandler.populateDataBase(workoutList);
            }
        }
    }

    WorkoutDataLayout convertWorkoutDetailsEntityToWorkoutDataLayout(WorkoutDetailsEntity workoutDetailsEntity)
    {
        WorkoutDataLayout workoutDataLayout = new WorkoutDataLayout(this, null);
        workoutDataLayout.createEntry(
                workoutDetailsEntity.getWorkoutName(),
                getString(R.string.SetsInteger, workoutDetailsEntity.getRepetitions().size()),
                getString(R.string.Reps, workoutDetailsEntity.getRepetitionsAsString()),
                getString(R.string.Weight, workoutDetailsEntity.getWeightAsString()),
                BitmapFactory.decodeResource(getResources(), R.drawable.benchpress), //m_workoutImageMap.get(wd.getWorkoutName())),
                workoutDetailsEntity.getBodyPart());

        return workoutDataLayout;
    }
    private WorkoutDetailsEntity convertWorkoutEntryListToWorkoutDetails(WorkoutDataLayout workoutEntryList)
    {
        WorkoutDetailsEntity workoutDetailsEntity = new WorkoutDetailsEntity();

        String ss = workoutEntryList.getNumberOfReps().getText().toString();
        ArrayList<String> str = new ArrayList<>(Arrays.asList(ss.split(" ")));
        ArrayList<Integer> intg = new ArrayList<>();
        for(String fav:str){
            if(fav.equals("Reps:") || fav.equals(" ") || fav.equals("") ) continue;
            intg.add(Integer.parseInt(fav.trim()));
        }
        workoutDetailsEntity.setRepetitions(intg);

        String ss1 = workoutEntryList.getWeight().getText().toString();
        ArrayList<String> str1 = new ArrayList<>(Arrays.asList(ss1.split(" ")));
        ArrayList<Double> dbl = new ArrayList<>();
        for(String fav:str1){
            if(fav.equals("Weight:") || fav.equals(" ") || fav.equals("") ) continue;
            dbl.add(Double.parseDouble(fav.trim()));
        }
        workoutDetailsEntity.setRepetitions(intg);
        workoutDetailsEntity.setWeights(dbl);
        workoutDetailsEntity.setWorkoutName(workoutEntryList.getWorkoutName().getText().toString());

        int position = isInteger(workoutEntryList.getNumberOfSets().getText().toString());
        String number = workoutEntryList.getNumberOfSets().getText().toString().substring(position);
        workoutDetailsEntity.setSets(Integer.parseInt(number));
        workoutDetailsEntity.setDate(m_DateHandler.m_currentDate);
        workoutDetailsEntity.setBodyPart(workoutEntryList.getBodyPart().getText().toString());
        Log.v("Debug", "convertWorkoutEntryListToWorkoutDetails workoutEntryList.m_BodyPart: " + workoutEntryList.getBodyPart());

        Log.v("Debug", "convertWorkoutEntryListToWorkoutDetails workoutDetailsEntity.getWorkoutName(): " + workoutDetailsEntity.getWorkoutName());

        return workoutDetailsEntity;
    }

    public static Integer isInteger(String s)
    {
        int position = -1;
        for(int i =0; i<s.length(); ++i)
        {
            position = i;
            if (Character.isDigit(s.charAt(position)))
            {
                return position;
            }
        }
        return position;
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
