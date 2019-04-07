package pibesprojects.workouttracker;

import android.arch.persistence.room.TypeConverter;
import android.support.v4.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromArrayListPairIntegerAndDouble(ArrayList<Pair<Integer,Double>> list) {
        return  list.toString();
    }

    @TypeConverter
    public static ArrayList<Integer> toArrayListInteger(String value) {
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static ArrayList<Double> toArrayListDouble(String value) {
        Type listType = new TypeToken<ArrayList<Double>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListInt(ArrayList<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromArrayListDouble(ArrayList<Double> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromArrayListDouble2(List<WorkoutDetailsEntity> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<WorkoutDetailsEntity> toArrayListDouble2(String value) {
        Type listType = new TypeToken<ArrayList<WorkoutDetailsEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
