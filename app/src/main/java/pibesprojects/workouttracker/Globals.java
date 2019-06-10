package pibesprojects.workouttracker;

import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;

public class Globals {
    static public <Type extends Button> String viewToString(View view)
    {
        return ((Type)view).getText().toString();
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
