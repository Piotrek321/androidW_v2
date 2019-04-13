package pibesprojects.workouttracker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static pibesprojects.workouttracker.CommonData.ACTION_ADD;
import static pibesprojects.workouttracker.CommonData.EXTRA_DATE;
import static pibesprojects.workouttracker.CommonData.GET_DATE_FROM_CALENDAR;
import static pibesprojects.workouttracker.CommonData.GET_EDIT_DATA;
import static pibesprojects.workouttracker.CommonData.GET_EDIT_DATA_INT;
import static pibesprojects.workouttracker.CommonData.dateFormat;

public class MainActivity extends AppCompatActivity {
    public static int a = 0;
    private ImageButton m_goToPreviousDayButton;
    private ImageButton m_goToNextDayButton;
    private DateHandler m_DateHandler;
    public Menu m_menu;
    public TableLayout m_tableLayout;
    public WorkoutForDayRepository m_WorkoutForDayRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                handleUncaughtException(thread, ex);
            }
        }
);
        m_WorkoutForDayRepository = new WorkoutForDayRepository(this);
        m_DateHandler = new DateHandler();
        m_tableLayout = findViewById(R.id.tableLayout);
        m_goToPreviousDayButton = findViewById(R.id.goToPreviousDayButton);
        m_goToNextDayButton = findViewById(R.id.goToNextDayButton);
        TextView dateText = findViewById(R.id.currentDateText);
        dateText.setText(m_DateHandler.getCurrentDate());

        insertWorkoutDataToLayoutForCurrentDate();
    }

    private void handleUncaughtException(Thread thread, Throwable ex) {
        String stackTrace = Log.getStackTraceString(ex);
        String message = ex.getMessage();

        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("message/rfc822");

        intent.putExtra (Intent.EXTRA_EMAIL, new String[] {"piotrbelkner@gmail.com"});

        intent.putExtra (Intent.EXTRA_SUBJECT, "MyApp Crash log file");

        intent.putExtra (Intent.EXTRA_TEXT, stackTrace);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application

        startActivity(intent);

    }

    public void insertWorkoutDetailsEntityIntoMainLayout(WorkoutDetailsEntity... workoutDetailsEntity) {
        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>(Arrays.asList(workoutDetailsEntity));

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(getCurrentDate(), workoutDetailsEntities);
        //TODO or UPDATE?
        m_WorkoutForDayRepository.insertAll(workoutsForDay);

        for (WorkoutDetailsEntity workoutDetailsEntity_ : workoutDetailsEntities) {
            m_tableLayout.addView(convertWorkoutDetailsEntityToWorkoutDataLayout(workoutDetailsEntity_));

        }
    }

    public void insertWorkoutDataToLayoutForCurrentDate() {
        WorkoutsForDay workoutsForDay = m_WorkoutForDayRepository.getWorkoutForGivenDate(getCurrentDate());
        if (workoutsForDay != null) {
            for (WorkoutDetailsEntity workoutDetailsEntity : workoutsForDay.getWorkoutDetailsEntityList()) {
                m_tableLayout.addView(convertWorkoutDetailsEntityToWorkoutDataLayout(workoutDetailsEntity));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.createTestConfig:
                Log.v("Debug", "R.id.action_showProgress");
                WorkoutDetailsEntityBuilder builder = new WorkoutDetailsEntityBuilder();
//                WorkoutDetailsEntity workoutDetailsEntity = builder.setSets(1).
//                        setRepetitions(new ArrayList<>(Collections.singletonList(10))).
//                        setWeights(new ArrayList<>(Collections.singletonList(5.0))).
//                        setBodyPart("bodypart").
//                        setWorkoutName("workout").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity1 = builder.setSets(2).
//                        setRepetitions(new ArrayList<>(Arrays.asList(50, 100))).
//                        setWeights(new ArrayList<>(Arrays.asList(10.0, 5.0))).
//                        setBodyPart("bodypart1").
//                        setWorkoutName("workout1").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity2 = builder.setSets(3).
//                        setRepetitions(new ArrayList<>(Arrays.asList(50, 100, 150))).
//                        setWeights(new ArrayList<>(Arrays.asList(10.0, 5.0, 7.5))).
//                        setBodyPart("bodypart2").
//                        setWorkoutName("workout2").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity3 = builder.setSets(4).
//                        setRepetitions(new ArrayList<>(Arrays.asList(50, 100, 150, 200))).
//                        setWeights(new ArrayList<>(Arrays.asList(10.0, 5.0, 7.5, 10.0))).
//                        setBodyPart("bodypart3").
//                        setWorkoutName("workout3").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity4 = builder.setSets(5).
//                        setRepetitions(new ArrayList<>(Arrays.asList(0, 50, 100, 150, 200))).
//                        setWeights(new ArrayList<>(Arrays.asList(0.0, 10.0, 5.0, 7.5, 10.0))).
//                        setBodyPart("bodypart4").
//                        setWorkoutName("workout4").build();
//                WorkoutDetailsEntity workoutDetailsEntity5 = builder.setSets(1).
//                        setRepetitions(new ArrayList<>(Collections.singletonList(10))).
//                        setWeights(new ArrayList<>(Collections.singletonList(5.0))).
//                        setBodyPart("bodypart").
//                        setWorkoutName("workout").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity6= builder.setSets(2).
//                        setRepetitions(new ArrayList<>(Arrays.asList(50, 100))).
//                        setWeights(new ArrayList<>(Arrays.asList(10.0, 5.0))).
//                        setBodyPart("bodypart1").
//                        setWorkoutName("workout1").build();
//
//                WorkoutDetailsEntity workoutDetailsEntity7 = builder.setSets(3).
//                        setRepetitions(new ArrayList<>(Arrays.asList(50, 100, 150))).
//                        setWeights(new ArrayList<>(Arrays.asList(10.0, 5.0, 7.5))).
//                        setBodyPart("bodypart2").
//                        setWorkoutName("workout2").build();
//
//                insertWorkoutDetailsEntityIntoMainLayout(workoutDetailsEntity, workoutDetailsEntity1, workoutDetailsEntity2, workoutDetailsEntity3, workoutDetailsEntity4);

                List<WorkoutDetailsEntity> workoutList = new ArrayList<>();

                for(int i =0; i< 10; ++i)
                {
                    WorkoutDetailsEntity workoutDetailsEntity= builder.setSets(1+a).
                            setRepetitions(new ArrayList<>(Collections.singletonList(10+a))).
                            setWeights(new ArrayList<>(Collections.singletonList(5.0))).
                            setBodyPart("bodypart" +a).
                            setWorkoutName("workout" + a).build();
                    insertWorkoutDetailsEntityIntoMainLayout(workoutDetailsEntity);
                    workoutList.add(workoutDetailsEntity);
                    ++a;

                }
                if (m_tableLayout.getChildCount() != 2) {
                    workoutList = new ArrayList<>();
                    for(int i = 0; i< m_tableLayout.getChildCount()-1; ++i) {
                        workoutList.add(getWorkoutDataLayoutAt(i).convertToWorkoutDetailsEntity());
                    }
                    WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
                    m_WorkoutForDayRepository.update(w);
                } else {
                    WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
                    m_WorkoutForDayRepository.deleteForGivenDate(getCurrentDate());
                    m_WorkoutForDayRepository.insertAll(w);
                }

                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        m_menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    ImageButton getGoToPreviousDayButton() {
        return m_goToPreviousDayButton;
    }

    ImageButton getGoToNextDayButton() {
        return m_goToNextDayButton;
    }

    public void removeWorkoutDataLayouts() {
        int childCount = m_tableLayout.getChildCount();
        for (int i = 1; i < childCount; ++i) {
            m_tableLayout.removeViewAt(1);
        }
    }

    public void changeDateButtonClicked(View view) throws ParseException {
        removeWorkoutDataLayouts();
        m_DateHandler.changeDateButtonClicked(view);
        insertWorkoutDataToLayoutForCurrentDate();
    }

    public String getCurrentDate() {
        return m_DateHandler.getCurrentDate();
    }

    private void startCalendar() {
        Intent intent = new Intent(this, CustomCalendar.class);
        startActivityForResult(intent, GET_DATE_FROM_CALENDAR);
    }

    public void resetApplication() {
        removeWorkoutDataLayouts();
        m_WorkoutForDayRepository.deleteAll();
        // m_AppDatabase.workoutNamesDao().deleteAll();
    }

    public void trashButtonClicked(View view) {
        Log.v("Debug", "trashButtonClicked " + view.getId());
        ViewGroup parent = (ViewGroup) view.getParent();
        ViewGroup grandparent = (ViewGroup) parent.getParent();
        //grandparent.removeView(parent);
        TableLayout gp = (TableLayout) grandparent.getParent();
        gp.removeView(grandparent);
        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
        //m_tableLayout.addView(findViewById(R.id.activity_label));

        for (int i = 1; i < gp.getChildCount(); ++i) {
            WorkoutDetailsEntity workoutDetailsEntity = ((WorkoutDataLayout) gp.getChildAt(i)).convertToWorkoutDetailsEntity();
            Log.v("Debug", "trashButtonClicked workoutDetailsEntity" + workoutDetailsEntity.getBodyPart());

            workoutDetailsEntities.add(workoutDetailsEntity);
        }
        WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutDetailsEntities);
        m_WorkoutForDayRepository.update(w);
//        if(workoutList.size() == 0)
//        {
//            m_databaseHandler.deleteWorkoutForGivenDate(m_currentDate);
//        }
//        else {
//
//            m_databaseHandler.populateDataBase(workoutList);
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || data.getExtras() == null) {
            return;
        }
        switch (requestCode) {
            case GET_DATE_FROM_CALENDAR: {
                String date = data.getStringExtra(EXTRA_DATE);
                m_DateHandler.setCurrentDate(date);
                TextView dateText = findViewById(R.id.currentDateText);
                dateText.setText(m_DateHandler.getCurrentDate());
                //m_tableLayout.removeAllViews();
                //generateActivityViewForDate();
                break;
            }
            case ACTION_ADD: {
                WorkoutDetailsEntity workoutDetailsEntity = data.getParcelableExtra(GET_EDIT_DATA);
                WorkoutDataLayout workoutDataLayout = convertWorkoutDetailsEntityToWorkoutDataLayout(workoutDetailsEntity);
                m_tableLayout.addView(workoutDataLayout);

                List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
                for (int i = 1; i < m_tableLayout.getChildCount(); ++i) {
                    WorkoutDetailsEntity workoutDetailsEntity2 = ((WorkoutDataLayout) m_tableLayout.getChildAt(i)).convertToWorkoutDetailsEntity();
                    workoutList.add(workoutDetailsEntity2);
                }

                if (m_tableLayout.getChildCount() != 2) {
                    WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
                    m_WorkoutForDayRepository.update(w);
                } else {
                    WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
                    m_WorkoutForDayRepository.deleteForGivenDate(getCurrentDate());
                    m_WorkoutForDayRepository.insertAll(w);
                }

                // m_databaseHandler.populateDataBase(workoutList);
            }
        }
    }

    WorkoutDataLayout convertWorkoutDetailsEntityToWorkoutDataLayout(WorkoutDetailsEntity workoutDetailsEntity) {
        WorkoutDataLayout workoutDataLayout = new WorkoutDataLayout(this, null);
        workoutDataLayout.createEntry(
                workoutDetailsEntity.getWorkoutName(),
                getString(R.string.SetsInteger, workoutDetailsEntity.getRepetitions().size()),
                getString(R.string.Reps, workoutDetailsEntity.getRepetitionsAsString()),
                getString(R.string.Weight, workoutDetailsEntity.getWeightAsString()),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),//benchpress), //m_workoutImageMap.get(wd.getWorkoutName())),
                workoutDetailsEntity.getBodyPart());

        return workoutDataLayout;
    }

    public void workoutRowClicked(View view) {
        //HOW TO CAST IT TO WORKOUTENTRYLIST????
        ViewGroup parent = (ViewGroup) view.getParent();
        //parent.indexOfChild()
        ViewGroup grandparent = (ViewGroup) parent.getParent();
        int childIndex = grandparent.indexOfChild(parent);
        Log.v("Debug", "a.indexOfChild(view); " + childIndex);

        WorkoutDataLayout workoutDataLayout = getWorkoutDataLayoutAt(childIndex - 1);
        Log.v("Debug", "getWorkoutName " + workoutDataLayout.getWorkoutName().getText().toString());
        WorkoutDetailsEntity workoutDetailsEntity = getWorkoutDataLayoutAt(childIndex - 1).convertToWorkoutDetailsEntity();

        Intent intent = new Intent(this, ChooseRepsAndSets.class);

        intent.putExtra(GET_EDIT_DATA, workoutDetailsEntity);
        //intent.putExtra(CHILD_INDEX, grandparent.indexOfChild(parent));
        startActivityForResult(intent, GET_EDIT_DATA_INT);
//        LinearLayout linearLayout = (LinearLayout)((RelativeLayout)view).getChildAt(1);
//
//        int index = ((LinearLayout) ((RelativeLayout) view).getChildAt(1)).indexOfChild(linearLayout);
//        Log.v("Debug", "index " + index);
//        WorkoutEntryList workoutEntryList = new WorkoutEntryList(this);
//        workoutEntryList.setWorkoutName((TextView)(linearLayout.getChildAt(0)));
//        workoutEntryList.setNumberOfSets((TextView)(linearLayout.getChildAt(1)));
//        workoutEntryList.setNumberOfReps((TextView)(linearLayout.getChildAt(2)));
//        workoutEntryList.setWeight((TextView)(linearLayout.getChildAt(3)));
//        workoutEntryList.setBodyPart((TextView)(linearLayout.getChildAt(4)));
//
//        WorkoutDetailsEntity workoutDetailsEntity = convertWorkoutEntryListToWorkoutDetails(workoutEntryList);
//
//        Intent intent1 = new Intent(this, ChooseRepsAndSets.class);
//        ArrayList<WorkoutDetailsEntity> workoutDetailsEntityList = new ArrayList<>();
//        workoutDetailsEntityList.add(workoutDetailsEntity);
        //        Intent intent1 = new Intent(this, ChooseRepsAndSets.class);

//        intent1.putParcelableArrayListExtra(GET_EDIT_DATA, workoutDetailsEntityList);
//        intent1.putExtra(CHILD_INDEX, grandparent.indexOfChild(parent));
//        startActivityForResult(intent1, GET_EDIT_DATA_INT);
    }

    public WorkoutDataLayout getWorkoutDataLayoutAt(int index) {
        return (WorkoutDataLayout) m_tableLayout.getChildAt(index + 1);
    }

    private class DateHandler {
        private SimpleDateFormat sdf;
        private String m_currentDate;

        DateHandler() {
            sdf = new SimpleDateFormat(dateFormat, Locale.US);
            m_currentDate = sdf.format(new Date());
        }

        public String getCurrentDate() {
            return m_currentDate;
        }

        public void setCurrentDate(String newDate) {
            m_currentDate = newDate;
        }

        public void changeDateButtonClicked(View view) throws ParseException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(m_currentDate));
            ImageButton imageButton = (ImageButton) view;
            if (imageButton == getGoToNextDayButton()) {
                calendar.add(Calendar.DATE, 1);  // number of days to add
            } else if (imageButton == getGoToPreviousDayButton()) {
                calendar.add(Calendar.DATE, -1);  // number of days to add
            }
            m_currentDate = sdf.format(calendar.getTime());  // dt is now the new date
            TextView dateText = findViewById(R.id.currentDateText);
            dateText.setText(m_currentDate);
            //generateActivityViewForDate();
        }
    }

}
