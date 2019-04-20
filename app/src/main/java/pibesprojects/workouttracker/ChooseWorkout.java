package pibesprojects.workouttracker;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_BODYPART_NAME;
import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_WORKOUT_NAME;
import static pibesprojects.workouttracker.CommonData.GET_EDIT_DATA;

public class ChooseWorkout extends IChoose implements View.OnClickListener, View.OnLongClickListener {
    private String currentWorkout;
    private String m_BodyPart;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_workout_abs);
        m_BodyPart = getIntent().getExtras().getString(EXTRA_MESSAGE_WORKOUT_NAME);
        LinearLayout linearLayout = findViewById(R.id.linearLayout_);
        initializeDefaultButtons(linearLayout);
        //        WorkoutNamesEntity workoutName = AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().getWorkoutsNameForGivenBodyPart(m_BodyPart);
//        if(workoutName != null) {
//            for (String workoutName_ : workoutName.m_workoutNames)
//            {
//                createAndAddButton(workoutName_);
//            }
//        }
        //choose_workout_biceps
    }

    @Override
    public void onClick(View view)
    {
        Log.v("TAG", "buttonClicked " + view.getId());
        Intent intent = new Intent(this, ChooseRepsAndSets.class);
        int requestCode = 1; // Or some number you choose
        Button b = (Button)view;
        currentWorkout = b.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, currentWorkout);
        intent.putExtra(EXTRA_MESSAGE_BODYPART_NAME, m_BodyPart);
        startActivityForResult(intent, requestCode);
    }

    public void addLayoutsContentToDataBase(LinearLayout layout)
    {
//        WorkoutNamesEntity workoutName = new WorkoutNamesEntity();
//        workoutName.m_BodyPart = m_BodyPart;
//
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().deleteWorkoutsNameForGivenBodyPart(m_BodyPart);
//
//        for (int i = 0; i < layout.getChildCount(); ++i) {
//            Button button = (Button) layout.getChildAt(i);
//            workoutName.m_workoutNames.add(button.getText().toString());
//            Log.v("Debug", "workoutName: " + workoutName.m_BodyPart);
//
//        }
//        AppDatabase.getAppDatabase(getApplicationContext()).m_workoutNamesDao().insertAll(workoutName);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        // Collect data from the intent and use it
        WorkoutDetailsEntity details = data.getParcelableExtra(GET_EDIT_DATA);
        if(details.getSets() == 0 && details.getWeights().size() == 0 && details.getRepetitions().size() == 0)
        {
            return;
        }
        Log.v("TAG", "onActivityResult " + "ChooseWorkout" + details.getSets());
        details.setWorkoutName(currentWorkout);
        details.setBodyPart(m_BodyPart);
        Intent i = new Intent();
        i.putExtra("message123", details);
        setResult(RESULT_OK, i);
        finish();
    }

    public void changeWorkoutName(LinearLayout layout, View view, String nameBeforeChange)
    {
       // addLayoutsContentToDataBase(layout);
    }
};
