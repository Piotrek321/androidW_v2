package pibesprojects.workouttracker;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutsForDayRepository {
    private WorkoutsForDayDao m_WorkoutsForDayDao;

    public WorkoutsForDayRepository(Context context) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        m_WorkoutsForDayDao = database.workoutForDayDao();
    }

    public List<WorkoutsForDay> getAll() {
        try {
            return  new GetAllWorkoutsAsyncTask(m_WorkoutsForDayDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            return new GetWorkoutsForGivenDateAsyncTask(m_WorkoutsForDayDao).execute(date).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dateLast)
    {
        try {
            return new GetWorkoutsForGivenPeriodAsyncTask(m_WorkoutsForDayDao).execute(dateFirst,dateLast ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;    }
//
//    public LiveData<List<WorkoutsForDay>> getAllNotes() {
//        return allNotes;
//    }
private static class GetWorkoutsForGivenPeriodAsyncTask extends AsyncTask<String, Void, List<WorkoutsForDay>> {
    private WorkoutsForDayDao workoutForDayDao;


    private GetWorkoutsForGivenPeriodAsyncTask(WorkoutsForDayDao workoutForDayDao) {
        this.workoutForDayDao = workoutForDayDao;
    }
    @Override
    protected  List<WorkoutsForDay> doInBackground(String ...date) {
        return workoutForDayDao.getWorkoutsForGivenPeriod(date[0], date[1]);
    }
}
    private static class GetWorkoutsForGivenDateAsyncTask extends AsyncTask<String, Void, WorkoutsForDay> {
        private WorkoutsForDayDao workoutForDayDao;


        private GetWorkoutsForGivenDateAsyncTask(WorkoutsForDayDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }
        @Override
        protected  WorkoutsForDay doInBackground(String ...date) {
            return workoutForDayDao.getWorkoutForGivenDate(date[0]);
        }
    }
private static class GetAllWorkoutsAsyncTask extends AsyncTask<Void, Void, List<WorkoutsForDay>> {
    private WorkoutsForDayDao workoutForDayDao;


    private GetAllWorkoutsAsyncTask(WorkoutsForDayDao workoutForDayDao) {
        this.workoutForDayDao = workoutForDayDao;
    }
    @Override
    protected  List<WorkoutsForDay> doInBackground(Void ...params) {
        return workoutForDayDao.getAll();
    }
}
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
