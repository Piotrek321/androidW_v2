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
    List<WorkoutNamesEntity> getAll();

    @Query("SELECT bodyPart FROM workoutNamesTable")
    List<String> getAllBodyParts();

    @Query("SELECT * FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    WorkoutNamesEntity getWorkoutsNameEnitiy(String bodyPart_);

    @Query("DELETE FROM workoutNamesTable")
    void deleteAll();

    @Query("DELETE FROM workoutNamesTable WHERE bodyPart=:bodyPart_")
    void deleteWorkoutsNameEntity(String bodyPart_);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WorkoutNamesEntity... workoutNameEntities);
    @Update
    void update(WorkoutNamesEntity... workouts);
    @Delete
    void delete(WorkoutNamesEntity workoutNameEntities);
}
