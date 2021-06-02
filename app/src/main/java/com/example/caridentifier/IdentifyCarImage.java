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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IdentifyCarImage extends AppCompatActivity {
    /**number of milli seconds for the timer*/
    private static final long START_TIME_IN_MILLIS = 20000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    /**set remaining time to the starting time in the beginning*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    /**an array list to store car all the images*/
    private List<Integer> carImages = new ArrayList<>();
    private ImageView car1, car2, car3;
    private Button submitBtn;
    /**integers to store ids of each car image*/
    private int car1Id, car2Id, car3Id, randomCarNameId;
    /**strings to store all three car names of loaded images*/
    private String car1Name, car2Name, car3Name, randomCarName;
    private TextView randomCarNameTextView;

    /**a string to keep track of the correct image number according to the randomly generated name*/
    private String i;

    /**a boolean to store the switch button state from the MainActivity*/
    private boolean switch_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        switch_state = getIntent().getExtras().getBoolean("switchBtn");
        mTextViewCountDown = findViewById(R.id.textView_identify_image_timer);

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
        builder = new AlertDialog.Builder(IdentifyCarImage.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='#388e3c'>ADVANCE LEVEL</font>"))
                .setMessage(Html.fromHtml("<font color='black'>Write down the car make in the corresponding text field.</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) IdentifyCarImage.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);

        submitBtn = findViewById(R.id.button_car_images);
        submitBtn.setVisibility(View.INVISIBLE);

        /**sets a random image as the imageView resource of first car image from the carImages ArrayList*/
        car1 = findViewById(R.id.imageView_car1);
        car1Id = (int) (Math.random() * carImages.size());
        car1Name = getResources().getResourceEntryName(carImages.get(car1Id));
        car1.setImageResource(carImages.get(car1Id));

        /**sets a random image as the imageView resource of second car image from the carImages ArrayList*/
        car2 = findViewById(R.id.imageView_car2);
        while (true){
            car2Id = (int) (Math.random() * carImages.size());
            car2Name = getResources().getResourceEntryName(carImages.get(car2Id));
            if (!car2Name.substring(0, car2Name.length() - 1).equals(car1Name.substring(0, car1Name.length() - 1))){
                car2.setImageResource(carImages.get(car2Id));
                break;
            }
        }

        /**sets a random image as the imageView resource of third car image from the carImages ArrayList*/
        car3 = findViewById(R.id.imageView_car3);
        while (true){
            car3Id = (int) (Math.random() * carImages.size());
            car3Name = getResources().getResourceEntryName(carImages.get(car3Id));
            if (!car2Name.substring(0, car2Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))
                && !car1Name.substring(0, car1Name.length() - 1).equals(car3Name.substring(0, car3Name.length() - 1))){
                car3.setImageResource(carImages.get(car3Id));
                break;
            }
        }

        /**generates a random car name from all the car manufacturers listed*/
        randomCarNameTextView = findViewById(R.id.textView_car_images);
        while (true){
            randomCarNameId = (int) (Math.random() * carImages.size());
            randomCarName = getResources().getResourceEntryName(carImages.get(randomCarNameId));
            if (randomCarName.equalsIgnoreCase(car1Name) || randomCarName.equalsIgnoreCase(car2Name) || randomCarName.equalsIgnoreCase(car3Name)){
                randomCarNameTextView.setText(randomCarName.substring(0, randomCarName.length() - 1).toUpperCase());
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
                    if (car1Name.substring(0, car1Name.length() - 1).equals(randomCarNameTextView.getText().toString().toLowerCase())){
                        i = "01";
                    } else if (car2Name.substring(0, car2Name.length() - 1).equals(randomCarNameTextView.getText().toString().toLowerCase())){
                        i = "02";
                    } else {
                        i = "03";
                    }

                    messageDialogLauncher("WRONG!", "Correct image is image no."+i,"#dd2c00");

                    submitBtn.setText(R.string.button_next);
                    submitBtn.setVisibility(View.VISIBLE);
                }
            }.start();

        } else {
            mTextViewCountDown.setText("");
        }
    }

    /**
     * removes displayed car images from the list so same image will not load again
     * @param carImages list of car images
     * @param carName name of the car in the image*/
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
     * shows a message dialog when the user tap on the image saying whether the answer is correct or not
     * @param  view to get the view id of the tapped image*/
    public void onClick(View view){
        if (car1Name.substring(0, car1Name.length() - 1).equals(randomCarNameTextView.getText().toString().toLowerCase())){
            i = "01";
        } else if (car2Name.substring(0, car2Name.length() - 1).equals(randomCarNameTextView.getText().toString().toLowerCase())){
            i = "02";
        } else {
            i = "03";
        }

        switch (view.getId()){
            case R.id.imageView_car1:
                if (randomCarNameTextView.getText().toString().equalsIgnoreCase(car1Name.substring(0, car1Name.length() - 1))){
                    messageDialogLauncher("CORRECT!", "Congratulations! You picked the right image.", "#388e3c");
                } else {
                    messageDialogLauncher("WRONG!", "Correct image is image no."+i,"#dd2c00");
                }
                if (switch_state) mCountDownTimer.cancel();
                submitBtn.setText(R.string.button_next);
                submitBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.imageView_car2:
                if (randomCarNameTextView.getText().toString().equalsIgnoreCase(car2Name.substring(0, car2Name.length() - 1))){
                    messageDialogLauncher("CORRECT!", "Congratulations! You picked the right image.","#388e3c");
                } else {
                    messageDialogLauncher("WRONG!", "Correct image is image no."+i,"#dd2c00");
                }
                if (switch_state) mCountDownTimer.cancel();
                submitBtn.setText(R.string.button_next);
                submitBtn.setVisibility(View.VISIBLE);

                break;
            case R.id.imageView_car3:
                if (randomCarNameTextView.getText().toString().equalsIgnoreCase(car3Name.substring(0, car3Name.length() - 1))){
                    messageDialogLauncher("CORRECT!", "Congratulations! You picked the right image.","#388e3c");
                } else {
                    messageDialogLauncher("WRONG!", "Correct image is image no."+i,"#dd2c00");
                }
                if (switch_state) mCountDownTimer.cancel();
                submitBtn.setText(R.string.button_next);
                submitBtn.setVisibility(View.VISIBLE);

                break;
        }
        car1.setEnabled(false);
        car2.setEnabled(false);
        car3.setEnabled(false);
    }

    /**
     * shows the message dialog
     * @param title title for the alert dialog
     * @param message body for the alert dialog
     * @param color color of the title*/
    private void messageDialogLauncher(String title, String message, String color){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(IdentifyCarImage.this, R.style.AlertDialogCustom)
                .setTitle(Html.fromHtml("<font color='"+color+"'>"+title+"</font>"))
                .setMessage(Html.fromHtml("<font color='black'>"+message+"</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        if(!((Activity) IdentifyCarImage.this).isFinishing()) {
            alertDialog.show();
            Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setTextColor(Color.BLACK);
        }
        alertDialogDismisser(alertDialog);
    }

    /**
     * creates the next round by generating new three images to the screen
     * @param view view for the generated items*/
    public void nextImagesGenerator(View view){
        if (switch_state) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mCountDownTimer.start();
        }

        car1.setEnabled(true);
        car2.setEnabled(true);
        car3.setEnabled(true);

        if (!carImages.isEmpty()) {
            submitBtn.setVisibility(View.INVISIBLE);
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

            while (true) {
                randomCarNameId = (int) (Math.random() * carImages.size());
                randomCarName = getResources().getResourceEntryName(carImages.get(randomCarNameId));
                if (randomCarName.equalsIgnoreCase(car1Name) || randomCarName.equalsIgnoreCase(car2Name) || randomCarName.equalsIgnoreCase(car3Name)) {
                    randomCarNameTextView.setText(randomCarName.substring(0, randomCarName.length() - 1).toUpperCase());
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
     * updates the count down timer text view as the timer decrements*/
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    /**
     * dismisses the alert dialog automatically after five seconds
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