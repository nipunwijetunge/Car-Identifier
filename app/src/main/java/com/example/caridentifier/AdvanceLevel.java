package com.example.caridentifier;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdvanceLevel extends AppCompatActivity {
    /**number of milli seconds for the timer*/
    private static final long START_TIME_IN_MILLIS = 20000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    /**set remaining time to the starting time in the beginning*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    /**an integer to track the number of points*/
    private int points = 0;
    /**an integer to keep track of number of wrong attempts*/
    private int wrongAttempts = 0;
    /**an array list to store car all the images*/
    private List<Integer> carImages = new ArrayList<>();
    private ImageView car1, car2, car3;
    private EditText car1Input, car2Input, car3Input;
    private TextView counter, car1textView, car2TextView, car3TextView, pointsLbl;
    /**integers to store car id for all three car images*/
    private int car1Id, car2Id, car3Id;
    private Button submitBtn;
    /**strings to store car names of all three car images, wrong attempts counter text and points text*/
    private String car1Name, car2Name, car3Name, counterTxt, pointsTxt;

    /**a boolean to change the functionality of submit button between submitting answer and loading next image*/
    private boolean flag = false;

    /**booleans to keep track whether user entered correct name of corresponding car image*/
    private boolean car1Ans = false;
    private boolean car2Ans = false;
    private boolean car3Ans = false;

    /**a boolean to store the switch button state from the MainActivity*/
    private boolean switch_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_level);

        submitBtn = findViewById(R.id.button_advance_level);
        pointsLbl = findViewById(R.id.textView_advance_level_points_Lbl);
        car1textView = findViewById(R.id.textView_advance_level_car01);
        car2TextView = findViewById(R.id.textView_advance_level_car02);
        car3TextView = findViewById(R.id.textView_advance_level_car03);

        switch_state = getIntent().getExtras().getBoolean("switchBtn");
        mTextViewCountDown = findViewById(R.id.textView_advance_level_timer);

        /**storing car images in the carImages ArrayList*/
        carImages.add(R.drawable.audi1);
        carImages.add(R.drawable.audi2);
        carImages.add(R.drawable.audi3);
        carImages.add(R.drawable.bentley1);
        carImages.add(R.drawable.bentley2);
        carImages.add(R.drawable.bentley3);
        carImages.add(R.drawable.bmw1);
        carImages.add(R.drawable.bmw2);
        carImages.add(R.drawable.bmw3);
        carImages.add(R.drawable.bugatti1);
        carImages.add(R.drawable.chevrolet1);
        carImages.add(R.drawable.dodge1);
        carImages.add(R.drawable.dodge2);
        carImages.add(R.drawable.ferrari1);
        carImages.add(R.drawable.ferrari2);
        carImages.add(R.drawable.ferrari3);
        carImages.add(R.drawable.koenigsegg1);
        carImages.add(R.drawable.koenigsegg2);
        carImages.add(R.drawable.koenigsegg3);
        carImages.add(R.drawable.lamborghini1);
        carImages.add(R.drawable.lamborghini2);
        carImages.add(R.drawable.lamborghini3);
        carImages.add(R.drawable.maserati1);
        carImages.add(R.drawable.maserati2);
        carImages.add(R.drawable.mclaren1);
        carImages.add(R.drawable.mclaren2);
        carImages.add(R.drawable.mclaren3);
        carImages.add(R.drawable.mercedes1);
        carImages.add(R.drawable.mercedes2);
        carImages.add(R.drawable.mercedes3);
        carImages.add(R.drawable.porsche1);
        carImages.add(R.drawable.porsche2);
        carImages.add(R.drawable.tesla1);
        carImages.add(R.drawable.tesla2);
        carImages.add(R.drawable.tesla3);
        carImages.add(R.drawable.volvo1);

        /**shows a message dialog giving the brief intro to the game level*/
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AdvanceLevel.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='#388e3c'>ADVANCE LEVEL</font>"))
                .setMessage(Html.fromHtml("<font color='black'>Write down the car make in the corresponding text field.</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) AdvanceLevel.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);

        /**sets a random image as the imageView resource of first car image from the carImages ArrayList*/
        car1 = findViewById(R.id.imageView_advance_level_car01);
        car1Id = (int) (Math.random() * carImages.size());
        car1Name = getResources().getResourceEntryName(carImages.get(car1Id));
        car1.setImageResource(carImages.get(car1Id));

        /**sets a random image as the imageView resource of second car image from the carImages ArrayList*/
        car2 = findViewById(R.id.imageView_advance_level_car02);
        while (true){
            car2Id = (int) (Math.random() * carImages.size());
            car2Name = getResources().getResourceEntryName(carImages.get(car2Id));
            if (!car2Name.substring(0, car2Name.length() - 1).equals(car1Name.substring(0, car1Name.length() - 1))){
                car2.setImageResource(carImages.get(car2Id));
                break;
            }
        }

        /**sets a random image as the imageView resource of third car image from the carImages ArrayList*/
        car3 = findViewById(R.id.imageView_advance_level_car03);
        while (true){
            car3Id = (int) (Math.random() * carImages.size());
            car3Name = getResources().getResourceEntryName(carImages.get(car3Id));
            if (!car2Name.substring(0, car2Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))
                    && !car1Name.substring(0, car1Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))){
                car3.setImageResource(carImages.get(car3Id));
                break;
            }
        }

        removeCarFromList(carImages, car1Name);
        removeCarFromList(carImages, car2Name);
        removeCarFromList(carImages, car3Name);

        /**update the timer text view while it is ticking and submit the typed answer and show message dialog automatically on finish
         * only if the switch button is checked from the Main Activity*/
        if (switch_state){
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }
                @Override
                public void onFinish() {
                    checkAnswers();
                }
            }.start();

        } else {
            mTextViewCountDown.setText("");
        }
    }

    /**
     * checks whether entered answers by user are correct or not*/
    private void checkAnswers(){
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        car1Input = findViewById(R.id.editText_advance_level_car01);
        car2Input = findViewById(R.id.editText_advance_level_car02);
        car3Input = findViewById(R.id.editText_advance_level_car03);

        counter = findViewById(R.id.textView_advance_level_counter);

        if (!car1Input.getText().toString().equalsIgnoreCase(car1Name.substring(0, car1Name.length() - 1)) ||
                !car2Input.getText().toString().equalsIgnoreCase(car2Name.substring(0, car2Name.length() - 1)) ||
                !car3Input.getText().toString().equalsIgnoreCase(car3Name.substring(0, car3Name.length() - 1)) ) {
            wrongAttempts++;
        }

        if (wrongAttempts <= 3) {
            if (car1Input.getText().toString().equalsIgnoreCase(car1Name.substring(0, car1Name.length() - 1))){
                car1Input.setEnabled(false);
                car1Input.setTextColor(Color.GREEN);
                if (!car1Ans){
                    points++;
                }
                car1Ans = true;
            } else {
                car1Input.setEnabled(true);
                car1Input.setTextColor(Color.RED);
            }

            if (car2Input.getText().toString().equalsIgnoreCase(car2Name.substring(0, car2Name.length() - 1))){
                car2Input.setEnabled(false);
                car2Input.setTextColor(Color.GREEN);
                if (!car2Ans){
                    points++;
                }
                car2Ans = true;
            } else {
                car2Input.setEnabled(true);
                car2Input.setTextColor(Color.RED);
            }

            if (car3Input.getText().toString().equalsIgnoreCase(car3Name.substring(0, car3Name.length() - 1))){
                car3Input.setEnabled(false);
                car3Input.setTextColor(Color.GREEN);
                if (!car3Ans){
                    points++;
                }
                car3Ans = true;
            } else {
                car3Input.setEnabled(true);
                car3Input.setTextColor(Color.RED);
            }

            counterTxt = "Wrong Attempts: " + wrongAttempts;
            counter.setText(counterTxt);

            pointsTxt = "Points : " + points;
            pointsLbl.setText(pointsTxt);

            if (car3Ans && car2Ans && car3Ans) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(AdvanceLevel.this, R.style.AlertDialogCustom)
                        .setTitle(Html.fromHtml("<font color='#388e3c'>CORRECT!</font>"))
                        .setMessage(Html.fromHtml("<font color='black'>YCongratulations you have named all three cars correctly.</font>"))
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                if(!((Activity) AdvanceLevel.this).isFinishing()) {
                    alertDialog.show();
                    Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    ok.setTextColor(Color.BLACK);
                }
                alertDialogDismisser(alertDialog);

                if (switch_state) mCountDownTimer.cancel();
                submitBtn.setText(R.string.button_next);
                flag = true;
            }

        } else {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(AdvanceLevel.this, R.style.AlertDialogCustom)
                    .setTitle(Html.fromHtml("<font color='#dd2c00'>WRONG!</font>"))
                    .setMessage(Html.fromHtml("<font color='black'>You have exceeded the incorrect guesses count.</font>"))
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            if(!((Activity) AdvanceLevel.this).isFinishing()) {
                alertDialog.show();
                Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                ok.setTextColor(Color.BLACK);
            }
            alertDialogDismisser(alertDialog);

            car1textView.setText(car1Ans ? "" : Html.fromHtml("Ans - <font color='#ffab00'>"+car1Name.substring(0, car1Name.length() - 1).toUpperCase()+"</font>"));
            car2TextView.setText(car2Ans ? "" : Html.fromHtml("Ans - <font color='#ffab00'>"+car2Name.substring(0, car2Name.length() - 1).toUpperCase()+"</font>"));
            car3TextView.setText(car3Ans ? "" : Html.fromHtml("Ans - <font color='#ffab00'>"+car3Name.substring(0, car3Name.length() - 1).toUpperCase()+"</font>"));

            if (switch_state) mCountDownTimer.cancel();

            submitBtn.setText(R.string.button_next);
            flag = true;
        }
    }

    /**
     * changes the submit button functionality to answer submission and load next images to start the next round with as necessary*/
    public void advanceLevelButtonHandler(View view){
        if (!flag){
            checkAnswers();
        } else {
            nextImagesGenerator();
            flag = false;
        }
    }

    /**
     * removes car image from the list after showing in the previous round. by that same image will not show up more than once
     * @param carImages list of car images
     * @param carName name of the car*/
    private void removeCarFromList(List<Integer> carImages, String carName){
        if (!carImages.isEmpty()) {
            for (int i = 0; i < carImages.size() - 1; i++) {
                if (getResources().getResourceEntryName(carImages.get(i)).equalsIgnoreCase(carName)) {
                    carImages.remove(i);
                }
            }
        } else {
            messageDialogLauncher("OOPS!", "Sorry! we have run out of images", "#dd2c00");
        }
    }

    /**
     * shows the message dialog
     * @param title title for the alert dialog
     * @param message body for the alert dialog
     * @param color color of the title*/
    private void messageDialogLauncher(String title, String message, String color){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AdvanceLevel.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='"+color+"'>"+title+"</font>"))
                .setMessage(Html.fromHtml("<font color='black'>"+message+"</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) AdvanceLevel.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);
    }

    /**
     * loads next image to the imageView (starts next round) after running out of wrong attempts or running out of time in the previous round*/
    private void nextImagesGenerator(){
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        wrongAttempts = 0;
        car1Ans = false;
        car2Ans = false;
        car3Ans = false;
        submitBtn.setText(R.string.button_submit);
        counter.setText(R.string.chances_count_label);

        car1Input.getText().clear();
        car1textView.setText("");
        car1Input.setEnabled(true);
        car1Input.setTextColor(Color.BLACK);

        car2Input.getText().clear();
        car2TextView.setText("");
        car2Input.setEnabled(true);
        car2Input.setTextColor(Color.BLACK);

        car3Input.getText().clear();
        car3TextView.setText("");
        car3Input.setEnabled(true);
        car3Input.setTextColor(Color.BLACK);

        if (!carImages.isEmpty()) {
            car1Id = (int) (Math.random() * carImages.size());
            car1Name = getResources().getResourceEntryName(carImages.get(car1Id));
            car1.setImageResource(carImages.get(car1Id));

            while (true) {
                car2Id = (int) (Math.random() * carImages.size());
                car2Name = getResources().getResourceEntryName(carImages.get(car2Id));
                if (!car2Name.substring(0, car2Name.length() - 1).equals(car1Name.substring(0, car1Name.length() - 1))) {
                    car2.setImageResource(carImages.get(car2Id));
                    break;
                }
            }

            while (true) {
                car3Id = (int) (Math.random() * carImages.size());
                car3Name = getResources().getResourceEntryName(carImages.get(car3Id));
                if (!car2Name.substring(0, car2Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))
                        && !car1Name.substring(0, car1Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))) {
                    car3.setImageResource(carImages.get(car3Id));
                    break;
                }
            }

            removeCarFromList(carImages, car1Name);
            removeCarFromList(carImages, car2Name);
            removeCarFromList(carImages, car3Name);
        } else {
            messageDialogLauncher("OOPS!", "Sorry! we have run out of images", "#dd2c00");
        }
    }

    /**
     * update the count down timer TextView dynamically when the time changes*/
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    /**
     * dismisses the message dialog window after 5 seconds
     * @param alertDialog alert dialog object*/
    private void alertDialogDismisser(AlertDialog alertDialog){
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable () {
            public void run() {
                if(alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
            }
        };
        mHandler.postDelayed(mRunnable,5000);
    }
}