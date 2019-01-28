package pibesprojects.workouttracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SimpleDateFormat sdf;

    public String getCurrentDate() {
        return m_currentDate;
    }

    private String m_currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        m_currentDate = sdf.format(new Date());
        setTitle(m_currentDate);
    }

    ImageButton getGoToPreviousDayButton()
    {
        return findViewById(R.id.goToPreviousDayButton);
    }

    ImageButton getGoToNextDayButton()
    {
        return findViewById(R.id.goToNextDayButton);
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

}
