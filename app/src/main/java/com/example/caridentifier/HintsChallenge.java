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

import com.google.android.material.datepicker.MaterialTextInputPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HintsChallenge extends AppCompatActivity {
    /**number of milli seconds for the timer*/
    private static final long START_TIME_IN_MILLIS = 20000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    /**set remaining time to the starting time in the beginning*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    /**an integer to keep track of number of wrong attempts*/
    private int wrongAttempts = 0;
    /**an array list to store car all the images*/
    private List<Integer> carImages = new ArrayList<>();
    private ImageView car_img;
    private TextView textView, counter;
    private EditText input;
    private Button submit;
    /**an integer to store the car id*/
    private int carId;
    /**a string to store car name of the corresponding image*/
    private String carName;
    /**an array to store the characters of the guess word or letter as strings and place those characters as the same index in the actual word*/
    private String[] currentWord;
    /**a string builder to store the dashes according to the number of characters in the car name*/
    private StringBuilder dashes = new StringBuilder();
    /**an array list to store characters of the guessed word or letter as char*/
    private List<Character> charArr = new ArrayList<>();

    /**a boolean to change the functionality of submit button between submitting answer and loading next image*/
    private boolean flag = false;
    /**a boolean to store the switch button state from the MainActivity*/
    private boolean switch_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        submit = findViewById(R.id.button_submit);
        car_img = findViewById(R.id.imageView_hints);
        textView = findViewById(R.id.textView_hints);
        counter = findViewById(R.id.textView_chance_count);
        input = findViewById(R.id.editText_hints);

        switch_state = getIntent().getExtras().getBoolean("switchBtn");
        mTextViewCountDown = findViewById(R.id.textView_hints_timer);

        if (switch_state){
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }
                @Override
                public void onFinish() {
                    replaceDashes();
                }
            }.start();

        } else {
            mTextViewCountDown.setText("");
        }

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
        builder = new AlertDialog.Builder(HintsChallenge.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='#388e3c'>GUESS THE NAME</font>"))
                .setMessage(Html.fromHtml("<font color='black'>You only have 3 incorrect guesses.<br>guess wisely!</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) HintsChallenge.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);

        /**sets a random image as the imageView resource from the carImages ArrayList*/
        carId = (int) (Math.random() * carImages.size());
        car_img.setImageResource(carImages.get(carId));
        carName = getResources().getResourceEntryName(carImages.get(carId));

        /**setting edit text view to show dashes according to the number of characters*/
        int dashCount = (carName.substring(0, carName.length()-1)).length();
        for(int i = 0; i < dashCount; i++){
            dashes.append(" _ ");
        }

        textView.setText(dashes.toString());
    }

    /**
     * replaces the dashes as user submits correct letters of the car manufacturer name*/
    public void replaceDashes() {
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        /**wrong attempts counter*/
        String guess = input.getText().toString();

        if (!carName.contains(guess) || guess.equalsIgnoreCase("")){
            wrongAttempts++;
        }

        if (wrongAttempts <= 3) {
            currentWord = new String[carName.length()];
            String finalGuess = "";

            String txt = "Wrong Attempts: "+ wrongAttempts;
            counter.setText(txt);

            for (int i = 0; i < guess.length(); i++) {
                if (!charArr.contains(guess.charAt(i))) {
                    charArr.add(guess.charAt(i));
                }
            }

            for (int i = 0; i < carName.length(); i++) {
                for (char c2 : charArr) {
                    if (String.valueOf(carName.charAt(i)).equalsIgnoreCase(String.valueOf(c2))) {
                        currentWord[i] = String.valueOf(c2);
                    }
                }
            }

            for (int i = 0; i < currentWord.length - 1; i++) {
                if (currentWord[i] == null) {
                    finalGuess += " _ ";
                } else {
                    finalGuess += " " + currentWord[i] + " ";
                }
            }

            textView.setText(finalGuess.toUpperCase());

            String trimTextView = textView.getText().toString();
            trimTextView = trimTextView.replaceAll("\\s", "");

            if (trimTextView.equalsIgnoreCase(carName.substring(0, carName.length() - 1))){
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HintsChallenge.this, R.style.AlertDialogCustom)
                        .setTitle(Html.fromHtml("<font color='#388e3c'>CORRECT!</font>"))
                        .setMessage(Html.fromHtml("<font color='black'>Congratulations! you have guessed the name right.</font>"))
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                if(!((Activity) HintsChallenge.this).isFinishing()) {
                    alertDialog.show();
                    Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    ok.setTextColor(Color.BLACK);
                }
                alertDialogDismisser(alertDialog);

                if (switch_state) mCountDownTimer.cancel();

                carImages.remove(carId);

                submit.setText(R.string.button_next);
                flag = true;
            }

        } else {
            if (switch_state) mCountDownTimer.cancel();

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(HintsChallenge.this, R.style.AlertDialogCustom)
                    .setTitle(Html.fromHtml("<font color='#dd2c00'>WRONG!</font>"))
                    .setMessage(Html.fromHtml("<font color='black'>You have exceeded the incorrect guesses count.</font>" +
                            "<br>correct answer is <font color='#ffab00'>"+ carName.substring(0, carName.length() - 1).toUpperCase()+"</font>" +
                            "<br>Better luck next time!"))
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            if(!((Activity) HintsChallenge.this).isFinishing()) {
                alertDialog.show();
                Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                ok.setTextColor(Color.BLACK);
            }
            alertDialogDismisser(alertDialog);

            carImages.remove(carId);

            submit.setText(R.string.button_next);
            flag = true;
        }
    }

    /**
     * changes the submit button functionality to answer submission and load next images to start the next round with as necessary*/
    public void submitHandler(View view){
        if (!flag){
            replaceDashes();
            input.setText("");
        } else {
            nextImage();
            flag = false;
        }
    }

    /**
     * loads next image to the imageView (starts next round) after running out of wrong attempts or running out of time in the previous round*/
    private void nextImage(){
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        charArr.clear();
        wrongAttempts = 0;
        dashes.delete(0, dashes.length());
        counter.setText(R.string.chances_count_label);
        input.setText("");

        carId = (int) (Math.random() * carImages.size());
        car_img.setImageResource(carImages.get(carId));
        carName = getResources().getResourceEntryName(carImages.get(carId));

        int dashCount = (carName.substring(0, carName.length()-1)).length();

        for(int i = 0; i < dashCount; i++){
            dashes.append(" _ ");
        }

        textView.setText(dashes.toString());

        submit.setText(R.string.button_submit);
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