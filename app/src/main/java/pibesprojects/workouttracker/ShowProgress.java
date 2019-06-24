package pibesprojects.workouttracker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static pibesprojects.workouttracker.CommonData.EXTRA_MESSAGE_WORKOUT_NAME;
import static pibesprojects.workouttracker.PreShowProgress.ONLY_AVAILABLE_WORKOUTS;

public class ShowProgress extends Activity implements SeekBar.OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener, View.OnClickListener{
    public static final String SHOW_PROGRESS_DATA = "dataForShowProgressClass";
    public static final String ADD_NEW_CHART = "addNewChart";
    public static final String ADD_NEW_CHART_WORKOUT_NAME = "addNewChartWorkoutName";

    private Context m_Context;
    private String m_CurrentWorkoutName;
    private LineData m_LineData;
    private ArrayList<LineDataSet> m_LineDataSet;
    private SimpleDateFormat m_Sdf;
    private LineChart mChart;
    private int m_CurrentColor = 0;
    private int[] m_Colors;
    private float m_Max = 0;
    public WorkoutsForDayRepository m_WorkoutsForDayRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_Context = this;
        m_LineDataSet = new ArrayList<>();
        m_LineData = new LineData();
        m_Sdf = new SimpleDateFormat("yyyy/MM/dd");
        m_WorkoutsForDayRepository = new WorkoutsForDayRepository(this);
        m_Colors = m_Context.getResources().getIntArray(R.array.rainbow);
        ArrayList<WorkoutDetailsEntity> workoutDetailEntities = getIntent().getParcelableArrayListExtra(SHOW_PROGRESS_DATA);
        String workoutName = getIntent().getStringExtra(EXTRA_MESSAGE_WORKOUT_NAME);
        m_CurrentWorkoutName = workoutName;
        setUpChart();
        generateCharts(workoutDetailEntities,workoutName);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.changeChart)
        {
            finish();
        }
        else if (view.getId() == R.id.addChart)
        {
            String m_CurrentDate = m_Sdf.format(new Date());

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date nextDate = cal.getTime();
            String strDt = m_Sdf.format(nextDate);
            List<WorkoutsForDay> workoutFromMonth2 =  m_WorkoutsForDayRepository.getWorkoutsForGivenPeriod(strDt, m_CurrentDate);
            ArrayList<WorkoutsForDay> workoutFromMonthAL2 = new ArrayList<>(workoutFromMonth2);
            Intent intent = new Intent(m_Context, PreShowProgress.class);
            intent.putExtra(ADD_NEW_CHART, true);
            intent.putExtra(ONLY_AVAILABLE_WORKOUTS, 1);
            intent.putParcelableArrayListExtra(SHOW_PROGRESS_DATA, workoutFromMonthAL2);

            startActivityForResult(intent, 2);
        }
        else if (view.getId() == R.id.changePeriod)
        {
            //Intent intent = new Intent(m_Context, ChangePeriodActivity.class);
            //startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data_)
    {
        Integer weeks = data_.getIntExtra("weeks", 4);
        String m_CurrentDate = m_Sdf.format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -weeks);
        Date nextDate = cal.getTime();
        String strDt = m_Sdf.format(nextDate);
        List<WorkoutsForDay> workoutFromMonth2 =  m_WorkoutsForDayRepository.getWorkoutsForGivenPeriod(strDt, m_CurrentDate);
        ArrayList<WorkoutDetailsEntity> workoutDetailsEntities = new ArrayList<>();
        for(WorkoutsForDay workoutsForDay : workoutFromMonth2)
        {
            workoutDetailsEntities.addAll(workoutsForDay.getWorkoutDetailsEntityList());
        }

        m_LineData = new LineData();
        mChart.invalidate();
        mChart.clear();
        setUpChart();

        if(requestCode == 1)
        {
            generateCharts(workoutDetailsEntities, m_CurrentWorkoutName);
        }
        else if(requestCode == 2)
        {
            String workoutName= data_.getStringExtra(ADD_NEW_CHART_WORKOUT_NAME);
            generateCharts(workoutDetailsEntities, workoutName);
            // setData(values, workoutName);
        }
    }

    private class WorkoutDetailsComparator implements Comparator<WorkoutDetailsEntity> {
        public int compare(WorkoutDetailsEntity left, WorkoutDetailsEntity right) {
            return left.getDate().compareTo(right.getDate());
        }
    }

    private ArrayList<WorkoutDetailsEntity> getWorkoutDetailsListForGivenWorkout(String workoutName, ArrayList<WorkoutDetailsEntity> workoutDetailEntities)
    {
        ArrayList<WorkoutDetailsEntity> list = new ArrayList<>();
        for(WorkoutDetailsEntity workout : workoutDetailEntities)
        {
            for(WorkoutDetailsEntity workout_ : workoutDetailEntities)
            if(workout_.getWorkoutName().equals(workoutName))
            {
                list.add(workout);
            }
        }
        return list;
    }
    private void setUpChart()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

        mChart = findViewById(R.id.chart1);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(true);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        // set an alternative background color
        // mChart.setBackgroundColor(Color.BLUE);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//            mv.setChartView(mChart); // For bounds control
