
package pibesprojects.workouttracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by aaa on 2018-03-27.
 */

public abstract class IChoose extends Activity implements View.OnClickListener, View.OnLongClickListener {
    private String currentWorkout;
    private String bodyPart;
    Boolean shortClicked = false;
    private AlertDialog alertDialog;
    public LayoutInflater li;
    public AlertDialog.Builder alertDialogBuilder;
    public EditText userInput;
    public LinearLayout m_LinearLayout;
    public WorkoutNamesRepository m_WorkoutNamesRepository;

    public void relativeLayoutClicked(View view) {
        finish();
    }

    protected void createAndAddButton(String text) {
        LinearLayout linear = findViewById(R.id.linearLayout_);
        Button btn = new Button(this);
        btn.setText(text);
        btn.setOnClickListener(this);
        btn.setOnLongClickListener(this);
        linear.addView(btn);
    }

    protected void initializeDefaultButtons(LinearLayout linearLayout) {
        for(int i =0; i< linearLayout.getChildCount(); ++i)
        {
            Button button = (Button)linearLayout.getChildAt(i);
            button.setOnLongClickListener(this);
        }
    }

    //
//    abstract public void changeWorkoutName(final LinearLayout layout, View view, String nameBeforeChange);
//
    abstract public void addLayoutsContentToDataBase();
//
    @Override
    public void onClick(View view) {
        Log.v("TAG", "buttonClicked " + view.getId());
        shortClicked = true;
//        Intent intent = new Intent(this, ChooseRepsAndSets.class);
//        int requestCode = 1; // Or some number you choose
//        Button b = (Button) view;
//        currentWorkout = b.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, currentWorkout);
//        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onLongClick(final View view)
    {
        Button btn = (Button) view;
        String previousName = btn.getText().toString();

        Log.v("Debug", "userInput.getText() " + btn.getText());
        li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_new_body_part_popup_window, null);
        userInput = promptsView.findViewById(R.id.editTextDialogUserInput);
        userInput.setText(btn.getText());
        String newName = btn.getText().toString();
        alertDialog = buildAlertDialog(promptsView, view, userInput, true);
        alertDialog.show();
        //finish();
        return false;
    }

    public AlertDialog getLastDialog()
    {
        return alertDialog;
    }
    AlertDialog buildAlertDialog(View promptsView, final  View mainView, final EditText newWorkoutName, final boolean doChangeWorkoutName)
    {
        final AlertDialogHandler alertDialogHandler = new AlertDialogHandler();
         alertDialogBuilder = new AlertDialog.Builder(this);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        DialogInterface.OnClickListener handleCancelButton = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };

        DialogInterface.OnClickListener handleChangeButton = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogHandler.handlePositiveButtonClicked(mainView, newWorkoutName, doChangeWorkoutName);
            }
        };


        // set dialog message
        String positiveButtonText;
        if(doChangeWorkoutName)
        {
            positiveButtonText = "Change";
        }else
        {
            positiveButtonText = "Add";

        }
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(positiveButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                alertDialogHandler.handlePositiveButtonClicked(mainView, newWorkoutName, doChangeWorkoutName);
                            }
                        })
                .setNegativeButton("Cancel",handleCancelButton);
        if(doChangeWorkoutName)
        {
            alertDialogBuilder.setNeutralButton("Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id)
                        {
                            alertDialogHandler.handleDeleteButtonClicked(mainView);
                        }
                    });
        }


        // create alert dialog
        return alertDialogBuilder.create();
    }




    public boolean isButtonTextValid(String buttonText)
    {
        LinearLayout layout = findViewById(R.id.linearLayout_);
        for (int i = 0; i < layout.getChildCount(); ++i) {
            Button button = (Button) layout.getChildAt(i);
            if (button.getText().toString().toLowerCase().trim().equals(buttonText.toLowerCase().trim())) {
                return false;
            }
        }
        return true;
    }

    public void changeButtonText(String buttonText, View view)
    {
        Button button = (Button) (view);
        button.setText(buttonText);
    }

    public void addButton(String buttonText) {
        LinearLayout layout = findViewById(R.id.linearLayout_);
        Button button = new Button(this);

        //TODO is it necessary
        button.setOnClickListener(this);
        button.setOnLongClickListener(this);

        button.setText(buttonText);
        layout.addView(button);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void FABClicked(View view)
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_new_body_part_popup_window, null);

        final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

        alertDialog = buildAlertDialog(promptsView, view, userInput, false);
        alertDialog.show();
        //finish();
    }

    public class AlertDialogHandler
    {
        boolean handlePositiveButtonClicked(final View view, EditText userInput, boolean doChangeButtonName)
        {
            Log.v("Debug", "handlePositiveButtonClicked with text: " + userInput);
            if(isButtonTextValid(userInput.getText().toString()))
            {
                if(doChangeButtonName)
                {
                    changeButtonText(userInput.getText().toString(), view);
                }
                else
                {
                    addButton(userInput.getText().toString());
                }
                Log.v("Debug", " button name changed " + userInput);
                addLayoutsContentToDataBase();

//            String nameBeforeChange = (String)((Button) (view)).getText().toString();
                //LinearLayout layout = findViewById(R.id.linearLayout_);
                //changeWorkoutName(layout, view, nameBeforeChange);
                return true;
            }else
            {
                Log.v("Debug", "handlePositiveButtonClicked name already exist " +
                        userInput.getText());
                Toast.makeText(getApplicationContext(),
                        "name already exist " + userInput.getText(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        void handleDeleteButtonClicked(final View view)
        {
            LayoutInflater li = LayoutInflater.from(view.getContext());
            View promptsView = li.inflate(R.layout.delete_workout_alert, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    view.getContext());

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ViewGroup parent = (ViewGroup)view.getParent();
                    parent.removeView(view);
                    //LinearLayout layout = findViewById(R.id.linearLayout_);
                    addLayoutsContentToDataBase();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
};