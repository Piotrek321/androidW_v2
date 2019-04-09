package pibesprojects.workouttracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WorkoutsForDayDao {

    @Query("DELETE FROM WorkoutsForDay")
    void deleteAll();

    @Query("DELETE FROM WorkoutsForDay WHERE date=:date")
    void deleteForGivenDate(String date);

    @Query("SELECT * FROM WorkoutsForDay")
    List<WorkoutsForDay> getAll();

    @Query("SELECT * FROM WorkoutsForDay WHERE date=:date")
    WorkoutsForDay getWorkoutForGivenDate(String date);

    @Query("SELECT * FROM WorkoutsForDay WHERE date BETWEEN :dateFirst AND :dayLast")
    List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dayLast);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WorkoutsForDay... workouts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(WorkoutsForDay... workouts);
    @Delete
    void delete(WorkoutsForDay workout);
}

