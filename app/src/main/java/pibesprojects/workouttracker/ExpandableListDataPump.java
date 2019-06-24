package pibesprojects.workouttracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    Context context;
    public WorkoutNamesRepository m_WorkoutNamesRepository;

    public ExpandableListDataPump(Context ct)
    {
        this.context = ct;
        m_WorkoutNamesRepository = new WorkoutNamesRepository(this.context);
    }

    public HashMap<String, List<String>> getData(Integer option, ArrayList<WorkoutDetailsEntity> workoutDetailEntities) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        if(option == 0) {
            List<String> bodyParts = m_WorkoutNamesRepository.getAllBodyParts();
            for (int i = 0; i < bodyParts.size(); ++i) {
                String name = bodyParts.get(i);
                int id = context.getResources().getIdentifier(name, "array", "pibesprojects.workouttracker");
                List<String> workoutsForBodyPart = m_WorkoutNamesRepository.getWorkoutsNameForGivenBodyPart(name).getWorkoutNames();//context.getResources().getStringArray(id);
                List<String> workoutsForBodyPartList = new ArrayList<String>();

                for (int j = 0; j < workoutsForBodyPart.size(); ++j) {
                    workoutsForBodyPartList.add(workoutsForBodyPart.get(j));
                }
                expandableListDetail.put(name, workoutsForBodyPartList);
            }
        }
         else if(option == 1)
        {
            List<String> bodyParts = m_WorkoutNamesRepository.getAllBodyParts();
            for(String bp : bodyParts)
            {
                ArrayList<String> workouts = new ArrayList<>();
                for(WorkoutDetailsEntity wd : workoutDetailEntities)
                {
                    if(wd.getBodyPart().equals(bp))
                    {
                        //Todo change to set later
                        boolean found =false;
                        for(int i =0 ; i< workouts.size(); ++i)
                        {

                            if(workouts.get(i).equals(wd.getWorkoutName())) {
                               found = true;
                            }
                        }
                        if(!found)
                        {
                            workouts.add(wd.getWorkoutName());
                        }
                    }
                }
                if(workouts.size() != 0)
                {
                    expandableListDetail.put(bp, workouts);
                }
            }
        }
        return expandableListDetail;
    }
}