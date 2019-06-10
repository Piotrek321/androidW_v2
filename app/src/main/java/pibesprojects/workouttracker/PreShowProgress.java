package pibesprojects.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_WORKOUT_NAME;
import static pibesprojects.workouttracker.ShowProgress.ADD_NEW_CHART;
import static pibesprojects.workouttracker.ShowProgress.ADD_NEW_CHART_WORKOUT_NAME;
import static pibesprojects.workouttracker.ShowProgress.SHOW_PROGRESS_DATA;

public class PreShowProgress extends AppCompatActivity {
    public static final String ONLY_AVAILABLE_WORKOUTS = "onlyAvailableWorkouts";

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<WorkoutDetailsEntity> workoutDetailEntities;

    private void runShowProgress(String workout)
    {
        Intent intent = new Intent(this, ShowProgress.class);
        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, workout);
        intent.putParcelableArrayListExtra(SHOW_PROGRESS_DATA, workoutDetailEntities);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_before_show_progress);
        workoutDetailEntities = getIntent().getParcelableArrayListExtra(SHOW_PROGRESS_DATA);
        Integer option = getIntent().getIntExtra(ONLY_AVAILABLE_WORKOUTS, 0);
        Boolean addNewChart = getIntent().getBooleanExtra(ADD_NEW_CHART, false);
        if (addNewChart) {
            expandableListView =  findViewById(R.id.expandableListView);
            ExpandableListDataPump expandableListDataPump = new ExpandableListDataPump(this);
            expandableListDetail = expandableListDataPump.getData(option, workoutDetailEntities);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    String workout =
                            expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)).get(
                                    childPosition);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(ADD_NEW_CHART_WORKOUT_NAME, workout);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
                    return false;
                }
            });
        }else
        {
            expandableListView =  findViewById(R.id.expandableListView);
            ExpandableListDataPump expandableListDataPump = new ExpandableListDataPump(this);
            expandableListDetail = expandableListDataPump.getData(option, workoutDetailEntities);
            if(!expandableListDetail.isEmpty()) {
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        String workout =
                                expandableListDetail.get(
                                        expandableListTitle.get(groupPosition)).get(
                                        childPosition);

                        runShowProgress(workout);

                        return false;
                    }
                });
            }else
            {
                TextView txt = findViewById(R.id.noDataAvailable);
                txt.setText(R.string.NoDataAvailable);
            }
        }
    }
}