package pibesprojects.workouttracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

public class CustomCalendar extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE_DATE = "DATE";
   // private boolean isCopyMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView simpleCalendarView = findViewById(R.id.calendarView);
        if(getIntent().getExtras() == null) return;
        //setTitle(getIntent().getStringExtra(WINDOW_NAME));

        //isCopyMode = getIntent().getBooleanExtra(IS_COPY_MODE, false);
        //long currentDate =  getIntent().getExtras().getLong(EXTRA_MESSAGE_DATE);
        //simpleCalendarView.setDate(currentDate);

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.v("Debug", "day: " + dayOfMonth);
                String day = Integer.toString(dayOfMonth);
                if(dayOfMonth < 10)
                {
                    day = "0" + dayOfMonth;
                }
                String month_ = Integer.toString(month+1);
                if(month < 9)
                {
                    month_ = "0" + (month+1);
                }
                String date = year + "/" + month_ + "/" + day;
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra(EXTRA_MESSAGE_DATE,date);
//                returnIntent.putExtra(IS_COPY_MODE, isCopyMode);
//                setResult(Activity.RESULT_OK,returnIntent);
//                finish();
            }
        });
    }
}