package pibesprojects.workouttracker;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.junit.Assert.assertEquals;

public class Helpers {
    public Integer sets = 2;
    public ArrayList<Integer> repetitions = new ArrayList<>(
            Arrays.asList(1,2));
    public ArrayList<Double> weights = new ArrayList<>(
            Arrays.asList(1.0,2.0));

    public String workoutName = "workoutName";
    public String bodyPart = "bodyPart";

    public Integer sets2 = 3;
    public ArrayList<Integer> repetitions2 = new ArrayList<>(
            Arrays.asList(3,4));
    public ArrayList<Double> weights2 = new ArrayList<>(
            Arrays.asList(3.0,4.0));

    public String workoutName2 = "workoutName2";
    public String bodyPart2 = "bodyPart2";

    public WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity1()
    {
        return new WorkoutDetailsEntityBuilder().setSets(sets)
                .setRepetitions(repetitions)
                .setWeights(weights)
                .setWorkoutName(workoutName)
                .setBodyPart(bodyPart);
    }

    public void compareWorkoutDetails1(WorkoutDetailsEntity workoutDetailsEntity)
    {
        assertThat(sets, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions,workoutDetailsEntity.getRepetitions());
        assertEquals(weights,workoutDetailsEntity.getWeights());
        assertThat(workoutName, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(bodyPart, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

    public WorkoutDetailsEntityBuilder createTestWorkoutDetailsEntity2()
    {
        return new WorkoutDetailsEntityBuilder().setSets(sets2)
                .setRepetitions(repetitions2)
                .setWeights(weights2)
                .setWorkoutName(workoutName2)
                .setBodyPart(bodyPart2);
    }

    public void compareWorkoutDetails2(WorkoutDetailsEntity workoutDetailsEntity, String dateToCompare)
    {
        assertThat(sets2, comparesEqualTo( workoutDetailsEntity.getSets()));
        assertEquals(repetitions2,workoutDetailsEntity.getRepetitions());
        assertEquals(weights2,workoutDetailsEntity.getWeights());
        assertThat(workoutName2, comparesEqualTo( workoutDetailsEntity.getWorkoutName()));
        assertThat(bodyPart2, comparesEqualTo( workoutDetailsEntity.getBodyPart()));
    }

}
