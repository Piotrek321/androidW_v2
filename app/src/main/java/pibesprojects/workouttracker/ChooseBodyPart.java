package pibesprojects.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ChooseBodyPart extends IChoose
{
// implements View.OnClickListener {
//


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        List<WorkoutNamesEntity> workouts = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getAll();
//
//        if(workouts.size() == 0)
//        {
             setContentView(R.layout.choose_bodysection_for_workout_default);
             LinearLayout linearLayout = findViewById(R.id.linearLayout_);
             initializeDefaultButtons(linearLayout);
//        }
//        else
//        {
//            setContentView(R.layout.choose_bodysection_for_workout);
//        }
//        for(WorkoutNamesEntity workout : workouts)
//        {
//            createAndAddButton(workout.m_BodyPart);
//        }
    }
//
//    public void addLayoutsContentToDataBase(LinearLayout layout)
//    {
//        ArrayList<WorkoutNamesEntity> workoutNameEntities = new ArrayList<>();
//        WorkoutNamesEntity workoutName = new WorkoutNamesEntity();
//        List<WorkoutNamesEntity> workoutNamesEntityFromDb = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getAll();
//
//        Button button = (Button) layout.getChildAt(layout.getChildCount()-1);
//        workoutName.m_BodyPart = button.getText().toString();
//        workoutNamesEntityFromDb.add(workoutName);
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().nukeTable(); //TODO CAHNGE IT
//        for(WorkoutNamesEntity wn : workoutNamesEntityFromDb) {
//            AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(wn);
//        }
//
//        List<WorkoutNamesEntity> workoutNamesEntityFromD2 = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getAll();
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
//    protected void onActivityResult (int requestCode, int resultCode, Intent data)
//    {
//        if(data == null)
//        {
//            return;
//        }
//        WorkoutDetailsEntity workoutDetailsEntity = data.getParcelableExtra("message123");
//
//        Log.v("Debug", "ChooseBodyPart::onActivityResult " +
//                "workoutDetailsEntity.getWorkoutName(): " + workoutDetailsEntity.getWorkoutName());
//        Log.v("Debug", "ChooseBodyPart::onActivityResult " +
//                "workoutDetailsEntity.getSetsWeight(): " + workoutDetailsEntity.getSetsWeight());
//
//        Intent i = new Intent();
//        i.putExtra("message", workoutDetailsEntity);
//        setResult(RESULT_OK, i);
//        finish();
//    }
//
    @Override
    public void onClick(View view)
    {
        Log.v("Debug", "buttonClicked view.getId()" + view.getId());
        Intent intent = new Intent(this, ChooseWorkout.class);
        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, ((Button)view).getText().toString());
        int requestCode = 1; // Or some number you choose
        startActivityForResult(intent, requestCode);
    }
//
////TODO CHANGE IT!!!!
//    public void changeWorkoutName(LinearLayout layout, View view, String nameBeforeChange)
//    {
//        Button button = (Button) (view);
//        WorkoutNamesEntity workoutNamesEntityToBeUpdated =
//                AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getWorkoutsNameForGivenBodyPart(nameBeforeChange);
//
//        WorkoutNamesEntity workoutName = new WorkoutNamesEntity();
//        for( String workout : workoutNamesEntityToBeUpdated.m_workoutNames)
//        {
//            workoutName.m_workoutNames.add(workout);
//        }
//        workoutName.m_BodyPart = button.getText().toString();
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().deleteWorkoutsNameForGivenBodyPart(button.getText().toString());
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(workoutName);
//
//        WorkoutNamesEntity workoutNamesEntityToBeUpdated123 =
//                AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getWorkoutsNameForGivenBodyPart(workoutName.m_BodyPart);
//    }
}