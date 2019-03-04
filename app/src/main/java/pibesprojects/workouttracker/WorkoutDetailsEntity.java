package pibesprojects.workouttracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "workoutDetailsTable")
public class WorkoutDetailsEntity implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int uid;
    private Integer sets;
    private ArrayList reps;
    private String workoutName;
    private ArrayList weightForEachSet;
    private String date;
    private String bodyPart;

    public WorkoutDetailsEntity(){
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
        pc.writeList(reps);
        pc.writeString(workoutName);
        pc.writeList(weightForEachSet);
        pc.writeString(date);
        pc.writeString(bodyPart);
    }

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public WorkoutDetailsEntity(Parcel pc){
        sets = pc.readInt();
        reps = pc.readArrayList(Integer.class.getClassLoader());
        workoutName = pc.readString();
        weightForEachSet = pc.readArrayList(Double.class.getClassLoader());
        date = pc.readString();
        bodyPart = pc.readString();
    }

    public ArrayList getReps() {
        return reps;
    }

    public void setReps(ArrayList<Integer> reps) {
        this.reps = reps;
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

    public ArrayList getWeightForEachSet() {
        return weightForEachSet;
    }

    public void setWeightForEachSet(ArrayList<Double> setsWeight) {
        this.weightForEachSet = setsWeight;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getBodyPart() { return bodyPart; }

    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }
}