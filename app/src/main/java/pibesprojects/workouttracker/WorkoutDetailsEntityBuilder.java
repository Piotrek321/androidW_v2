package pibesprojects.workouttracker;

import java.util.ArrayList;

public class WorkoutDetailsEntityBuilder {

    private Integer sets;
    private ArrayList<Integer> repetitions;
    private ArrayList<Double> weights;
    private String workoutName;
    private String bodyPart;
    private String date;

    public WorkoutDetailsEntityBuilder setSets(Integer sets) {
        this.sets = sets;
        return this;
    }

    public WorkoutDetailsEntityBuilder setRepetitions(ArrayList<Integer> repetitions) {
        this.repetitions = repetitions;
        return this;
    }

    public WorkoutDetailsEntityBuilder setWeights(ArrayList<Double> weights) {
        this.weights = weights;
        return this;
    }

    public WorkoutDetailsEntityBuilder setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
        return this;
    }

    public WorkoutDetailsEntityBuilder setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
        return this;
    }

    public WorkoutDetailsEntityBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    WorkoutDetailsEntity build()
    {
        WorkoutDetailsEntity workoutDetailsEntity = new WorkoutDetailsEntity(sets, repetitions, weights, workoutName, bodyPart);
        workoutDetailsEntity.setDate(date);
        return workoutDetailsEntity;
    }
}
