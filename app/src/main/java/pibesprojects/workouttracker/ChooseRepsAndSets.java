package pibesprojects.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static pibesprojects.workouttracker.ChooseWorkout.EXTRA_MESSAGE_BODYPART_NAME;
import static pibesprojects.workouttracker.IChoose.EXTRA_MESSAGE_WORKOUT_NAME;

public class ChooseRepsAndSets extends Activity {
    private final int GET_EDIT_DATA_INT = 20;
    public static final String CHILD_INDEX = "childIndex";

    Spinner spinnerWeight;
    Spinner spinnerReps;
    Integer sets = 0;
    Boolean isExerciseDetailsClicked = false;
    Integer currentExerciseDetailsId =0;
    String m_WorkoutName;
    String m_BodyPart;
    Integer childsIndex;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_reps_and_sets);
        childsIndex = Integer.valueOf(999);
        m_WorkoutName = getIntent().getStringExtra(EXTRA_MESSAGE_WORKOUT_NAME);
        m_BodyPart = getIntent().getStringExtra(EXTRA_MESSAGE_BODYPART_NAME);
        spinnerWeight = findViewById(R.id.spinnerWeight);
        TextView txt = findViewById(R.id.textView);
        txt.setText(m_WorkoutName);
        ArrayAdapter<Double> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(spinnerAdapter);

        for(double i =0; i <300; i+=0.5)
        {
            spinnerAdapter.add(i);
        }
        spinnerAdapter.notifyDataSetChanged();

        spinnerReps = findViewById(R.id.spinnerReps);
        ArrayAdapter<Integer> spinnerAdapterReps =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterReps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReps.setAdapter(spinnerAdapterReps);

        for(int i =1; i <300; ++i)
        {
            spinnerAdapterReps.add(i);
        }
        spinnerAdapterReps.notifyDataSetChanged();
//        ArrayList<WorkoutDetailsEntity> workoutDetailsEntityList = getIntent().getParcelableArrayListExtra(GET_EDIT_DATA);
//
//        if(workoutDetailsEntityList != null)
//        {
//            childsIndex = getIntent().getIntExtra(CHILD_INDEX, 99999);
//            for(int i = 0; i< workoutDetailsEntityList.get(0).getSets(); ++i)
//            {
//                LinearLayout layout = findViewById(R.id.linearLayoutForExercises);
//                LayoutInflater inflater = this.getLayoutInflater();
//                View rowView = inflater.inflate(R.layout.layout_helpers, layout, false);
//                RelativeLayout main = rowView.findViewById(R.id.exerciseDetails);
//
//                TextView view_ = rowView.findViewById(R.id.setView);
//                view_.setText(String.valueOf(i+1));
//
//                view_=  rowView.findViewById(R.id.repsView);
//                view_.setText(workoutDetailsEntityList.get(0).getReps().get(i).toString() + (workoutDetailsEntityList.get(0).getReps().get(i).toString().equals("1") ? " rep" : " reps")) ;
//
//                view_=  rowView.findViewById(R.id.weightView);
//                view_.setText(workoutDetailsEntityList.get(0).getSetsWeight().get(i).toString() + " kg");
//                layout.addView(main);
//            }
//            m_WorkoutName = workoutDetailsEntityList.get(0).getWorkoutName();
//            m_BodyPart = workoutDetailsEntityList.get(0).getBodyPart();
//            txt.setText(m_WorkoutName);
//
//            sets = workoutDetailsEntityList.get(0).getSets();
//        }




