package pibesprojects.workouttracker;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class WorkoutDetailsEntity implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int uid;
    private Integer sets;
    private ArrayList<Integer> repetitions;
    private ArrayList<Double> weights;
    private String workoutName;
    private String bodyPart;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    @Ignore
    public WorkoutDetailsEntity(){}

    public WorkoutDetailsEntity(Integer sets, ArrayList<Integer> repetitions, ArrayList<Double> weights,
                                String workoutName, String bodyPart){
        this.sets = sets;
        this.repetitions = repetitions;
        this.weights = weights;
        this.workoutName = workoutName;
        this.bodyPart = bodyPart;
    }

    public static final Parcelable.Creator<WorkoutDetailsEntity> CREATOR = new Parcelable.Creator<WorkoutDetailsEntity>()
    {
        public WorkoutDetailsEntity createFromParcel(Parcel pc) {
            return new WorkoutDetailsEntity(pc);
        }
        public WorkoutDetailsEntity[] newArray(int size) {
            return new WorkoutDetailsEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeInt(sets);
        pc.writeList(repetitions);
        pc.writeList(weights);
        pc.writeString(workoutName);
        pc.writeString(bodyPart);
        pc.writeString(date);
    }

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public WorkoutDetailsEntity(Parcel pc){
        sets = pc.readInt();
        repetitions = pc.readArrayList(Integer.class.getClassLoader());
        weights = pc.readArrayList(Double.class.getClassLoader());
        workoutName = pc.readString();
        bodyPart = pc.readString();
        if(sets != repetitions.size() || sets != weights.size())
        {
            String message = Thread.currentThread().getStackTrace().toString();
            message += " number of sets must be equal to repetitions size and weights size";
            throw new RuntimeException(message);
        }
        date = pc.readString();
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public ArrayList getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(ArrayList<Integer> repetitions) {
        this.repetitions = repetitions;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public String getBodyPart() { return bodyPart; }

    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public <Type> String arrayToString(ArrayList<Type> array)
    {
        //It must be done like this because toString on Array returns [1, 2, 3] and I need to remove [ and ]
        String toString = array.toString().substring(1, array.toString().length() - 1);
        return toString.replace(",", "");
    }

    public String getRepetitionsAsString()
    {

        return arrayToString(repetitions);
    }

    public String getWeightAsString()
    {
        return arrayToString(weights);
    }


    WorkoutDataLayout convertToWorkoutDataLayout(Context context) {
        WorkoutDataLayout workoutDataLayout = new WorkoutDataLayout(context, null);
        workoutDataLayout.createEntry(
                getWorkoutName(),
                context.getString(R.string.SetsInteger, getRepetitions().size()),
                context.getString(R.string.Reps, getRepetitionsAsString()),
                context.getString(R.string.Weight, getWeightAsString()),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher),//benchpress), //m_workoutImageMap.get(wd.getWorkoutName())),
                getBodyPart());
        workoutDataLayout.m_Date = date;
        return workoutDataLayout;
    }
}