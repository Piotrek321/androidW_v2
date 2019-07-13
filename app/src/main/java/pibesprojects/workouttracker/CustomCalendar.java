package pibesprojects.workouttracker;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static pibesprojects.workouttracker.CommonData.DATE_TO_BE_COPIED_IN;
import static pibesprojects.workouttracker.CommonData.EXTRA_DATE;
import static pibesprojects.workouttracker.CommonData.IS_COPY_MODE;
import static pibesprojects.workouttracker.CommonData.dateFormat;

public class CustomCalendar extends AppCompatActivity {
    public CalendarView m_calendarView;
    private String m_currentDate;
    boolean isCopyMode;
    //TODO it is done for testing purpose, is there any way to call onSelectedDayChange from
    //tests?
    public CalendarView.OnDateChangeListener m_listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        m_calendarView = findViewById(R.id.calendarView);
        m_currentDate = sdf.format(new Date());
       // isCopyMode = getIntent().getBooleanExtra(IS_COPY_MODE, false);
//        if(getIntent().getExtras() != null)
//        {
//            m_currentDate =  getIntent().getExtras().getString(EXTRA_DATE);
//        }

        //setTitle(getIntent().getStringExtra(WINDOW_NAME));

        //isCopyMode = getIntent().getBooleanExtra(IS_COPY_MODE, false);

        long time;
        try
        {
            Date oldDate = sdf.parse(m_currentDate);
            time = oldDate.getTime();
            Log.v("Debug", "runCalendar:" + "oldDate: " + oldDate);

        } catch (ParseException e)
        {
            time = System.currentTimeMillis();
        }

        m_calendarView.setDate(time);
        m_listener = new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                parseDate(year, month, dayOfMonth);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_DATE, m_currentDate);
                returnIntent.putExtra(IS_COPY_MODE, getIntent().getBooleanExtra(IS_COPY_MODE, false));
                returnIntent.putExtra(DATE_TO_BE_COPIED_IN, getIntent().getStringExtra(DATE_TO_BE_COPIED_IN));

                //returnIntent.putExtra(IS_COPY_MODE, isCopyMode);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        };
        m_calendarView.setOnDateChangeListener(m_listener);
    }
    void parseDate(int year, int month, int dayOfMonth)
    {
        String day = Integer.toString(dayOfMonth);
        if(dayOfMonth < 10)
        {
            day = "0" + dayOfMonth;
        }
        //TODO Why +1??
        String month_ = Integer.toString(month+1);
        if(month < 9)
        {
            month_ = "0" + (month+1);
        }
        m_currentDate = year + "/" + month_ + "/" + day;
        Log.v("Debug", "CustomCalendar date: " + m_currentDate);
    }
    public String getCurrentDate()
    {
        return m_currentDate;
    }

}