package pibesprojects.workouttracker;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class WorkoutDetailsEntity implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int uid;
    private Integer sets;
    private ArrayList<Integer> repetitions;
    private ArrayList<Double> weights;
    private String workoutName;
    private String bodyPart;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    @Ignore
    public WorkoutDetailsEntity(){}

    public WorkoutDetailsEntity(Integer sets, ArrayList<Integer> repetitions, ArrayList<Double> weights,
                                String workoutName, String bodyPart){
        this.sets = sets;
        this.repetitions = repetitions;
        this.weights = weights;
        this.workoutName = workoutName;
        this.bodyPart = bodyPart;
    }

    public static final Parcelable.Creator<WorkoutDetailsEntity> CREATOR = new Parcelable.Creator<WorkoutDetailsEntity>()
    {
        public WorkoutDetailsEntity createFromParcel(Parcel pc) {
            return new WorkoutDetailsEntity(pc);
        }
        public WorkoutDetailsEntity[] newArray(int size) {
            return new WorkoutDetailsEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeInt(sets);
        pc.writeList(repetitions);
        pc.writeList(weights);
        pc.writeString(workoutName);
        pc.writeString(bodyPart);
        pc.writeString(date);
    }

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public WorkoutDetailsEntity(Parcel pc){
        sets = pc.readInt();
        repetitions = pc.readArrayList(Integer.class.getClassLoader());
        weights = pc.readArrayList(Double.class.getClassLoader());
        workoutName = pc.readString();
        bodyPart = pc.readString();
        if(sets != repetitions.size() || sets != weights.size())
        {
            String message = Thread.currentThread().getStackTrace().toString();
            message += " number of sets must be equal to repetitions size and weights size";
            throw new RuntimeException(message);
        }
        date = pc.readString();
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public ArrayList getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(ArrayList<Integer> repetitions) {
        this.repetitions = repetitions;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public String getBodyPart() { return bodyPart; }

    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public <Type> String arrayToString(ArrayList<Type> array)
    {
        //It must be done like this because toString on Array returns [1, 2, 3] and I need to remove [ and ]
        String toString = array.toString().substring(1, array.toString().length() - 1);
        return toString.replace(",", "");
    }

    public String getRepetitionsAsString()
    {

        return arrayToString(repetitions);
    }

    public String getWeightAsString()
    {
        return arrayToString(weights);
    }
    static String separator = " ";


        public static String getEmpty
                (int times) {
            String tmp = "";
            for (int i = 0; i <times; i++) {
                tmp += separator;
            }
            return tmp;
        }

        public static String adjustString(String original, String toAdjust)
        {
            if(original.length() > toAdjust.length())
            {
                return adjustString2(original, toAdjust);
            }
            return  adjustString2(toAdjust, original);

        }
        public static String adjustString2(String original, String toAdjust)
        {
            String[] parts = toAdjust.split(" ");
            int index = 0;

            StringBuilder builder = new StringBuilder(getEmpty(original.length()));
            int indexFirstBegin = 0, indexSecondBegin =0;
            int indexFirstEnd = toAdjust.indexOf(separator), indexSecondEnd = original.indexOf(separator);
            System.out.println("indexFirstBegin: " + indexFirstBegin);
            System.out.println("indexFirstEnd: "  + indexFirstEnd);
            System.out.println("indexSecondBegin: " + indexSecondBegin);
            System.out.println("indexSecondEnd: " + indexSecondEnd);
            int sizeFirst;
            int sizeSecond;

            while(true)
            {
                sizeFirst = indexFirstEnd - indexFirstBegin ;
                sizeSecond = indexSecondEnd - indexSecondBegin ;
                System.out.println("sizeFirst: " + sizeFirst);
                System.out.println("sizeSecond: " + sizeSecond);
                int divider = (sizeSecond - sizeFirst)/2;
                System.out.println("divider: " + divider);

                int padding = divider ;///2;
                System.out.println("padding: " + padding);
                int offset = indexSecondBegin + padding + (index *2);
                if(index >= parts.length || offset >  builder.length())
                {
                    return builder.toString();
                }
                builder.insert(offset, parts[index]);
                System.out.println("builder:" + builder);
                System.out.println("second :" + original);
               // ++index;
                if(indexFirstEnd == toAdjust.length() || indexFirstEnd == toAdjust.length())
                {
                    return builder.toString();//.substring(0, original.length());
                }
                ++index;
                indexFirstBegin = indexFirstEnd + 1;
                indexSecondBegin = indexSecondEnd + 1;
                indexFirstEnd = toAdjust.indexOf(separator, indexFirstBegin);
                if(indexFirstEnd == -1)
                {
                    indexFirstEnd = toAdjust.length();
                }
                indexSecondEnd = original.indexOf(separator, indexSecondBegin);
                if(indexSecondEnd == -1)
                {
                    indexSecondEnd = original.length();
                }
                System.out.println("indexFirstBegin: " + indexFirstBegin);
                System.out.println("indexFirstEnd: "  + indexFirstEnd);
                System.out.println("indexSecondBegin: " + indexSecondBegin);
                System.out.println("indexSecondEnd: " + indexSecondEnd);
            }
        }


    WorkoutDataLayout convertToWorkoutDataLayout(Context context) {
        WorkoutDataLayout workoutDataLayout = new WorkoutDataLayout(context, null);
        String weights = getWeightAsString();
        String repetitions = getRepetitionsAsString();
        if(weights.length() > repetitions.length())
        {
            repetitions = adjustString2(weights, repetitions);
            StringBuilder builder = new StringBuilder(repetitions);
            builder.insert(0, "    ");
            weights = weights.replace(" ", "  ");
            repetitions = builder.toString();//.substring(0, repetitions.length());
        }
        else
        {
            weights = adjustString2(weights, repetitions);
            StringBuilder builder = new StringBuilder(weights);
            builder.insert(0, "   ");
            weights = builder.toString();//.substring(0, weights.length());
            repetitions.replace(" ", "  ");

        }

        System.out.println("repetitions: " + repetitions);
        System.out.println("weights:     " + weights);
        workoutDataLayout.createEntry(
                getWorkoutName(),
                context.getString(R.string.SetsInteger, getRepetitions().size()),
                context.getString(R.string.Reps, repetitions),
                context.getString(R.string.Weight, weights ),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher),//benchpress), //m_workoutImageMap.get(wd.getWorkoutName())),
                getBodyPart());
        workoutDataLayout.m_Date = date;
        System.out.println("123:" + workoutDataLayout.getNumberOfReps().getText().toString());
        System.out.println("123:" + workoutDataLayout.getWeight().getText().toString());
        return workoutDataLayout;
    }
}

/*
      public static void main(String []args)
        {
            System.out.println("Hello World");
            String original = "0.0 11.1 334.5";
            String first =  "12 1 111";
            String adjusted = adjustString(original, first);//.substring(0, original.length());
            System.out.println("adjustet:" + adjusted);
            System.out.println("original:" + original);


            original = "0.2220 113213.1 32234.5";
            first =  "12 1 111";
            adjusted = adjustString(original, first);//.substring(0, original.length());
            System.out.println("adjustet:" + adjusted);
            System.out.println("original:" + original);

            original = "12 1 111";
            first =  "0.2220 113213.1 32234.5";
            adjusted = adjustString(original, first);//.substring(0, original.length());
            System.out.println("adjustet:" + adjusted);
            System.out.println("original:" + first);


        }
 */