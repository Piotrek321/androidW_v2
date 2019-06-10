package pibesprojects.workouttracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Entity(tableName = "workoutNamesTable")
public class WorkoutNames implements Parcelable {
    @PrimaryKey
    @NotNull
    private String bodyPart;
    private ArrayList<String> workoutNames;
    @Ignore
    WorkoutNames()
    {
        workoutNames = new ArrayList<>();
    }
    @Ignore

    WorkoutNames(String bodyPart)
    {
        this.bodyPart = bodyPart;
        workoutNames = new ArrayList<>();
    }
    WorkoutNames(String bodyPart, ArrayList<String> workoutNames)
    {
        this.bodyPart = bodyPart;
        this.workoutNames = workoutNames;
    }
    private WorkoutNames(Parcel in) {
        bodyPart = in.readString();
        workoutNames = in.createStringArrayList();
    }

    public static final Creator<WorkoutNames> CREATOR = new Creator<WorkoutNames>() {
        @Override
        public WorkoutNames createFromParcel(Parcel in) {
            return new WorkoutNames(in);
        }

        @Override
        public WorkoutNames[] newArray(int size) {
            return new WorkoutNames[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bodyPart);
        dest.writeList(workoutNames);
    }


    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public ArrayList<String> getWorkoutNames() {
        return workoutNames;
    }

    public void setWorkoutNames(ArrayList<String> workoutNames) {
        this.workoutNames = workoutNames;
    }
}
