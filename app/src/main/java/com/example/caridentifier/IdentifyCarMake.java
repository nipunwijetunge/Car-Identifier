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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IdentifyCarMake extends AppCompatActivity {
    /**number of milli seconds for the timer*/
    private static final long START_TIME_IN_MILLIS = 20000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    /**set remaining time to the starting time in the beginning*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private ImageView imgView;
    private Spinner dropdown;
    private Button identify_btn;
    /**an array list to store car all the images*/
    public List<Integer> carImages = new ArrayList<>();
    /**a string to store car name of the corresponding image*/
    private String carName;
    /**an integer to store an id of each car image*/
    private int carId;

    /**a boolean to change the functionality of submit button between submitting answer and loading next image*/
    private boolean flag = false;
    /**a boolean to store the switch button state from the MainActivity*/
    private boolean switch_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        /**creating the dropdown list with car makes*/
        dropdown = findViewById(R.id.car_list);
        identify_btn = findViewById(R.id.button_identify);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cars_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(0);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                identify_btn.setEnabled(!parent.getItemAtPosition(position).equals("Select an option"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch_state = getIntent().getExtras().getBoolean("switchBtn");
        mTextViewCountDown = findViewById(R.id.textView_timer);

        /**
         * reference for timer - https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer*/
        if (switch_state){
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }
                @Override
                public void onFinish() {
                    launchDialog();
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
        builder = new AlertDialog.Builder(IdentifyCarMake.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='#388e3c'>IDENTIFY THE CAR</font>"))
                .setMessage(Html.fromHtml("<font color='black'>Pick the correct car make from the drop down menu corresponding to the shown image.</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) IdentifyCarMake.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);

        /**sets a random image as the imageView resource from the carImages ArrayList*/
        imgView = findViewById(R.id.car_imageView);
        carId = (int) (Math.random() * carImages.size());
        imgView.setImageResource(carImages.get(carId));
        carName = getResources().getResourceEntryName(carImages.get(carId));
    }



    /**
     * shows a message dialog containing info whether selected car make is CORRECT or NOT*/
    public void launchDialog() {
        AlertDialog.Builder builder;
        String selectedAnswer = dropdown.getSelectedItem().toString().toLowerCase();

        if (!carImages.isEmpty()) {
            if (carName.contains(selectedAnswer)) {
                builder = new AlertDialog.Builder(IdentifyCarMake.this, R.style.AlertDialogCustom)
                        .setTitle(Html.fromHtml("<font color='#388e3c'>CORRECT!</font>"))
                        .setMessage(Html.fromHtml("<font color='black'>Congratulations! you have picked the right name.</font>"))
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                if(!((Activity) IdentifyCarMake.this).isFinishing()) {
                    alertDialog.show();
                    Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    ok.setTextColor(Color.BLACK);
                }
                alertDialogDismisser(alertDialog);

                if (switch_state) {
                    mCountDownTimer.cancel();
                }

                for (int i = 0; i < carImages.size() - 1; i++) {
                    if (getResources().getResourceEntryName(carImages.get(i)).equalsIgnoreCase(carName)) {
                        carImages.remove(i);
                    }
                }

            } else {
                builder = new AlertDialog.Builder(IdentifyCarMake.this, R.style.AlertDialogCustom)
                        .setTitle(Html.fromHtml("<font color='#dd2c00'>INCORRECT!</font>"))
                        .setMessage(Html.fromHtml("correct answer is <font color='#ffab00'>"+ carName.substring(0, carName.length() - 1).toUpperCase()+"</font>"))
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                if(!((Activity) IdentifyCarMake.this).isFinishing()) {
                    alertDialog.show();
                    Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    ok.setTextColor(Color.BLACK);
                }
                alertDialogDismisser(alertDialog);

                if (switch_state) {
                    mCountDownTimer.cancel();
                }
            }

            identify_btn = findViewById(R.id.button_identify);
            identify_btn.setText(R.string.button_next);
            identify_btn.setEnabled(true);
            flag = true;
        } else {
            builder = new AlertDialog.Builder(IdentifyCarMake.this)
                    .setTitle("OOPS!")
                    .setMessage("Sorry! we have run out of images")
                    .setPositiveButton("OK", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            if(!((Activity) IdentifyCarMake.this).isFinishing()) {
                alertDialog.show();
                Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                ok.setTextColor(Color.BLACK);
            }
            alertDialogDismisser(alertDialog);
        }
    }

    /**
     * change the submit button functionality to answer submission and load next images to start the next round with*/
    public void identifyHandler(View view){
        if (!flag){
            launchDialog();
        } else {
            dropdown.setSelection(0);
            nextImage();
            flag = false;
        }
    }

    /**
     * loads next image to the imageView (starts next round) after submitting answer or running out of time in the previous round*/
    public void nextImage(){
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        carId = (int) (Math.random() * carImages.size());
        imgView.setImageResource(carImages.get(carId));
        carName = getResources().getResourceEntryName(carImages.get(carId));

        identify_btn = findViewById(R.id.button_identify);
        identify_btn.setText(R.string.button_identify);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                identify_btn.setEnabled(!parent.getItemAtPosition(position).equals("Select an option"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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