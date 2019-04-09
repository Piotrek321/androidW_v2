package pibesprojects.workouttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class WorkoutDataLayout extends RelativeLayout {
    private RelativeLayout mContentView;
    private TextView m_NumberOfReps;
    private TextView m_WorkoutName;
    private TextView m_NumberOfSets;
    private TextView m_Weight;
    private ImageView m_WorkoutImage;
    private TextView m_BodyPart;
    private ImageView m_TrashButton;


    public WorkoutDataLayout(Context context) {
        this(context, null);
    }

    public WorkoutDataLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.workout_row, this);
        m_WorkoutName = findViewById(R.id.workoutName);
        m_NumberOfSets = findViewById(R.id.numberOfSets);
        m_NumberOfReps = findViewById(R.id.numberOfReps);
        m_Weight = findViewById(R.id.weight);
        m_WorkoutImage = findViewById(R.id.workoutImage);
        m_BodyPart = findViewById(R.id.bodyPart);
        m_TrashButton = findViewById(R.id.trashBin);
    }

    public void createEntry(String workoutName, String numberOfSets, String numberOfReps, String weight, Bitmap image, String bodyPart)
    {
        m_WorkoutName.setText(workoutName);
        m_NumberOfSets.setText(numberOfSets);
        m_NumberOfReps.setText(numberOfReps);
        m_Weight.setText(weight);
        m_WorkoutImage.setImageBitmap(image);
        m_BodyPart.setText(bodyPart);
    }

    public WorkoutDetailsEntity convertWorkoutDataLayoutToWorkoutDetails()
    {
        WorkoutDetailsEntity workoutDetailsEntity = new WorkoutDetailsEntity();

        String reps = getNumberOfReps().getText().toString();
        ArrayList<String> splittedRepsString = new ArrayList<>(Arrays.asList(reps.split(" ")));
        ArrayList<Integer> splittedRepsInteger = new ArrayList<>();
        for(String rep: splittedRepsString){
            if(rep.equals("Reps:") || rep.equals(" ") || rep.equals("") ) continue;
            splittedRepsInteger.add(Integer.parseInt(rep.trim()));
        }
        workoutDetailsEntity.setRepetitions(splittedRepsInteger);

        String weights = getWeight().getText().toString();
        ArrayList<String> splittedWeightString = new ArrayList<>(Arrays.asList(weights.split(" ")));
        ArrayList<Double> splittedWeightDouble = new ArrayList<>();
        for(String fav: splittedWeightString){
            if(fav.equals("Weight:") || fav.equals(" ") || fav.equals("") ) continue;
            splittedWeightDouble.add(Double.parseDouble(fav.trim()));
        }
        workoutDetailsEntity.setWeights(splittedWeightDouble);
        workoutDetailsEntity.setWorkoutName(getWorkoutName().getText().toString());

        int position = findIntegersPosition(getNumberOfSets().getText().toString());
        String number = getNumberOfSets().getText().toString().substring(position);
        workoutDetailsEntity.setSets(Integer.parseInt(number));
        workoutDetailsEntity.setBodyPart(getBodyPart().getText().toString());
        Log.v("Debug", "convertWorkoutDataLayoutToWorkoutDetails m_BodyPart: " + getBodyPart());

        Log.v("Debug", "convertWorkoutDataLayoutToWorkoutDetails workoutDetailsEntity.getWorkoutName(): " + workoutDetailsEntity.getWorkoutName());

        return workoutDetailsEntity;
    }


    private static Integer findIntegersPosition(String s)
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
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(mContentView == null)
        {
            super.addView(child, index, params);
        }
        else
        {
            //Forward these calls to the content view
            mContentView.addView(child, index, params);
        }
    }

    public void pressTrashBinButton()
    {
        m_TrashButton.performClick();
    }

    public TextView getNumberOfReps() {
        return m_NumberOfReps;
    }

    public void setNumberOfReps(TextView m_NumberOfReps) {
        this.m_NumberOfReps = m_NumberOfReps;
    }

    public TextView getWorkoutName() {
        return m_WorkoutName;
    }

    public void setWorkoutName(TextView m_WorkoutName) {
        this.m_WorkoutName = m_WorkoutName;
    }

    public TextView getNumberOfSets() {
        return m_NumberOfSets;
    }

    public void setNumberOfSets(TextView m_NumberOfSets) {
        this.m_NumberOfSets = m_NumberOfSets;
    }

    public TextView getWeight() {
        return m_Weight;
    }

    public void setWeight(TextView m_Weight) {
        this.m_Weight = m_Weight;
    }

    public ImageView getWorkoutImage() {
        return m_WorkoutImage;
    }

    public void setWorkoutImage(ImageView m_WorkoutImage) {
        this.m_WorkoutImage = m_WorkoutImage;
    }

    public TextView getBodyPart() {
        return m_BodyPart;
    }

    public void setBodyPart(TextView m_BodyPart) {
        this.m_BodyPart = m_BodyPart;
    }

}