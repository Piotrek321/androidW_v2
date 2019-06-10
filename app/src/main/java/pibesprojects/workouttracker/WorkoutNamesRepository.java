package pibesprojects.workouttracker;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutNamesRepository {
    private WorkoutNamesDao m_WorkoutNamesDao;

    public WorkoutNamesRepository(Context context) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        m_WorkoutNamesDao = database.workoutNamesDao();
    }

    public WorkoutNames getWorkoutName(String bodyPart) {
        try {
            return  new GetWorkoutNamesAsyncTask(m_WorkoutNamesDao).execute(bodyPart).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WorkoutNames getWorkoutsNameForGivenBodyPart(String bodyPart) {
        try {
            return  new GetWorkoutsNameForGivenBodyPartAsyncTask(m_WorkoutNamesDao).execute(bodyPart).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WorkoutNames> getAll() {
        try {
            return  new GetAllWorkoutNamesAsyncTask(m_WorkoutNamesDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllBodyParts(){
        try {
            return  new GetAllBodyPartsWorkoutNamesAsyncTask(m_WorkoutNamesDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertAll(WorkoutNames... workoutNames) {
        new InsertNoteAsyncTask(m_WorkoutNamesDao).execute(workoutNames);
    }

    public void update(WorkoutNames... workoutNames) {
        new UpdateNoteAsyncTask(m_WorkoutNamesDao).execute(workoutNames);
    }

//
//    public void update(WorkoutsForDay workoutsForDay) {
//        new UpdateNoteAsyncTask(m_WorkoutsForDayDao).execute(workoutsForDay);
//    }
//
//    public void delete(WorkoutsForDay workoutsForDay) {
//        new DeleteNoteAsyncTask(m_WorkoutsForDayDao).execute(workoutsForDay);
//    }
//
    public void deleteAll() {
        new DeleteAllNotesAsyncTask(m_WorkoutNamesDao).execute();
    }
//
//    public void deleteForGivenDate(String date) {
//        new deleteForGivenDate(m_WorkoutsForDayDao).execute(date);
//    }
//
//    public WorkoutsForDay getWorkoutForGivenDate(String date){
//        try {
//            return new getWorkoutsForGivenPeriodAsyncTask(m_WorkoutsForDayDao).execute(date).get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dateLast)
//    {
//
//        return m_WorkoutsForDayDao.getWorkoutsForGivenPeriod(dateFirst, dateLast);
//    }
    //
//    public LiveData<List<WorkoutsForDay>> getAllNotes() {
//        return allNotes;
//    }
private static class UpdateNoteAsyncTask extends AsyncTask<WorkoutNames, Void, Void> {
    private WorkoutNamesDao workoutNamesDao;

    private UpdateNoteAsyncTask(WorkoutNamesDao workoutNamesDao) {
        this.workoutNamesDao = workoutNamesDao;
    }

    @Override
    protected Void doInBackground(WorkoutNames... workoutNames) {
        workoutNamesDao.update(workoutNames);
        return null;
    }
}


    private static class GetWorkoutsNameForGivenBodyPartAsyncTask extends AsyncTask<String, Void, WorkoutNames> {
        private WorkoutNamesDao workoutNamesDao;


        private GetWorkoutsNameForGivenBodyPartAsyncTask(WorkoutNamesDao workoutForDayDao) {
            this.workoutNamesDao = workoutForDayDao;
        }
        @Override
        protected  WorkoutNames doInBackground(String ...bodyPart) {
            return workoutNamesDao.getWorkoutsNameEnitiy(bodyPart[0]);
        }
    }

    private static class GetWorkoutNamesAsyncTask extends AsyncTask<String, Void, WorkoutNames> {
        private WorkoutNamesDao workoutNamesDao;


        private GetWorkoutNamesAsyncTask(WorkoutNamesDao workoutForDayDao) {
            this.workoutNamesDao = workoutForDayDao;
        }
        @Override
        protected  WorkoutNames doInBackground(String ...bodyPart) {
            return workoutNamesDao.getWorkoutsNameEnitiy(bodyPart[0]);
        }
    }

    private static class GetAllWorkoutNamesAsyncTask extends AsyncTask<Void, Void, List<WorkoutNames>> {
        private WorkoutNamesDao workoutNamesDao;


        private GetAllWorkoutNamesAsyncTask(WorkoutNamesDao workoutForDayDao) {
            this.workoutNamesDao = workoutForDayDao;
        }
        @Override
        protected  List<WorkoutNames> doInBackground(Void ...params) {
            return workoutNamesDao.getAll();
        }
    }

private static class GetAllBodyPartsWorkoutNamesAsyncTask extends AsyncTask<Void, Void, List<String>> {
    private WorkoutNamesDao workoutNamesDao;


    private GetAllBodyPartsWorkoutNamesAsyncTask(WorkoutNamesDao workoutForDayDao) {
        this.workoutNamesDao = workoutForDayDao;
    }
    @Override
    protected  List<String> doInBackground(Void ...params) {
        return workoutNamesDao.getAllBodyParts();
    }
}


    private static class InsertNoteAsyncTask extends AsyncTask<WorkoutNames, Void, Void> {
        private WorkoutNamesDao workoutNamesDao;

        private InsertNoteAsyncTask(WorkoutNamesDao workoutNamesDao) {
            this.workoutNamesDao = workoutNamesDao;
        }

        @Override
        protected Void doInBackground(WorkoutNames... workoutNames) {
            workoutNamesDao.insertAll(workoutNames);
            return null;
        }
    }
//
//    private static class UpdateNoteAsyncTask extends AsyncTask<WorkoutsForDay, Void, Void> {
//        private WorkoutsForDayDao workoutForDayDao;
//
//        private UpdateNoteAsyncTask(WorkoutsForDayDao workoutForDayDao) {
//            this.workoutForDayDao = workoutForDayDao;
//        }
//
//        @Override
//        protected Void doInBackground(WorkoutsForDay... workoutsForDays) {
//            workoutForDayDao.update(workoutsForDays);
//            return null;
//        }
//    }
//
//    private static class DeleteNoteAsyncTask extends AsyncTask<WorkoutsForDay, Void, Void> {
//        private WorkoutsForDayDao workoutForDayDao;
//
//        private DeleteNoteAsyncTask(WorkoutsForDayDao workoutForDayDao) {
//            this.workoutForDayDao = workoutForDayDao;
//        }
//
//        @Override
//        protected Void doInBackground(WorkoutsForDay... workoutForDays) {
//            workoutForDayDao.delete(workoutForDays[0]);
//            return null;
//        }
//    }
//
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private WorkoutNamesDao workoutForDayDao;

        private DeleteAllNotesAsyncTask(WorkoutNamesDao workoutForDayDao) {
            this.workoutForDayDao = workoutForDayDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workoutForDayDao.deleteAll();
            return null;
        }
    }
//
//    private static class deleteForGivenDate extends AsyncTask<String, Void, Void> {
//        private WorkoutsForDayDao workoutForDayDao;
//
//        private deleteForGivenDate(WorkoutsForDayDao workoutForDayDao) {
//            this.workoutForDayDao = workoutForDayDao;
//        }
//
//        @Override
//        protected Void doInBackground(String... date) {
//            workoutForDayDao.deleteForGivenDate(date[0]);
//            return null;
//        }
//    }

}



//    @Query("SELECT * FROM WorkoutsForDay WHERE date=:date")
//    WorkoutsForDay getWorkoutForGivenDate(String date);
//
//    @Query("SELECT * FROM WorkoutsForDay WHERE date BETWEEN :dateFirst AND :dateLast")
//    List<WorkoutsForDay> getWorkoutsForGivenPeriod(String dateFirst, String dateLast);
