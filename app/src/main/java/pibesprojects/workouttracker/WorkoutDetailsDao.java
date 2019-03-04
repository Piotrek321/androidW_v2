package pibesprojects.workouttracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WorkoutDetailsDao {

    @Query("DELETE FROM workoutDetailsTable")
    void nukeTable();

    @Query("DELETE FROM workoutDetailsTable WHERE date=:date")
    void deleteForGivenDate(String date);

    @Query("SELECT * FROM workoutDetailsTable")
    List<WorkoutDetailsEntity> getAll();

    @Query("SELECT * FROM workoutDetailsTable WHERE date=:date")
    List<WorkoutDetailsEntity> getWorkoutForGivenDate(String date);

    @Query("SELECT * FROM workoutDetailsTable WHERE date BETWEEN :dateFirst AND :dayLast")
    List<WorkoutDetailsEntity> getWorkoutsForGivenPeriod(String dateFirst, String dayLast);

    @Insert
    void insertAll(WorkoutDetailsEntity... workouts);
    @Update
    void update(WorkoutDetailsEntity... workouts);
    @Delete
    void delete(WorkoutDetailsEntity workout);
}

