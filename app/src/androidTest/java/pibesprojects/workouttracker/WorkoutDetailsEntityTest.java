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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.junit.Assert.assertEquals;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class WorkoutDetailsEntityTest {
    public static <T> void assertNotNull(T object) {
        if (object == null)
            throw new AssertionError("Object cannot be null");
    }
    public static <T> void assertNull(T object) {
        if (object != null)
            throw new AssertionError("Object must be null");
    }

    private AppDatabase m_database;
    private Integer sets = 2;
    private ArrayList<Integer> repetitions = new ArrayList<>(
            Arrays.asList(1,2));
    private ArrayList<Double> weights = new ArrayList<>(
            Arrays.asList(1.0,2.0));

    private String workoutName = "workoutName";
    private String date = "2019/03/01";
    private String bodyPart = "bodyPart";

    private Integer sets2 = 3;
    private ArrayList<Integer> repetitions2 = new ArrayList<>(
            Arrays.asList(3,4));
    private ArrayList<Double> weights2 = new ArrayList<>(
            Arrays.asList(3.0,4.0));

    private String workoutName2 = "workoutName2";
    private String date2 = "2019/03/30";
    private String bodyPart2 = "bodyPart2";

    private int sleepDuration = 10;

    @Before
    public void initDb()  {
        m_database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();
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
        .setBodyPart(bodyPart);
    }

    private void compareWorkoutDetails1(WorkoutDetailsEntity workoutDetailsEntity)
    {
        assertThat(sets, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions,workoutDetailsEntity.getRepetitions());
        assertEquals(weights,workoutDetailsEntity.getWeights());
        assertThat(workoutName, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(bodyPart, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

    private WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity2()
    {
        return new WorkoutDetailsEntityBuilder().setSets(sets2)
                .setRepetitions(repetitions2)
                .setWeights(weights2)
                .setWorkoutName(workoutName2)
                .setBodyPart(bodyPart2);
    }

    private void compareWorkoutDetails2(WorkoutDetailsEntity workoutDetailsEntity, String dateToCompare)
    {
        assertThat(sets2, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions2,workoutDetailsEntity.getRepetitions());
        assertEquals(weights2,workoutDetailsEntity.getWeights());
        assertThat(workoutName2, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(bodyPart2, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

    @Test
    public void test_CreateWorkoutDetailsEntity_InsertIntoDBAndThenReadFromIt()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
        workoutDetailsEntities.add(createTestWorkoutDetailsEntity1().build());
        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities);
        
        m_database.workoutDetailsDao().insertAll(workoutsForDay);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
        compareWorkoutDetails1(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_InsertIntoDBAndThenReadAllFromIt()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
        workoutDetailsEntities.add(createTestWorkoutDetailsEntity1().build());
        workoutDetailsEntities.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities);

        m_database.workoutDetailsDao().insertAll(workoutsForDay);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
        assertThat(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().size(), comparesEqualTo(2));

        compareWorkoutDetails1(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
        compareWorkoutDetails2(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(1), date2);
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntitiesForDifferentDates_InsertIntoDBAndGetWorkoutForGivenDate()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2);

        WorkoutsForDay workoutForDayFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        WorkoutsForDay workoutForDayFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);

        assertThat(workoutForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));

        compareWorkoutDetails1(workoutForDayFromDB.getWorkoutDetailsEntityList().get(0));
        compareWorkoutDetails2(workoutForDayFromDB2.getWorkoutDetailsEntityList().get(0), date2);
    }

    @Test
    public void test_CreateThreeWorkoutDetailsEntitiesThirdWorkoutsDateIsAfterPeriod_InsertIntoDBAndThenReadForPeriod()
    {
        String dateAfterPeriod = "2019/02/01";

        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
        WorkoutsForDay workoutsForDay3 = new WorkoutsForDay(dateAfterPeriod, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2, workoutsForDay3);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutForDayFromDB = m_database.workoutDetailsDao().getWorkoutsForGivenPeriod(date, date2);

        assertThat(workoutForDayFromDB.size(), comparesEqualTo(2));

        compareWorkoutDetails1(workoutForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
        compareWorkoutDetails2(workoutForDayFromDB.get(1).getWorkoutDetailsEntityList().get(0), date2);
    }

    @Test
    public void test_CreateThreeWorkoutDetailsEntitiesThirdWorkoutsDateIsBeforePeriod_InsertIntoDBAndThenReadForPeriod()
    {
        String dateBeforePeriod = "2019/05/01";

        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
        WorkoutsForDay workoutsForDay3 = new WorkoutsForDay(dateBeforePeriod, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2, workoutsForDay3);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutForDayFromDB = m_database.workoutDetailsDao().getWorkoutsForGivenPeriod(date, date2);

        assertThat(workoutForDayFromDB.size(), comparesEqualTo(2));

        compareWorkoutDetails1(workoutForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
        compareWorkoutDetails2(workoutForDayFromDB.get(1).getWorkoutDetailsEntityList().get(0), date2);
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntitiesForDifferentDates_InsertIntoDBAndGetWorkoutForGivenDateDeleteWorkoutForGivenDate()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2);

        WorkoutsForDay workoutForDayFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        WorkoutsForDay workoutForDayFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);

        assertNotNull(workoutForDayFromDB);
        assertNotNull(workoutForDayFromDB2);
        assertThat(workoutForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));

        compareWorkoutDetails1(workoutForDayFromDB.getWorkoutDetailsEntityList().get(0));
        compareWorkoutDetails2(workoutForDayFromDB2.getWorkoutDetailsEntityList().get(0), date2);

        m_database.workoutDetailsDao().deleteForGivenDate(date);
        workoutForDayFromDB = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
        workoutForDayFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date2);
        assertNull(workoutForDayFromDB);
        assertNotNull(workoutForDayFromDB2);

        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_DeleteWorkout()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(2));

        m_database.workoutDetailsDao().delete(workoutsForDayFromDB.get(0));

        workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
    }

    @Test
    public void test_CreateTwoWorkoutDetailsEntities_DeleteAll()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();

        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(2));
        m_database.workoutDetailsDao().deleteAll();

        workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(0));
    }

    @Test
    public void test_InsertWorkoutDetailsEntityWithTheSameDateTwice_ShouldReturnOneEntity()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date, workoutDetailsEntities1);

        m_database.workoutDetailsDao().insertAll(workoutsForDay, workoutsForDay2);
        android.os.SystemClock.sleep(sleepDuration);

        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
    }

    @Test
    public void test_UpdateWorkoutDetailsEntityWithTheSameUidTwice_ShouldReturnOneEntity()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        m_database.workoutDetailsDao().insertAll(workoutsForDay);
        android.os.SystemClock.sleep(sleepDuration);

        String newBodyPartName = "newBodyPart";
        workoutsForDay.getWorkoutDetailsEntityList().get(0).setBodyPart(newBodyPartName);

        m_database.workoutDetailsDao().update(workoutsForDay);
        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
        assertThat(newBodyPartName, comparesEqualTo( workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0).getBodyPart()));
    }

    @Test
    public void test_UpdateWorkoutDetailsEntityWith_ShouldReturnOneEntity()
    {
        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());

        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
        m_database.workoutDetailsDao().insertAll(workoutsForDay);
        android.os.SystemClock.sleep(sleepDuration);

        workoutsForDay.getWorkoutDetailsEntityList().add(createTestWorkoutDetailsEntity2().build());

        m_database.workoutDetailsDao().update(workoutsForDay);
        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();
        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
        assertThat(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
    }

    @Test
    public void test_UpdateWorkoutDetailsEntityWith_ShouldReturnOneEntity2()
    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(createTestWorkoutDetailsEntity1().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        m_database.workoutDetailsDao().insertAll(workoutsForDay);
//        android.os.SystemClock.sleep(sleepDuration);
//
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(createTestWorkoutDetailsEntity2().build());
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date, workoutDetailsEntities2);
//
//        m_database.workoutDetailsDao().update(workoutsForDay2);
//        List<WorkoutsForDay> workoutsForDayFromDB = m_database.workoutDetailsDao().getAll();
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//        compareWorkoutDetails2(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0), date);
//        assertThat(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
//
//        WorkoutsForDay workoutsForDayFromDB2 = m_database.workoutDetailsDao().getWorkoutForGivenDate(date);
//        assertThat(workoutsForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
    }
}
