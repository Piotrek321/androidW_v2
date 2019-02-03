package pibesprojects.workouttracker;

import android.app.Activity;

/**
 * Created by aaa on 2018-03-27.
 */

public abstract class IChoose extends Activity //implements View.OnClickListener, View.OnLongClickListener {
{
    private String currentWorkout;
    private String bodyPart;

    public static final String EXTRA_MESSAGE_WORKOUT_NAME = "WORKOUT_NAME";

//    public void relativeLayoutClicked(View view) {
//        finish();
//    }
//
//    protected void createAndAddButton(String text) {
//        LinearLayout linear = findViewById(R.id.linearLayout_);
//        Button btn = new Button(this);
//        btn.setText(text);
//        btn.setOnClickListener(this);
//        btn.setOnLongClickListener(this);
//        linear.addView(btn);
//    }
//
//    abstract public void changeWorkoutName(final LinearLayout layout, View view, String nameBeforeChange);
//
//    abstract public void addLayoutsContentToDataBase(final LinearLayout layout);
//
//    @Override
//    public void onClick(View view) {
//        Log.v("TAG", "buttonClicked " + view.getId());
//        Intent intent = new Intent(this, ChooseRepsAndSets.class);
//        int requestCode = 1; // Or some number you choose
//        Button b = (Button) view;
//        currentWorkout = b.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE_WORKOUT_NAME, currentWorkout);
//        startActivityForResult(intent, requestCode);
//    }
//
//    public boolean isButtonTextValid(String buttonText) {
//        LinearLayout layout = findViewById(R.id.linearLayout_);
//        for (int i = 0; i < layout.getChildCount(); ++i) {
//            Button button = (Button) layout.getChildAt(i);
//            if (button.getText().toString().toLowerCase().equals(buttonText.toLowerCase())) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void addButton(String buttonText) {
//        LinearLayout layout = findViewById(R.id.linearLayout_);
//        Button button = new Button(this);
//
//        //TODO is it necessary
//        button.setOnClickListener(this);
//        button.setOnLongClickListener(this);
//
//        button.setText(buttonText);
//        layout.addView(button);
//    }
//
//    public void changeButtonText(String buttonText, View view) {
//        Button button = (Button) (view);
//        button.setText(buttonText);
//        // TODO CHANGE content of database
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//
//    public void FABClicked(View view)
//    {
//        // get prompts.xml view
//        LayoutInflater li = LayoutInflater.from(this);
//        View promptsView = li.inflate(R.layout.add_new_body_part_popup_window, null);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set prompts.xml to alertdialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserInput);
//
//        // set dialog message
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("Add",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                // get user input and set it to result
//                                // edit text
//                                Log.v("Debug", "userInput.getText() " + userInput.getText());
//                                if(isButtonTextValid(userInput.getText().toString()))
//                                {
//                                    addButton(userInput.getText().toString());
//                                    LinearLayout layout = findViewById(R.id.linearLayout_);
//                                    addLayoutsContentToDataBase(layout);
//
//                                }else
//                                {
//                                    Log.v("Debug", "addButton(String buttonText) name already exist " +
//                                            userInput.getText().toString());
//                                    Toast.makeText(getApplicationContext(),
//                                            "THere is no data for: " + userInput.getText().toString(),
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();
//
//        //finish();
//    }
//
//    @Override
//    public boolean onLongClick(final View view) {
//        Button btn = (Button) view;
//
//        Log.v("Debug", "userInput.getText() " + btn.getText());
//        LayoutInflater li = LayoutInflater.from(this);
//        View promptsView = li.inflate(R.layout.add_new_body_part_popup_window, null);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set prompts.xml to alertdialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserInput);
//        userInput.setText(btn.getText());
//
//        TextView txt = promptsView.findViewById(R.id.editTextDialogUserInputText);
//        txt.setText("Change workout name: ");
//        // set dialog message
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("Change",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                // get user input and set it to result
//                                // edit text
//                                Log.v("Debug", "userInput.getText() " + userInput.getText());
//                                if(isButtonTextValid(userInput.getText().toString()))
//                                {
//                                    String nameBeforeChange = (String)((Button) (view)).getText().toString();
//
//                                    changeButtonText(userInput.getText().toString(), view);
//                                    LinearLayout layout = findViewById(R.id.linearLayout_);
//                                    changeWorkoutName(layout, view, nameBeforeChange);
//                                }else
//                                {
//                                    Log.v("Debug", "addButton(String buttonText) name already exist " +
//                                            userInput.getText().toString());
//                                    Toast.makeText(getApplicationContext(),
//                                            "THere is no data for: " + userInput.getText().toString(),
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
//                        })
//                .setNeutralButton("Delete",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id)
//                            {
//                                LayoutInflater li = LayoutInflater.from(view.getContext());
//                                View promptsView = li.inflate(R.layout.delete_workout_alert, null);
//
//                                AlertDialog.Builder alertDialogBuilder_ = new AlertDialog.Builder(
//                                        view.getContext());
//
//                                // set prompts.xml to alertdialog builder
//                                alertDialogBuilder_.setView(promptsView);
//                                alertDialogBuilder_.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ViewGroup parent = (ViewGroup)view.getParent();
//                                        parent.removeView(view);
//                                        LinearLayout layout = findViewById(R.id.linearLayout_);
//                                        addLayoutsContentToDataBase(layout);
//                                    }
//                                })
//                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        });
//                                AlertDialog alertDialog1 = alertDialogBuilder_.create();
//
//                                // show it
//                                alertDialog1.show();
//                            }
//                        });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();
//        //finish();
//        return false;
//    }

};