//        getIntent().getParcelableArrayListExtra(GET_EDIT_DATA))

    }

    public void clearButtonClicked(View view)
    {
        if(((Button)view).getText().toString().equals(getString(R.string.ClearButton)))
        {
            Log.v("Debug", "Clear ");
            spinnerReps.setSelection(0);
            spinnerWeight.setSelection(0);
        }
//        else if(((Button)view).getText().toString().equals(getString(R.string.DeleteButton)))
//        {
//            Log.v("Debug", "Delete ");
//            ScrollView scroll = findViewById(R.id.ScrollView);
//            LinearLayout layout_ = (LinearLayout)scroll.getChildAt(0);
//            if(layout_.getChildCount() == 0)
//            {
//                return;
//            }
//
//            layout_.removeViewAt(currentExerciseDetailsId-1);
//            Log.v("Debug", "Delete: layoutsChildCount before delete: " + layout_.getChildCount());
//            --sets;
//            for(int i= currentExerciseDetailsId-1; i<layout_.getChildCount(); ++i)
//            {
//                RelativeLayout rel =(RelativeLayout)layout_.getChildAt(i);
//                LinearLayout ll = (LinearLayout)rel.getChildAt(0);
//                TextView setsView = (TextView)(ll).getChildAt(0);
//                setsView.setText(String.valueOf(i+1));
//            }
//
//            if(layout_.getChildCount() < currentExerciseDetailsId)
//            {
//                currentExerciseDetailsId = layout_.getChildCount();
//            }
//            if(layout_.getChildCount() == 0)
//            {
//                ((Button)findViewById(R.id.SaveButton)).setText(getString(R.string.SaveButton));
//                ((Button)findViewById(R.id.ClearButton)).setText(getString(R.string.ClearButton));
//            }
//
//            Log.v("Debug", "Delete: layoutsChildCount after delete: " + layout_.getChildCount());
//            Log.v("Debug", "Delete: currentExerciseDetailsId: " + currentExerciseDetailsId);
//            Log.v("Debug", "Delete: sets: " + sets);
//        }
    }

    public void saveButtonClicked(View view)
    {
//        if(Integer.parseInt(spinnerReps.getSelectedItem().toString()) != 0)
//        {
//            if(((Button)view).getText().toString().equals(getString(R.string.ChangeButton)))
//            {
//                Log.v("Debug", "Change ");
//                ScrollView scroll = findViewById(R.id.ScrollView);
//
//                LinearLayout layout_ = (LinearLayout) scroll.getChildAt(0);
//                RelativeLayout rel = (RelativeLayout) layout_.getChildAt(currentExerciseDetailsId - 1);
//
//                layout_ = (LinearLayout) rel.getChildAt(0);
//                TextView reps = (TextView) ((layout_).getChildAt(1));
//                TextView weight = (TextView) ((layout_).getChildAt(2));
//                Log.v("Debug", "reps " + reps.getText().toString());
//                Log.v("Debug", "weight " + weight.getText().toString());
//                reps.setText(spinnerReps.getSelectedItem().toString() + (spinnerReps.getSelectedItem().toString().equals("1") ? " rep" : " reps"));
//                weight.setText(getString(R.string.Kg, spinnerWeight.getSelectedItem().toString()));
//            }
//            else if(((Button)view).getText().toString().equals(getString(R.string.SaveButton)))
//            {
//                Log.v("Debug", "Save ");
//                LinearLayout layout = findViewById(R.id.linearLayoutForExercises);
//
//                LayoutInflater inflater = this.getLayoutInflater();
//                View rowView = inflater.inflate(R.layout.layout_helpers, layout, false);
//                RelativeLayout main = rowView.findViewById(R.id.exerciseDetails);
//
//                TextView view_ = rowView.findViewById(R.id.setView);
//                view_.setText(String.valueOf(++sets));
//
//                view_=  rowView.findViewById(R.id.repsView);
//                view_.setText(spinnerReps.getSelectedItem().toString() + (spinnerReps.getSelectedItem().toString().equals("1") ?  " rep" : " reps")) ;
//
//                view_=  rowView.findViewById(R.id.weightView);
//                view_.setText(getString(R.string.Kg, spinnerWeight.getSelectedItem().toString()));
//
//                Log.v("Debug", "spinnerWeight value: " + Double.parseDouble(spinnerWeight.getSelectedItem().toString()));
//
//                layout.addView(main);
//            }
//        Log.v("Debug", "saveButtonClicked " + view.getId());
//        Log.v("Debug", "spinnerReps value: " + Integer.parseInt(spinnerReps.getSelectedItem().toString()));
//        }
    }

    public void exerciseDetailsClicked(View view)
    {
//        LinearLayout ll = (LinearLayout)((RelativeLayout)view).getChildAt(0);
//
//        Integer currentExerciseDetailsIdTemp = Integer.parseInt(((TextView)(ll.getChildAt(0))).getText().toString());
//        if(isExerciseDetailsClicked && currentExerciseDetailsId == currentExerciseDetailsIdTemp)
//        {
//            isExerciseDetailsClicked = false;
//            currentExerciseDetailsId = 999;
//            ((Button)findViewById(R.id.SaveButton)).setText(R.string.SaveButton);
//            ((Button)findViewById(R.id.ClearButton)).setText(R.string.ClearButton);
//            spinnerReps.setSelection(0);
//            spinnerWeight.setSelection(0);
//            return;
//        }
//        isExerciseDetailsClicked = true;
//        currentExerciseDetailsId = currentExerciseDetailsIdTemp;
//        ((Button)findViewById(R.id.SaveButton)).setText(R.string.ChangeButton);
//        ((Button)findViewById(R.id.ClearButton)).setText(R.string.DeleteButton);
//
//        Log.v("TAG", "layoutClicked: " + view.getId() + " currentExerciseDetailsId: " + currentExerciseDetailsId);
//
//        TextView reps = (TextView)(ll.getChildAt(1));
//        TextView weight = (TextView)(ll.getChildAt(2));
//
//        spinnerReps.setSelection(Integer.parseInt(reps.getText().toString().split(" ")[0])-1);
//        Double val = Double.parseDouble(weight.getText().toString().split(" ")[0])*2;
//        spinnerWeight.setSelection(val.intValue());
    }

    public void buttonClicked(View view)
    {
        ((Button)findViewById(R.id.SaveButton)).setText(R.string.SaveButton);
        if(view.getId() == R.id.plusButtonWeight)
        {
            spinnerWeight.setSelection(spinnerWeight.getSelectedItemPosition()+1);
        }
        if(view.getId() == R.id.minusButtonWeight  && spinnerWeight.getSelectedItemPosition() != 0)
        {
            spinnerWeight.setSelection(spinnerWeight.getSelectedItemPosition()-1);
        }
        if(view.getId() == R.id.plusButtonReps)
        {
            spinnerReps.setSelection(spinnerReps.getSelectedItemPosition()+1);
        }
        if(view.getId() == R.id.minusButtonReps  && spinnerReps.getSelectedItemPosition() != 0)
        {
            spinnerReps.setSelection(spinnerReps.getSelectedItemPosition()-1);
        }
    }

    @Override
    public void onBackPressed()
    {
//        if(isExerciseDetailsClicked)
//        {
//            isExerciseDetailsClicked = false;
//            currentExerciseDetailsId = 999;
//            ((Button)findViewById(R.id.SaveButton)).setText(R.string.SaveButton);
//            ((Button)findViewById(R.id.ClearButton)).setText(R.string.ClearButton);
//            spinnerReps.setSelection(0);
//            spinnerWeight.setSelection(0);
//            return;
//        }
//        Log.v("Debug", "onBackPressed ");
//        WorkoutDetailsEntity wd = new WorkoutDetailsEntity();
//        ArrayList<Double> weights = new ArrayList<>(sets);
//        ArrayList<Integer> reps = new ArrayList<>(sets);
//        ScrollView scroll = findViewById(R.id.ScrollView);
//        LinearLayout layout_ =(LinearLayout)scroll.getChildAt(0);
//
//        for(int i =0; i< sets; ++i)
//        {
//            RelativeLayout rel  = (RelativeLayout)layout_.getChildAt(i);
//            LinearLayout layout_2 = (LinearLayout)rel.getChildAt(0);
//            TextView reps_ = (TextView)((layout_2).getChildAt(1));
//            TextView weight_ = (TextView)((layout_2).getChildAt(2));
//
//            weights.add(Double.parseDouble(weight_.getText().toString().split(" ")[0]));
//            reps.add(Integer.parseInt(reps_.getText().toString().split(" ")[0]));
//            Log.v("Debug", "onBackPressed weights.get(" + i + ") = " + weights.get(i));
//            Log.v("Debug", "onBackPressed reps.get(" + i + ") = " + reps.get(i));
//        }
//        wd.setReps(reps);
//        wd.setSetsWeight(weights);
//        wd.setSets(sets);
//        wd.setWorkoutName(m_WorkoutName);
//        wd.setBodyPart(m_BodyPart);
//        Log.v("Debug", "onBackPressed m_BodyPart " + m_BodyPart);
//
//        Intent i = new Intent();
//        i.putExtra("message", wd);
//        if(childsIndex != 999)
//        {
//            i.putExtra(CHILD_INDEX, childsIndex);
//
//            setResult(GET_EDIT_DATA_INT, i);
//            finish();
//        }
//        setResult(RESULT_OK, i);
//        finish();
    }

    public void fabClicked(View view) throws UnsupportedEncodingException {
        String query = new String(m_BodyPart + " " + m_WorkoutName + " workout");
        String escapedQuery = URLEncoder.encode(query, "UTF-8");
        Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
