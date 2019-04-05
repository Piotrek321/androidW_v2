package pibesprojects.workouttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class WorkoutEntryList extends RelativeLayout {
    private RelativeLayout mContentView;
    static int i =0;
    private TextView m_NumberOfReps;
    private TextView m_WorkoutName;
    private TextView m_NumberOfSets;
    private TextView m_Weight;
    private ImageView m_WorkoutImage;
    private TextView m_BodyPart;

    public WorkoutEntryList(Context context) {
        this(context, null);
    }

    public WorkoutEntryList(Context context, AttributeSet attrs)
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

    public WorkoutEntryList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //Inflate and attach your child XML
        LayoutInflater.from(context).inflate(R.layout.workout_row, this);
        //Get a reference to the layout where you want children to be placed
        mContentView = findViewById(R.id.relativeLayout);
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