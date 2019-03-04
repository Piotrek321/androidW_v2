package pibesprojects.workouttracker;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Dao;

import java.util.List;

@Dao
public interface WorkoutNamesDao {
    @Query("SELECT * FROM workoutNamesTable")
    List<WorkoutNamesEntity> getAllWorkoutNames();

    @Query("SELECT bodyPart FROM workoutNamesTable")
    List<String> getAllBodyParts();

    @Query("DELETE FROM workoutNamesTable")
    void nukeTable();

    @Query("SELECT * FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    WorkoutNamesEntity getWorkoutsNameForGivenBodyPart(String bodyPart_);

    @Query("DELETE FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    void deleteWorkoutsNameForGivenBodyPart(String bodyPart_);

    @Update
    void update(WorkoutNamesEntity... workouts);

    @Insert
    void insertAll(WorkoutNamesEntity... workoutNameEntities);

}
