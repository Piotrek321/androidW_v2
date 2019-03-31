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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.junit.Assert.assertEquals;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
public AppDatabase m_database;
    private Integer sets = 2;
    private ArrayList<Integer> repetitions = new ArrayList<>();
    private ArrayList<Double> weights = new ArrayList<>();
    private String workoutName = "workoutName";
    private String date = "2019/03/01";
    private String bodyPart = "bodyPart";
    private String newBodyPartName = "newBodyPart";

    private Integer sets2 = 3;
    private ArrayList<Integer> repetitions2 = new ArrayList<>();
    private ArrayList<Double> weights2 = new ArrayList<>();
    private String workoutName2 = "workoutName2";
    private String date2 = "2019/03/30";

    private String bodyPart2 = "bodyPart2";

    private String dateAfterPeriod = "2019/02/01";
    private String dateBeforePeriod= "2019/05/01";

    @Before
    public void initDb() throws Exception {
        m_database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();

        repetitions.add(1);
        repetitions.add(2);
        weights.add(1.0);
        weights.add(2.0);

        repetitions2.add(3);
        repetitions2.add(4);
        weights2.add(3.0);
        weights2.add(4.0);
    }

    @After
    public void closeDb() throws Exception {
        m_database.close();
    }

    private WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity1()
    {
        return new WorkoutDetailsEntityBuilder().setSets(sets)
        .setRepetitions(repetitions)
        .setWeights(weights)
        .setWorkoutName(workoutName)
        .setDate(date)
        .setBodyPart(bodyPart);
    }

    private void compareWorkoutDetails1(WorkoutDetailsEntity workoutDetailsEntity)
    {
        assertThat(sets, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions,workoutDetailsEntity.getRepetitions());
        assertEquals(weights,workoutDetailsEntity.getWeights());
        assertThat(workoutName, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(date, comparesEqualTo( workoutDetailsEntity.getDate()));
        assertThat(bodyPart, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

    private WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity2()
    {
        return new WorkoutDetailsEntityBuilder().setSets(sets2)
                .setRepetitions(repetitions2)
                .setWeights(weights2)
                .setWorkoutName(workoutName2)
                .setDate(date2)
                .setBodyPart(bodyPart2);
    }

    private void compareWorkoutDetails2(WorkoutDetailsEntity workoutDetailsEntity, String dateToCompare)
    {
        assertThat(sets2, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions2,workoutDetailsEntity.getRepetitions());
        assertEquals(weights2,workoutDetailsEntity.getWeights());
        assertThat(workoutName2, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(dateToCompare, comparesEqualTo( workoutDetailsEntity.getDate()));
        assertThat(bodyPart2, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

    @Test
    public void test_CreateWorkoutDetailsEntity_InsertIntoDBAndThenReadFromIt()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_InsertIntoDBAndThenReadAllFromIt()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(2));
        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
        compareWorkoutDetails2(workoutDetailsEntityFromDB.get(1), date2);
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntitiesForDifferentDates_InsertIntoDBAndGetWorkoutForGivenDate()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
        assertThat(workoutDetailsEntityFromDB2.size(), comparesEqualTo(1));

        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
        compareWorkoutDetails2(workoutDetailsEntityFromDB2.get(0), date2);
    }

    @Test
    public void test_CreateThreeWorkoutDetailsEntitiesThirdWorkoutsDateIsAfterPeriod_InsertIntoDBAndThenReadForPeriod()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();
        WorkoutDetailsEntity workoutDetailsEntity3 = createTestWorkoutDetailsEntity2().setDate(dateAfterPeriod).build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2, workoutDetailsEntity3);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getWorkoutsForGivenPeriod(date, date2);

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(2));

        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
        compareWorkoutDetails2(workoutDetailsEntityFromDB.get(1), date2);
    }

    @Test
    public void test_CreateThreeWorkoutDetailsEntitiesThirdWorkoutsDateIsBeforePeriod_InsertIntoDBAndThenReadForPeriod()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();
        WorkoutDetailsEntity workoutDetailsEntity3 = createTestWorkoutDetailsEntity2().setDate(dateBeforePeriod).build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2, workoutDetailsEntity3);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getWorkoutsForGivenPeriod(date, date2);

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(2));

        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
        compareWorkoutDetails2(workoutDetailsEntityFromDB.get(1), date2);
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntitiesForDifferentDates_InsertIntoDBAndGetWorkoutForGivenDateDeleteWorkoutForGivenDate()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
        assertThat(workoutDetailsEntityFromDB2.size(), comparesEqualTo(1));

        compareWorkoutDetails1(workoutDetailsEntityFromDB.get(0));
        compareWorkoutDetails2(workoutDetailsEntityFromDB2.get(0), date2);

        m_database.workoutDetailsDao().deleteForGivenDate(date);
        workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        workoutDetailsEntityFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(0));
        assertThat(workoutDetailsEntityFromDB2.size(), comparesEqualTo(1));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_DeleteWorkout()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(2));

        m_database.workoutDetailsDao().delete(workoutDetailsEntityFromDB.get(0));

        workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_DeleteAll()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();
        WorkoutDetailsEntity workoutDetailsEntity2 = createTestWorkoutDetailsEntity2().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity, workoutDetailsEntity2);
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(2));
        m_database.workoutDetailsDao().deleteAll();

        workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(0));
    }

    @Test
    public void test_InsertWorkoutDetailsEntityWithTheSameUidTwice_ShouldReturnOneEntity()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity );
        android.os.SystemClock.sleep(100);

        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();
        m_database.workoutDetailsDao().insertAll(workoutDetailsEntityFromDB.get(0) );
        workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
    }

    @Test
    public void test_UpdateWorkoutDetailsEntityWithTheSameUidTwice_ShouldReturnOneEntity()
    {
        WorkoutDetailsEntity workoutDetailsEntity = createTestWorkoutDetailsEntity1().build();

        m_database.workoutDetailsDao().insertAll(workoutDetailsEntity );
        android.os.SystemClock.sleep(100);
        List<WorkoutDetailsEntity> workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();
        workoutDetailsEntityFromDB.get(0).setBodyPart(newBodyPartName);

        m_database.workoutDetailsDao().update(workoutDetailsEntityFromDB.get(0));
        workoutDetailsEntityFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutDetailsEntityFromDB.size(), comparesEqualTo(1));
        assertThat(newBodyPartName, comparesEqualTo( workoutDetailsEntityFromDB.get(0).getBodyPart()));

    }
}
