//package pibesprojects.workouttracker;
//
//
//import android.support.test.InstrumentationRegistry;
//import android.support.test.filters.MediumTest;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
//
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//public class WorkoutsForDayRepositoryTest {
//    public static <T> void assertNotNull(T object) {
//        if (object == null)
//            throw new AssertionError("Object cannot be null");
//    }
//    public static <T> void assertNull(T object) {
//        if (object != null)
//            throw new AssertionError("Object must be null");
//    }
//
//    private WorkoutForDayRepository m_WorkoutForDayRepository;
//    private String date = "2019/03/01";
//    private String date2 = "2019/03/30";
//    private Helpers helpers;
//
//    private int sleepDuration = 10;
//
//    @Before
//    public void initDb()  {
//        helpers = new Helpers();
//        m_WorkoutForDayRepository =  new WorkoutForDayRepository(InstrumentationRegistry.getContext());
////        m_WorkoutForDayRepository = Room.inMemoryDatabaseBuilder(
////                InstrumentationRegistry.getContext(),
////                AppDatabase.class)
////                .build();
//        m_WorkoutForDayRepository.deleteAll();
//    }
//
//    @After
//    public void closeDb() throws Exception {
//       // m_WorkoutForDayRepository.close();
//    }
//
//    @Test
//    public void test_InsertAll_OneDefaultEntity_GetAllShouldReturnProperValue()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
//        workoutDetailsEntities.add(helpers.createTestWorkoutDetailsEntity1().build());
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay);
//
//
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//        helpers.compareWorkoutDetails1(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
//    }
//
//    @Test
//    public void test_InsertAll_OneDefaultEntityWithTwoWorkoutDetails_GetAllShouldReturnProperValue()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
//        workoutDetailsEntities.add(helpers.createTestWorkoutDetailsEntity1().build());
//        workoutDetailsEntities.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay);
//
//
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//        assertThat(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
//
//        helpers.compareWorkoutDetails1(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
//        helpers.compareWorkoutDetails2(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(1));
//    }
//
//    @Test
//    public void test_GetWorkoutForGivenDate_TwoDefaultEntitiesWithDifferentDates_GetWorkoutForGivenDateShouldReturnProperValue()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2);
//
//        WorkoutsForDay workoutForDayFromDB = m_WorkoutForDayRepository.getWorkoutForGivenDate(date);
//        WorkoutsForDay workoutForDayFromDB2 = m_WorkoutForDayRepository.getWorkoutForGivenDate(date2);
//
//        assertThat(workoutForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//
//        helpers.compareWorkoutDetails1(workoutForDayFromDB.getWorkoutDetailsEntityList().get(0));
//        helpers.compareWorkoutDetails2(workoutForDayFromDB2.getWorkoutDetailsEntityList().get(0));
//    }
//
//    @Test
//    public void test_GetWorkoutsForGivenPeriod_TwoEntitiesWithinRangeOneIsAfterPeriod_GetWorkoutForGivenPeriodShouldReturnTwoEntities()
//    {
//        String dateAfterPeriod = "2019/02/01";
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//        WorkoutsForDay workoutsForDay3 = new WorkoutsForDay(dateAfterPeriod, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2, workoutsForDay3);
//
//
//        List<WorkoutsForDay> workoutForDayFromDB = m_WorkoutForDayRepository.getWorkoutsForGivenPeriod(date, date2);
//
//        assertThat(workoutForDayFromDB.size(), comparesEqualTo(2));
//
//        helpers.compareWorkoutDetails1(workoutForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
//        helpers.compareWorkoutDetails2(workoutForDayFromDB.get(1).getWorkoutDetailsEntityList().get(0));
//    }
//
//    @Test
//    public void test_GetWorkoutsForGivenPeriod_TwoEntitiesWithinRangeOneIsBeforePeriod_GetWorkoutForGivenPeriodShouldReturnTwoEntities()
//    {
//        String dateBeforePeriod = "2019/05/01";
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//        WorkoutsForDay workoutsForDay3 = new WorkoutsForDay(dateBeforePeriod, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2, workoutsForDay3);
//
//
//        List<WorkoutsForDay> workoutForDayFromDB = m_WorkoutForDayRepository.getWorkoutsForGivenPeriod(date, date2);
//
//        assertThat(workoutForDayFromDB.size(), comparesEqualTo(2));
//
//        helpers.compareWorkoutDetails1(workoutForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
//        helpers.compareWorkoutDetails2(workoutForDayFromDB.get(1).getWorkoutDetailsEntityList().get(0));
//    }
//
//    @Test
//    public void test_DeleteForGivenDate_TwoEntitiesWithDifferentDate_GetWorkoutForGivenDateShouldReturnProperEntities()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2);
//
//        WorkoutsForDay workoutForDayFromDB = m_WorkoutForDayRepository.getWorkoutForGivenDate(date);
//        WorkoutsForDay workoutForDayFromDB2 = m_WorkoutForDayRepository.getWorkoutForGivenDate(date2);
//
//        assertNotNull(workoutForDayFromDB);
//        assertNotNull(workoutForDayFromDB2);
//        assertThat(workoutForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//
//        helpers.compareWorkoutDetails1(workoutForDayFromDB.getWorkoutDetailsEntityList().get(0));
//        helpers.compareWorkoutDetails2(workoutForDayFromDB2.getWorkoutDetailsEntityList().get(0));
//
//        m_WorkoutForDayRepository.deleteForGivenDate(date);
//        workoutForDayFromDB = m_WorkoutForDayRepository.getWorkoutForGivenDate(date);
//        workoutForDayFromDB2 = m_WorkoutForDayRepository.getWorkoutForGivenDate(date2);
//        assertNull(workoutForDayFromDB);
//        assertNotNull(workoutForDayFromDB2);
//
//        assertThat(workoutForDayFromDB2.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//    }
//
//    @Test
//    public void test_Delete_TwoEntitiesWithDifferentDate_MakeSureItDeleteOnlyOneEntity()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2);
//
//
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(2));
//
//        m_WorkoutForDayRepository.delete(workoutsForDayFromDB.get(0));
//
//        workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//    }
//
//    @Test
//    public void test_DeleteAll_TwoEntitiesWithDifferentDate_MakeSureItDeleteAll()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date2, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay, workoutsForDay2);
//
//
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(2));
//        m_WorkoutForDayRepository.deleteAll();
//
//        workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(0));
//    }
//
//    @Test
//    public void test_Insert_TwoEntitiesWithTheSameDate_ShouldReturnOneEntity()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        List<WorkoutDetailsEntity> workoutDetailsEntities2 = new ArrayList<>();
//        workoutDetailsEntities2.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        WorkoutsForDay workoutsForDay2 = new WorkoutsForDay(date, workoutDetailsEntities2);
//
//        m_WorkoutForDayRepository.insertAll(workoutsForDay);
//        m_WorkoutForDayRepository.insertAll(workoutsForDay2);
//
//
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//        helpers.compareWorkoutDetails2(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0));
//
//
//    }
//
//    @Test
//    public void test_Update_InsertEntityFirstChangeItsValueAndThenCallUpdate_GetAllShouldReturnChangedEntity()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
//        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
//        m_WorkoutForDayRepository.insertAll(workoutsForDay);
//
//
//        String newBodyPartName = "newBodyPart";
//        workoutsForDay.getWorkoutDetailsEntityList().get(0).setBodyPart(newBodyPartName);
//
//        m_WorkoutForDayRepository.update(workoutsForDay);
//        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
//        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
//        assertThat(newBodyPartName, comparesEqualTo( workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().get(0).getBodyPart()));
//    }
//
//    @Test
//    public void test_Update_InsertEntityWithTwoItemsRemoveOneItemAndUpdate_ShouldReturnEntityWithOneItem()
//    {
//        List<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
//        workoutDetailsEntities.add(helpers.createTestWorkoutDetailsEntity1().build());
//        workoutDetailsEntities.add(helpers.createTestWorkoutDetailsEntity2().build());
//
//        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities);
//        m_WorkoutForDayRepository.insertAll(workoutsForDay);
//
//        WorkoutsForDay workoutsForDayFromDB = m_WorkoutForDayRepository.getWorkoutForGivenDate(date);
//        assertThat(workoutsForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
//
//        workoutsForDayFromDB.getWorkoutDetailsEntityList().remove(0);
//        m_WorkoutForDayRepository.update(workoutsForDayFromDB);
//
//
//        workoutsForDayFromDB = m_WorkoutForDayRepository.getWorkoutForGivenDate(date);
//        assertThat(workoutsForDayFromDB.getWorkoutDetailsEntityList().size(), comparesEqualTo(1));
//    }
//
////    @Test
////    public void test_UpdateWorkoutDetailsEntityWith_ShouldReturnOneEntity()
////    {
////        List<WorkoutDetailsEntity> workoutDetailsEntities1 = new ArrayList<>();
////        workoutDetailsEntities1.add(helpers.createTestWorkoutDetailsEntity1().build());
////
////        WorkoutsForDay workoutsForDay = new WorkoutsForDay(date, workoutDetailsEntities1);
////        m_WorkoutForDayRepository.insertAll(workoutsForDay);
////
////
////        workoutsForDay.getWorkoutDetailsEntityList().add(helpers.createTestWorkoutDetailsEntity2().build());
////
////        m_WorkoutForDayRepository.update(workoutsForDay);
////        List<WorkoutsForDay> workoutsForDayFromDB = m_WorkoutForDayRepository.getAll();
////        assertThat(workoutsForDayFromDB.size(), comparesEqualTo(1));
////        assertThat(workoutsForDayFromDB.get(0).getWorkoutDetailsEntityList().size(), comparesEqualTo(2));
////    }
//}