//            mChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                //long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date((long) value));
            }
        });
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(500f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
    }
    private void generateCharts(ArrayList<WorkoutDetailsEntity> workoutDetailEntities, String workoutName)
    {
        Collections.sort(workoutDetailEntities, new WorkoutDetailsComparator());
        if (workoutDetailEntities.size() == 0) {
            return;
        }

        ArrayList<WorkoutDetailsEntity> details = getWorkoutDetailsListForGivenWorkout(workoutName, workoutDetailEntities);
        if(details.size() == 0)
        {
            Toast.makeText(this,
                    "THere is no data for: " + workoutName,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Entry> values = new ArrayList<Entry>();
        float max = 0;
        for (int i = 0; i < details.size(); ++i) {
            float sum = 0;
//            for (Double j : details.get(i).get()) {
//                sum += j;
//            }
            if (sum > max) max = sum;
            Date date = new Date();
            try {
                date = m_Sdf.parse(details.get(i).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.add(new Entry(date.getTime(), sum, getResources().getDrawable(R.drawable.fade_red)));
        }

        YAxis leftAxis = mChart.getAxisLeft();
        if(max > m_Max) m_Max = max;
        leftAxis.setAxisMaximum(m_Max + m_Max / 10);
        setData(values, workoutName);

        for(LineDataSet lds : m_LineDataSet) {
            m_LineData.addDataSet(lds);
        }
        mChart.setData(m_LineData);

        mChart.invalidate();

        mChart.animateX(1000);
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // dont forget to refresh the drawing
        mChart.invalidate();

//
////                mChart.setAutoScaleMinMaxEnabled(true);
//        mChart.notifyDataSetChanged();
//
//        List<ILineDataSet> sets = mChart.getData()
//                .getDataSets();
//
//        for (ILineDataSet iSet : sets) {
//
//            LineDataSet set = (LineDataSet) iSet;
//            set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
//                    ? LineDataSet.Mode.LINEAR
//                    :  LineDataSet.Mode.CUBIC_BEZIER);
//        }
//        mChart.invalidate();
//        List<ILineDataSet> sets = mChart.getData()
//                .getDataSets();
//
//        for (ILineDataSet iSet : sets) {
//
//            LineDataSet set = (LineDataSet) iSet;
//            set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
//                    ? LineDataSet.Mode.LINEAR
//                    :  LineDataSet.Mode.HORIZONTAL_BEZIER);
//        }
//        mChart.invalidate();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_show_progress, menu);
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(ArrayList<Entry> values, String name) {

//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set1.setValues(values);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, name);

        set1.setDrawIcons(true);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);

        set1.setColor(m_Colors[m_CurrentColor]);
        ++m_CurrentColor;

        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

//            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//            dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        m_LineDataSet.add(set1);
        // set data

        //}

    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.actionToggleValues: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//                }
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleIcons: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setDrawIcons(!set.isDrawIconsEnabled());
//                }
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHighlight: {
//                if(mChart.getData() != null) {
//                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
//                    mChart.invalidate();
//                }
//                break;
//            }
//            case R.id.actionToggleFilled: {
//
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawFilledEnabled())
//                        set.setDrawFilled(false);
//                    else
//                        set.setDrawFilled(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCircles: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawCirclesEnabled())
//                        set.setDrawCircles(false);
//                    else
//                        set.setDrawCircles(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCubic: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.CUBIC_BEZIER);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleStepped: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.STEPPED);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHorizontalCubic: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.HORIZONTAL_BEZIER);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionTogglePinch: {
//                if (mChart.isPinchZoomEnabled())
//                    mChart.setPinchZoom(false);
//                else
//                    mChart.setPinchZoom(true);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleAutoScaleMinMax: {
//                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
//                mChart.notifyDataSetChanged();
//                break;
//            }
//            case R.id.animateX: {
//                mChart.animateX(3000);
//                break;
//            }
//            case R.id.animateY: {
//                mChart.animateY(3000, Easing.EaseInCubic);
//                break;
//            }
//            case R.id.animateXY: {
//                mChart.animateXY(3000, 3000);
//                break;
//            }
//            case R.id.actionSave: {
//                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
//                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
//                            Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
//                            .show();
//
//                // mChart.saveToGallery("title"+System.currentTimeMillis())
//                break;
//            }
//        }
        return true;
    }

}





