package pibesprojects.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_BODYPART_NAME;
import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_WORKOUT_NAME;
import static pibesprojects.workouttracker.CommonData.GET_EDIT_DATA;

public class ChooseRepsAndSets extends Activity {

    Spinner spinnerWeight;
    Spinner spinnerReps;
    Integer sets = 0;
    Boolean isExerciseDetailsClicked = false;
    Integer currentExerciseDetailsId =0;
    String m_WorkoutName;
    String m_BodyPart;

    private <Type> ArrayAdapter<Type> createSpinner()
    {
        ArrayAdapter<Type> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();

        return spinnerAdapter;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_reps_and_sets);
        m_WorkoutName = getIntent().getStringExtra(EXTRA_MESSAGE_WORKOUT_NAME);
        m_BodyPart = getIntent().getStringExtra(EXTRA_MESSAGE_BODYPART_NAME);
        TextView workoutNameText = findViewById(R.id.workoutNameText);
        workoutNameText.setText(m_WorkoutName);
        spinnerWeight = findViewById(R.id.spinnerWeight);
        spinnerReps = findViewById(R.id.spinnerReps);

        ArrayAdapter<Double> weightAdapter = createSpinner();
        ArrayAdapter<Integer> repsAdapter = createSpinner();
        for(double i =0; i <300; i+=0.5)
        {
            weightAdapter.add(i);
            if(i % 1 == 0 && i != 0)
            {
                repsAdapter.add((int)i);
            }
        }
        spinnerWeight.setAdapter(weightAdapter);
        spinnerReps.setAdapter(repsAdapter);

        WorkoutDetailsEntity workoutDetailsEntity = getIntent().getParcelableExtra(GET_EDIT_DATA);

