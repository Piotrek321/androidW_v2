package pibesprojects.workouttracker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = "WorkoutsForDay")
public class WorkoutsForDay implements Parcelable
{
    public void setDate(@NotNull String date) {
        this.date = date;
    }

    public void setWorkoutDetailsEntityList(List<WorkoutDetailsEntity> workoutDetailsEntityList) {
        this.workoutDetailsEntityList = workoutDetailsEntityList;
    }

    @PrimaryKey()
    @NotNull
    private String date;
    private List<WorkoutDetailsEntity> workoutDetailsEntityList;

    @NotNull
    public String getDate() {
        return date;
    }

    public List<WorkoutDetailsEntity> getWorkoutDetailsEntityList() {
        for(WorkoutDetailsEntity workoutDetailsEntity: workoutDetailsEntityList)
        {
            workoutDetailsEntity.setDate(date);
        }
        return workoutDetailsEntityList;
    }

    public WorkoutsForDay()
    {

    }

    public WorkoutsForDay(@NonNull String date, List<WorkoutDetailsEntity> workoutDetailsEntities)
    {
        this.date = date;
        this.workoutDetailsEntityList = workoutDetailsEntities;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(date);
        pc.writeList(workoutDetailsEntityList);
    }

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public WorkoutsForDay(Parcel pc){
        date = pc.readString();
        workoutDetailsEntityList =  pc.readArrayList(WorkoutDetailsEntity.class.getClassLoader());
    }

    public static final Creator<WorkoutsForDay> CREATOR = new Creator<WorkoutsForDay>() {
        @Override
        public WorkoutsForDay createFromParcel(Parcel in) {
            return new WorkoutsForDay(in);
        }

        @Override
        public WorkoutsForDay[] newArray(int size) {
            return new WorkoutsForDay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
