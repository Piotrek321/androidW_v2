package pibesprojects.workouttracker;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class WorkoutNamesEntityTest {
    private AppDatabase m_database;
    private String workout1 = "workout1";
    private String workout2 = "workout2";
    private String bodyPart = "bodyPart";
    private String bodyPart2 = "bodyPart2";
    private String bodyPart3 = "bodyPart3";
    private ArrayList<String> bodyParts = new ArrayList<>(
            Arrays.asList(bodyPart, bodyPart2, bodyPart3));

    private ArrayList<String> workoutNames = new ArrayList<>(
        Arrays.asList(workout1, workout2));


    private int sleepDuration = 10;
    private WorkoutNamesEntity workoutNamesEntity;

    @Before
    public void initDb() {
        m_database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();

        workoutNamesEntity = createDefaultWorkoutNamesEntity(bodyPart);
    }

    WorkoutNamesEntity createDefaultWorkoutNamesEntity(String bodyPart)
    {
        WorkoutNamesEntity workoutNamesEntity = new WorkoutNamesEntity();
        workoutNamesEntity.setBodyPart(bodyPart);
        workoutNamesEntity.setWorkoutNames(workoutNames);

        return workoutNamesEntity;
    }
    @After
    public void closeDb() throws Exception {
        m_database.close();
    }

    @Test
    public void test_CreateWorkoutNameEntity_InsertIntoDBAndThenReadFromIt()
    {
        m_database.workoutNamesDao().insertAll(workoutNamesEntity);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutNamesEntity> workoutNamesEntityFromDB = m_database.workoutNamesDao().getAll();

        assertThat(workoutNamesEntityFromDB.size(), comparesEqualTo(1));
        assertThat(bodyPart, comparesEqualTo( workoutNamesEntityFromDB.get(0).getBodyPart()));
        assertEquals(workoutNames,workoutNamesEntityFromDB.get(0).getWorkoutNames());
    }

    @Test
    public void test_CreateThreeWorkoutNameEntities_GetAllBodyParts()
    {
        WorkoutNamesEntity workoutNamesEntity2 = createDefaultWorkoutNamesEntity(bodyPart2);
        WorkoutNamesEntity workoutNamesEntity3 = createDefaultWorkoutNamesEntity(bodyPart3);

        m_database.workoutNamesDao().insertAll(workoutNamesEntity, workoutNamesEntity2, workoutNamesEntity3);
        android.os.SystemClock.sleep(sleepDuration);

        List<String> bodyPartsFromDB = m_database.workoutNamesDao().getAllBodyParts();

        assertThat(bodyPartsFromDB.size(), comparesEqualTo(3));
        assertEquals(bodyParts,bodyPartsFromDB);
    }
}