        if(workoutDetailsEntity != null)
        {
            for(int i =0; i< workoutDetailsEntity.getSets(); ++i) {
                String repetitionsFromSpinner = workoutDetailsEntity.getRepetitions().get(i).toString();
                String repetitionsText = repetitionsFromSpinner + (repetitionsFromSpinner.equals("1") ? " rep" : " reps");
                String weightText = getString(R.string.Kg, workoutDetailsEntity.getWeights().get(i).toString());

                createNewEntry(String.valueOf(i+1), repetitionsText, weightText);
            }
            sets = workoutDetailsEntity.getSets();
            workoutNameText.setText(workoutDetailsEntity.getWorkoutName());
            TextView isEditModeText = ((TextView)findViewById(R.id.isEditMode));
            isEditModeText.setText(R.string.EditMode);
            isEditModeText.setVisibility(View.VISIBLE);
        }
    }

    public void clearButtonClicked(View view)
    {
        if(Globals.viewToString(view).equals(getString(R.string.ClearButton)))
        {
            spinnerReps.setSelection(0);
            spinnerWeight.setSelection(0);
        }
        else if(Globals.viewToString(view).equals(getString(R.string.DeleteButton)))
        {
            LinearLayout layout = findViewById(R.id.linearLayoutForExercises);

            if(layout.getChildCount() == 0)
            {
                return;
            }

            layout.removeViewAt(currentExerciseDetailsId-1);
            --sets;
            for(int i= currentExerciseDetailsId-1; i<layout.getChildCount(); ++i)
            {
                TextView setsView = geSetsTextView(i);
                setsView.setText(String.valueOf(i+1));
            }

            if(layout.getChildCount() < currentExerciseDetailsId)
            {
                currentExerciseDetailsId = layout.getChildCount();
            }

            if(layout.getChildCount() == 0)
            {
                ((Button)findViewById(R.id.SaveButton)).setText(getString(R.string.SaveButton));
                ((Button)findViewById(R.id.ClearButton)).setText(getString(R.string.ClearButton));
            }
        }
    }

    private LinearLayout getExerciseDetailsLayout(int childIndex)
    {
        LinearLayout linearLayoutForExercises = findViewById(R.id.linearLayoutForExercises);
        RelativeLayout rel = (RelativeLayout) linearLayoutForExercises.getChildAt(childIndex);
        return (LinearLayout) rel.getChildAt(0);
    }

    private TextView geSetsTextView(int childIndex)
    {
        LinearLayout exerciseDetailsLayout = getExerciseDetailsLayout(childIndex);
        return (TextView) (exerciseDetailsLayout.getChildAt(0));
    }

    private TextView getRepetitionsTextView(int childIndex)
    {
        LinearLayout exerciseDetailsLayout = getExerciseDetailsLayout(childIndex);
        return (TextView) (exerciseDetailsLayout.getChildAt(1));
    }

    private TextView geWeightTextView(int childIndex)
    {
        LinearLayout exerciseDetailsLayout = getExerciseDetailsLayout(childIndex);
        return (TextView) (exerciseDetailsLayout.getChildAt(2));
    }


    public void saveButtonClicked(View view)
    {
        String repetitionsFromSpinner = spinnerReps.getSelectedItem().toString();
        String repetitionsText = repetitionsFromSpinner + (repetitionsFromSpinner.equals("1") ? " rep" : " reps");
        String weightText = getString(R.string.Kg, spinnerWeight.getSelectedItem().toString());
        if(Integer.parseInt(repetitionsFromSpinner) != 0)
        {
            String buttonText = Globals.viewToString(view);
            if(buttonText.equals(getString(R.string.ChangeButton)))
            {
                getRepetitionsTextView(currentExerciseDetailsId - 1).setText(repetitionsText);
                geWeightTextView(currentExerciseDetailsId - 1).setText(weightText);
            }
            else if(buttonText.equals(getString(R.string.SaveButton)))
            {
                createNewEntry(String.valueOf(++sets), repetitionsText, weightText);
            }
        }
    }

    private void createNewEntry(String setsText, String repetitionsText, String weightText)
    {
        LinearLayout layout = findViewById(R.id.linearLayoutForExercises);

        LayoutInflater inflater = this.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_helpers, layout, false);

        RelativeLayout main = rowView.findViewById(R.id.exerciseDetailsBackground);

        TextView textView = rowView.findViewById(R.id.setView);
        textView.setText(setsText);

        textView = rowView.findViewById(R.id.repsView);
        textView.setText(repetitionsText) ;

        textView = rowView.findViewById(R.id.weightView);
        textView.setText(weightText);

        layout.addView(main);
    }

    private void setLayoutToDefault()
    {
        isExerciseDetailsClicked = false;
        currentExerciseDetailsId = 999;
        ((Button)findViewById(R.id.SaveButton)).setText(R.string.SaveButton);
        ((Button)findViewById(R.id.ClearButton)).setText(R.string.ClearButton);
        spinnerReps.setSelection(0);
        spinnerWeight.setSelection(0);
    }

    public void exerciseDetailsClicked(View view)
    {
        LinearLayout ll = (LinearLayout)((RelativeLayout)view).getChildAt(0);

        Integer currentExerciseDetailsIdTemp = Integer.parseInt(((TextView)(ll.getChildAt(0))).getText().toString());
        if(isExerciseDetailsClicked && currentExerciseDetailsId.equals(currentExerciseDetailsIdTemp))
        {
            setLayoutToDefault();
            return;
        }
        isExerciseDetailsClicked = true;
        currentExerciseDetailsId = currentExerciseDetailsIdTemp;
        ((Button)findViewById(R.id.SaveButton)).setText(R.string.ChangeButton);
        ((Button)findViewById(R.id.ClearButton)).setText(R.string.DeleteButton);

        Log.v("TAG", "layoutClicked: " + view.getId() + " currentExerciseDetailsId: " + currentExerciseDetailsId);

        TextView reps = (TextView)(ll.getChildAt(1));
        TextView weight = (TextView)(ll.getChildAt(2));

        spinnerReps.setSelection(Integer.parseInt(reps.getText().toString().split(" ")[0])-1);
        Double val = Double.parseDouble(weight.getText().toString().split(" ")[0])*2;
        spinnerWeight.setSelection(val.intValue());
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

    private WorkoutDetailsEntity collectDataBeforeExiting()
    {
        ArrayList<Double> weights = new ArrayList<>(sets);
        ArrayList<Integer> reps = new ArrayList<>(sets);

        for(int i =0; i< sets; ++i)
        {
            TextView repetitions = getRepetitionsTextView(i);
            TextView weightsView = geWeightTextView(i);

            Double weight = Double.parseDouble(weightsView.getText().toString().split(" ")[0]);
            Integer repetition = Integer.parseInt(repetitions.getText().toString().split(" ")[0]);

            weights.add(weight);
            reps.add(repetition);
        }

        return new WorkoutDetailsEntityBuilder().setRepetitions(reps).
                setWeights(weights).
                setSets(sets).
                setWorkoutName(m_WorkoutName).
                setBodyPart(m_BodyPart).
                build();
    }

    @Override
    public void onBackPressed()
    {
        if(isExerciseDetailsClicked)
        {
            setLayoutToDefault();
            return;
        }
        Log.v("Debug", "onBackPressed ");
        Log.v("Debug", "onBackPressed m_BodyPart " + m_BodyPart);

        Intent i = new Intent();
        i.putExtra(GET_EDIT_DATA, collectDataBeforeExiting());

        setResult(RESULT_OK, i);
        finish();
    }

    public void fabClicked(View view) throws UnsupportedEncodingException {
        Intent i = new Intent();
        i.putExtra(GET_EDIT_DATA, collectDataBeforeExiting());
        setResult(RESULT_OK, i);
        finish();
//        String query = m_BodyPart + " " + m_WorkoutName + " workout";
//        String escapedQuery = URLEncoder.encode(query, "UTF-8");
//        Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }
}
