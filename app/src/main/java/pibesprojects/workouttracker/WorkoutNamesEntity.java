package pibesprojects.workouttracker;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "workoutNamesTable")
public class WorkoutNamesEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String bodyPart;
    public ArrayList<String> workoutNames;

    public WorkoutNamesEntity()
    {
        workoutNames = new ArrayList<>();
    }

    public WorkoutNamesEntity(Parcel in) {
        uid = in.readInt();
        bodyPart = in.readString();
        workoutNames = in.createStringArrayList();
    }

    public static final Creator<WorkoutNamesEntity> CREATOR = new Creator<WorkoutNamesEntity>() {
        @Override
        public WorkoutNamesEntity createFromParcel(Parcel in) {
            return new WorkoutNamesEntity(in);
        }

        @Override
        public WorkoutNamesEntity[] newArray(int size) {
            return new WorkoutNamesEntity[size];
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
}
