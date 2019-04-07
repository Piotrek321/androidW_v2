package pibesprojects.workouttracker;

import java.util.ArrayList;

public class WorkoutDetailsEntityBuilder {

    private Integer sets;
    private ArrayList<Integer> repetitions;
    private ArrayList<Double> weights;
    private String workoutName;
    private String bodyPart;

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

    WorkoutDetailsEntity build()
    {
        return new WorkoutDetailsEntity(sets, repetitions, weights, workoutName, bodyPart);
    }
}
