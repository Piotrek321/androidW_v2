package pibesprojects.workouttracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WorkoutNamesDao {
    @Query("SELECT * FROM workoutNamesTable")
    List<WorkoutNames> getAll();

    @Query("SELECT bodyPart FROM workoutNamesTable")
    List<String> getAllBodyParts();

    @Query("SELECT * FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    WorkoutNames getWorkoutsNameEnitiy(String bodyPart_);

    @Query("DELETE FROM workoutNamesTable")
    void deleteAll();

    @Query("DELETE FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    void deleteWorkoutsNameEntity(String bodyPart_);

    @Query("SELECT * FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    WorkoutNames getWorkoutsNameForGivenBodyPart(String bodyPart_);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WorkoutNames... workoutNameEntities);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(WorkoutNames... workouts);
    @Delete
    void delete(WorkoutNames workoutNameEntities);
}
