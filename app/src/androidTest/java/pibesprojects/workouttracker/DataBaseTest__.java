//package pibesprojects.workouttracker;
//
//
//import android.arch.persistence.room.Room;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.filters.MediumTest;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
//import static org.junit.Assert.assertEquals;
//
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//public class DataBaseTest__ {
//    private AppDatabase m_database;
//    private Integer sets = 2;
//    private ArrayList<Integer> repetitions = new ArrayList<>(
//            Arrays.asList(1,2));
//    private ArrayList<Double> weights = new ArrayList<>(
//            Arrays.asList(1.0,2.0));
//
//    private String workoutName = "workoutName";
//    private String date = "2019/03/01";
//    private String bodyPart = "bodyPart";
//
//
//        private Integer sets2 = 3;
//    private ArrayList<Integer> repetitions2 = new ArrayList<>(
//            Arrays.asList(3,4));
//    private ArrayList<Double> weights2 = new ArrayList<>(
//            Arrays.asList(3.0,4.0));
//
//    private String workoutName2 = "workoutName2";
//    private String date2 = "2019/03/30";
//    private String bodyPart2 = "bodyPart2";
//    private WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity1()
//    {
//        return new WorkoutDetailsEntityBuilder().setSets(sets)
//                .setRepetitions(repetitions)
//                .setWeights(weights)
//                .setWorkoutName(workoutName)
//                .setDate(date)
//                .setBodyPart(bodyPart);
//    }
//        private WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity2()
//    {
//        return new WorkoutDetailsEntityBuilder().setSets(sets2)
//                .setRepetitions(repetitions2)
//                .setWeights(weights2)
//                .setWorkoutName(workoutName2)
//                .setDate(date2)
//                .setBodyPart(bodyPart2);
//    }
//    private void compareWorkoutDetails1(WorkoutDetailsEntity workoutDetailsEntity)
//    {
//        assertEquals("Number of sets are not correct" , sets,  workoutDetailsEntity.getSets());
//        assertEquals("Repetitions are not correct" , repetitions,  workoutDetailsEntity.getRepetitions());
//        assertEquals("Weights are not correct" , weights,  workoutDetailsEntity.getWeights());
//        assertEquals("WorkoutName is not correct" , workoutName,  workoutDetailsEntity.getWorkoutName());
//        assertEquals("Date is not correct" , date,  workoutDetailsEntity.getDate());
//        assertEquals("BodyPart is not correct" , bodyPart,  workoutDetailsEntity.getBodyPart());
//    }
//
//        private void compareWorkoutDetails2(WorkoutDetailsEntity workoutDetailsEntity, String dateToCompare)
//    {
//        assertThat(sets2, comparesEqualTo( workoutDetailsEntity.getSets()));
//        assertEquals(repetitions2,workoutDetailsEntity.getRepetitions());
//        assertEquals(weights2,workoutDetailsEntity.getWeights());
//        assertThat(workoutName2, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
//        assertThat(dateToCompare, comparesEqualTo( workoutDetailsEntity.getDate()));
//        assertThat(bodyPart2, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
//    }
//    @Before
//    public void initDb()  {
//        m_database = Room.inMemoryDatabaseBuilder(
//                InstrumentationRegistry.getContext(),
//                AppDatabase.class)
//                .build();
//    }
//
//    @After
//    public void closeDb() throws Exception {
//        m_database.close();
//    }
//
//    @Rule
//    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void insertElementIntoDB_GetAll_MakeSureThereIsOnlyOneElement() throws Exception {
//
//        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//
//        WorkoutsForDay w = new WorkoutsForDay(date, workoutList);
//        m_database.workoutForDayDao().insertAll(w);
//
//        List<WorkoutsForDay> workoutDetailsEntities = m_database.workoutForDayDao().getAll();
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 1);
//        compareWorkoutDetails1(workoutDetailsEntities.get(0).workoutDetailsEntityList.get(0));
//    }
//
//    @Test
//    public void insertTwoElementsIntoDB_GetAll_EnsureProperAmountWillBeReturned() throws Exception {
//
//        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//        workoutList.add(createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay w = new WorkoutsForDay(date, workoutList);
//        m_database.workoutForDayDao().insertAll(w);
//
//        List<WorkoutsForDay> workoutDetailsEntities = m_database.workoutForDayDao().getAll();
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 2);
//        compareWorkoutDetails1(workoutDetailsEntities.get(0).workoutDetailsEntityList.get(0));
//        compareWorkoutDetails2(workoutDetailsEntities.get(0).workoutDetailsEntityList.get(1), date2);
//    }
//
//    @Test
//    public void insertTwoSameElementIntoDB_GetAll_EnsureProperAmountWillBeReturned()  throws Exception {
//
//        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//
//        WorkoutsForDay w = new WorkoutsForDay(date, workoutList);
//        m_database.workoutForDayDao().insertAll(w);
//
//        List<WorkoutsForDay> workoutDetailsEntities = m_database.workoutForDayDao().getAll();
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 2);
//        compareWorkoutDetails1(workoutDetailsEntities.get(0).workoutDetailsEntityList.get(0));
//    }
//
//    @Test
//    public void insertTwoElementsIntoDB_GetForDate_EnsureProperAmountWillBeReturned() throws Exception {
//        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutList2 = new ArrayList<>();
//        workoutList2.add(createTestWorkoutDetailsEntity1().build());
//        workoutList2.add(createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay w = new WorkoutsForDay(date, workoutList);
//        WorkoutsForDay w2 = new WorkoutsForDay(date2, workoutList2);
//        m_database.workoutForDayDao().insertAll(w, w2);
//
//        List<WorkoutsForDay> workoutDetailsEntities = m_database.workoutForDayDao().getWorkoutForGivenDate(date2);
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 2);
//        workoutDetailsEntities = m_database.workoutForDayDao().getWorkoutForGivenDate(date);
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 1);
//
//        workoutDetailsEntities = m_database.workoutForDayDao().getAll();
//        assertEquals(workoutDetailsEntities.size(), 2);
//    }
//
//
//    @Test
//    public void insertElementIntoDB_Update_MakeSureThereIsOnlyOneElement() throws Exception {
//
//        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
//        workoutList.add(createTestWorkoutDetailsEntity1().build());
//
//        WorkoutsForDay w = new WorkoutsForDay(date, workoutList);
//        m_database.workoutForDayDao().update (w);
//
//        List<WorkoutsForDay> workoutDetailsEntities = m_database.workoutForDayDao().getAll();
//        assertEquals(workoutDetailsEntities.size(), 1);
//        assertEquals(workoutDetailsEntities.get(0).workoutDetailsEntityList.size(), 1);
//        compareWorkoutDetails1(workoutDetailsEntities.get(0).workoutDetailsEntityList.get(0));
//    }
//}