package pibesprojects.workouttracker;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class WorkoutForDayRepository {
    private WorkoutsForDayDao m_WorkoutsForDayDao;
    private List<WorkoutsForDay> allWorkoutForDays;

    public WorkoutForDayRepository(Context context) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        m_WorkoutsForDayDao = database.workoutForDayDao();
        allWorkoutForDays = m_WorkoutsForDayDao.getAll();
    }

    public List<WorkoutsForDay> getAll() {
        return m_WorkoutsForDayDao.getAll();
    }

    public void insertAll(WorkoutsForDay... workoutsForDay) {
        new InsertNoteAsyncTask(m_WorkoutsForDayDao).execute(workoutsForDay);
    }

    public void update(WorkoutsForDay workoutsForDay) {
        new UpdateNoteAsyncTask(m_WorkoutsForDayDao).execute(workoutsForDay);
    }

    public void delete(WorkoutsForDay workoutsForDay) {
        new DeleteNoteAsyncTask(m_WorkoutsForDayDao).execute(workoutsForDay);
    }

    public void deleteAll() {
        new DeleteAllNotesAsyncTask(m_WorkoutsForDayDao).execute();
    }

    public void deleteForGivenDate(String date) {
        new deleteForGivenDate(m_WorkoutsForDayDao).execute(date);
    }

    public WorkoutsForDay getWorkoutForGivenDate(String date){
        return m_WorkoutsForDayDao.getWorkoutForGivenDate(date);
    }

   
    public List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dateLast)
    {

        return m_WorkoutsForDayDao.getWorkoutsForGivenPeriod(dateFirst, dateLast);
    }
//
//    public LiveData<List<WorkoutsForDay>> getAllNotes() {
//        return allNotes;
//    }

    private static class InsertNoteAsyncTask extends AsyncTask<WorkoutsForDay, Void, Void> {
        private WorkoutsForDayDao workoutForDayDao;

        private InsertNoteAsyncTask(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(WorkoutsForDay... workoutsForDays) {
            workoutForDayDao.insertAll(workoutsForDays);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<WorkoutsForDay, Void, Void> {
        private WorkoutsForDayDao workoutForDayDao;

        private UpdateNoteAsyncTask(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(WorkoutsForDay... workoutsForDays) {
            workoutForDayDao.update(workoutsForDays);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<WorkoutsForDay, Void, Void> {
        private WorkoutsForDayDao workoutForDayDao;

        private DeleteNoteAsyncTask(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(WorkoutsForDay... workoutForDays) {
            workoutForDayDao.delete(workoutForDays[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private WorkoutsForDayDao workoutForDayDao;

        private DeleteAllNotesAsyncTask(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workoutForDayDao.deleteAll();
            return null;
        }
    }

    private static class deleteForGivenDate extends AsyncTask<String, Void, Void> {
        private WorkoutsForDayDao workoutForDayDao;

        private deleteForGivenDate(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(String... date) {
            workoutForDayDao.deleteForGivenDate(date[0]);
            return null;
        }
    }

}



//    @Query("SELECT * FROM WorkoutsForDay WHERE date=:date")
//    WorkoutsForDay getWorkoutForGivenDate(String date);
//
//    @Query("SELECT * FROM WorkoutsForDay WHERE date BETWEEN :dateFirst AND :dateLast")
//    List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dateLast);
