package pibesprojects.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static pibesprojects.workouttracker.CustomCalendar.EXTRA_DATE;
import static pibesprojects.workouttracker.CustomCalendar.dateFormat;

public class MainActivity extends AppCompatActivity {
    private final int GET_DATE_FROM_CALENDAR= 10;

    private SimpleDateFormat sdf;
    private String m_currentDate;
    private ImageButton m_goToPreviousDayButton;
    private ImageButton m_goToNextDayButton;
    public Menu m_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        m_currentDate = sdf.format(new Date());
        setTitle(m_currentDate);

        m_goToPreviousDayButton = findViewById(R.id.goToPreviousDayButton);
        m_goToNextDayButton = findViewById(R.id.goToNextDayButton);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.v("Debug", "R.id.action_add");

                break;
            case R.id.restoreFactorySettings:
                Log.v("Debug", "R.id.restoreFactorySettings");
                break;
            case R.id.action_calendar:
                Log.v("Debug", "R.id.action_calendar");
                runCalendar();
                break;
            case R.id.action_showProgress:
                Log.v("Debug", "R.id.action_showProgress");

                break;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        m_menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    ImageButton getGoToPreviousDayButton()
    {
        return m_goToPreviousDayButton;
    }

    ImageButton getGoToNextDayButton()
    {
        return m_goToNextDayButton;
    }

    public void changeDateButtonClicked(View view) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(m_currentDate));
        ImageButton imageButton = (ImageButton)view;
        if(imageButton == getGoToNextDayButton())
        {
            c.add(Calendar.DATE, 1);  // number of days to add
        }else if(imageButton == getGoToPreviousDayButton())
        {
            c.add(Calendar.DATE, -1);  // number of days to add
        }
        m_currentDate = sdf.format(c.getTime());  // dt is now the new date
        TextView dateText = findViewById(R.id.currentDateText);
        dateText.setText(m_currentDate);
        //generateActivityViewForDate();
    }

    public String getCurrentDate()
    {
        return m_currentDate;
    }

    private void runCalendar()
    {
        Intent intent = new Intent(this, CustomCalendar.class);

        startActivityForResult(intent, GET_DATE_FROM_CALENDAR);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (data == null || data.getExtras() == null)
        {
            return;
        }
        if (requestCode == GET_DATE_FROM_CALENDAR) {
            String date = data.getStringExtra(EXTRA_DATE);
            m_currentDate = date;
            Log.v("Debug", "onActivityResult date: " + date);
            //m_tableLayout.removeAllViews();
            //generateActivityViewForDate();
        }
    }

}
