package pibesprojects.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_WORKOUT_NAME;
import static pibesprojects.workouttracker.CommonData.GET_EDIT_DATA;


public class ChooseBodyPart extends IChoose
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_bodysection_for_workout);

        m_WorkoutNamesRepository = new WorkoutNamesRepository(this);
        m_LinearLayout = findViewById(R.id.linearLayout_);

        List<WorkoutNames> workoutNames = m_WorkoutNamesRepository.getAll();
        if(workoutNames.size() == 0)
        {
            // initializeDefaultButtons(m_LinearLayout);
        }
        else
        {
        }
        for(WorkoutNames workoutName : workoutNames)
        {
            createAndAddButton(workoutName.getBodyPart());
        }
    }


    public void addLayoutsContentToDataBase()
    {
        WorkoutNames[] workoutList = new WorkoutNames[m_LinearLayout.getChildCount()];
        if (m_LinearLayout.getChildCount() >= 2)
        {
            //workoutList = new ArrayList<>();
            for(int i = 0; i< m_LinearLayout.getChildCount(); ++i) // -1
            {
                String bodyPartName = Globals.viewToString(m_LinearLayout.getChildAt(i));
                WorkoutNames workoutNames = new WorkoutNames();
                workoutNames.setBodyPart(bodyPartName);

                workoutNames = m_WorkoutNamesRepository.getWorkoutName(bodyPartName);

                if(workoutNames != null) {
                    //wn.setBodyPart(bodyPartName);
                    //workoutNames.setWorkoutNames(wn.getWorkoutNames());

                }
                else
                {
                    workoutNames = new WorkoutNames(bodyPartName);
                }
                workoutList[i] = workoutNames;

            }
            //WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
            m_WorkoutNamesRepository.deleteAll();
            m_WorkoutNamesRepository.insertAll(workoutList);

           // m_WorkoutNamesRepository.update(workoutList);

        } else {
            WorkoutNames workoutNames = new WorkoutNames( Globals.viewToString(m_LinearLayout.getChildAt(0)));
//            WorkoutsForDay w = new WorkoutsForDay(getCurrentDate(), workoutList);
//            m_WorkoutsForDayRepository.deleteForGivenDate(getCurrentDate());
            m_WorkoutNamesRepository.deleteAll();

            m_WorkoutNamesRepository.insertAll(workoutNames);
        }
//        WorkoutNames workoutName = new WorkoutNames();
//        workoutName.setBodyPart(m_BodyPart);
//
//        //AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().deleteWorkoutsNameForGivenBodyPart(m_BodyPart);
//
//        for (int i = 0; i < layout.getChildCount(); ++i) {
//            Button button = (Button) layout.getChildAt(i);
//            workoutName.m_workoutNames.add(button.getText().toString());
//            Log.v("Debug", "workoutName: " + workoutName.m_BodyPart);
//
//        }
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(workoutName);
    }
//
//    public void addLayoutsContentToDataBase(LinearLayout layout)
//    {
//        ArrayList<WorkoutNames> workoutNameEntities = new ArrayList<>();
//        WorkoutNames workoutName = new WorkoutNames();
//        List<WorkoutNames> WorkoutNamesFromDb = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getAll();
//
//        Button button = (Button) layout.getChildAt(layout.getChildCount()-1);
//        workoutName.m_BodyPart = button.getText().toString();
//        WorkoutNamesFromDb.add(workoutName);
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().nukeTable(); //TODO CAHNGE IT
//        for(WorkoutNames wn : WorkoutNamesFromDb) {
//            AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(wn);
//        }
//
//        List<WorkoutNames> WorkoutNamesFromD2 = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getAll();
//
////        for(int i =0; i<layout.getChildCount(); ++i) {
////            Button button = (Button) layout.getChildAt(i);
////            workoutName.m_BodyPart = button.getText().toString();
////            workoutNameEntities.add(workoutName);
////            Log.v("Debug", "workoutName: " + workoutName.m_BodyPart);
////
////            AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(workoutName);
////        }
//    }
//
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if(data == null)
        {
            return;
        }
        WorkoutDetailsEntity workoutDetailsEntity = data.getParcelableExtra("message123");

        Log.v("Debug", "ChooseBodyPart::onActivityResult " +
                "workoutDetailsEntity.getWorkoutName(): " + workoutDetailsEntity.getWorkoutName());
        Log.v("Debug", "ChooseBodyPart::onActivityResult " +
                "workoutDetailsEntity.getSetsWeight(): " + workoutDetailsEntity.getWeights());

        Intent i = new Intent();
        i.putExtra(GET_EDIT_DATA, workoutDetailsEntity);
        setResult(RESULT_OK, i);
        finish();
    }
//
    @Override
    public void onClick(View view)
    {
        Log.v("Debug", "buttonClicked view.getId()" + view.getId());
        Intent intent = new Intent(this, ChooseWorkout.class);
        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, Globals.viewToString(view));
        int requestCode = 1; // Or some number you choose
        startActivityForResult(intent, requestCode);
    }
//
////TODO CHANGE IT!!!!
//    public void changeWorkoutName(LinearLayout layout, View view, String nameBeforeChange)
//    {
//        Button button = (Button) (view);
//        WorkoutNames WorkoutNamesToBeUpdated =
//                AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getWorkoutsNameForGivenBodyPart(nameBeforeChange);
//
//        WorkoutNames workoutName = new WorkoutNames();
//        for( String workout : WorkoutNamesToBeUpdated.m_workoutNames)
//        {
//            workoutName.m_workoutNames.add(workout);
//        }
//        workoutName.m_BodyPart = button.getText().toString();
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().deleteWorkoutsNameForGivenBodyPart(button.getText().toString());
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(workoutName);
//
//        WorkoutNames WorkoutNamesToBeUpdated123 =
//                AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getWorkoutsNameForGivenBodyPart(workoutName.m_BodyPart);
//    }
}