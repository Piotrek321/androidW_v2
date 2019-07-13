package pibesprojects.workouttracker;

import android.content.Intent;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static pibesprojects.workouttracker.CommonData.DATE_TO_BE_COPIED_IN;
import static pibesprojects.workouttracker.CommonData.GET_DATE_FROM_CALENDAR;
import static pibesprojects.workouttracker.CommonData.IS_COPY_MODE;

public class WorkoutCopier
{
    String dateToBeCopiedTo;
    String dateToBeCopiedFrom;
    ArrayList<WorkoutDataLayout> m_WorkoutDataLayouts;
    public WorkoutCopier()
    {

        dateToBeCopiedTo = "";
        dateToBeCopiedFrom = "";
        m_WorkoutDataLayouts = new ArrayList<>();
    }

    public void createCopyButton(MainActivity activity)
    {
        MenuItem copyWorkout = activity.m_menu.findItem(R.id.copyWorkout);
        copyWorkout.setVisible(true);
    }

    public void createCopyThisDayButton(MainActivity activity)
    {
        MenuItem copyThisWorkout = activity.m_menu.findItem(R.id.copyThisDay);
        copyThisWorkout.setVisible(true);
        MenuItem copyWorkout = activity.m_menu.findItem(R.id.copyWorkout);
        copyWorkout.setVisible(false);
    }

    public void handleCopyButtonClicked(MainActivity activity)
    {
        dateToBeCopiedTo = activity.getCurrentDate();
        Intent intent = new Intent(activity, CustomCalendar.class);
        intent.putExtra(IS_COPY_MODE, true);
        intent.putExtra(DATE_TO_BE_COPIED_IN, dateToBeCopiedTo);

        activity.startActivityForResult(intent, GET_DATE_FROM_CALENDAR);
    }

    public void handleCopyThisDayButtonClicked(MainActivity activity)
    {
        dateToBeCopiedFrom = activity.getCurrentDate();
       // m_WorkoutDataLayouts = activity.getAllWorkoutDataLayout();
        activity.changeDate(dateToBeCopiedTo);

        List<WorkoutDetailsEntity> workoutList = new ArrayList<>();
        for (int i = 1; i < activity.m_tableLayout.getChildCount(); ++i) {
            WorkoutDetailsEntity workoutDetailsEntity2 = ((WorkoutDataLayout) activity.m_tableLayout.getChildAt(i)).convertToWorkoutDetailsEntity();
            workoutDetailsEntity2.setDate(dateToBeCopiedTo);
            workoutList.add(workoutDetailsEntity2);
        }
        activity.removeWorkoutDataLayouts();
        activity.insertCurrentWorkoutIntoLayout();
        boolean z = false;
        List<WorkoutDetailsEntity> workoutList2 = new ArrayList<>();

        for (int i = 1; i < activity.m_tableLayout.getChildCount(); ++i) {
            WorkoutDetailsEntity workoutDetailsEntity2 = ((WorkoutDataLayout) activity.m_tableLayout.getChildAt(i)).convertToWorkoutDetailsEntity();
            workoutDetailsEntity2.setDate(dateToBeCopiedTo);
            workoutList2.add(workoutDetailsEntity2);
            z = true;
        }

        workoutList2.addAll(workoutList);

        if ( z)//activity.m_tableLayout.getChildCount() != 1) {
        {    WorkoutsForDay w = new WorkoutsForDay(activity.getCurrentDate(), workoutList2);
            activity.m_WorkoutsForDayRepository.update(w);
        } else {
            WorkoutsForDay w = new WorkoutsForDay(activity.getCurrentDate(), workoutList2);
            activity.m_WorkoutsForDayRepository.deleteForGivenDate(activity.getCurrentDate());
            activity.m_WorkoutsForDayRepository.insertAll(w);
        }

        List<WorkoutsForDay> zz =  activity.m_WorkoutsForDayRepository.getAll();

        activity.removeWorkoutDataLayouts();

        activity.insertCurrentWorkoutIntoLayout();
        MenuItem copyThisWorkout = activity.m_menu.findItem(R.id.copyThisDay);
        copyThisWorkout.setVisible(false);
        MenuItem copyWorkout = activity.m_menu.findItem(R.id.copyWorkout);
        copyWorkout.setVisible(true);
        activity.setTitle(activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString());


    }
}
