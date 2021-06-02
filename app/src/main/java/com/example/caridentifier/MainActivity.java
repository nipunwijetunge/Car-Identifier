package com.example.caridentifier;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.Future;

/**
 * Car Identifier
 * The Car Identifier application is a simple car image identifying game
 * with four different levels and a count down timer.
 *
 * @author Nipun Wijetunge*/

public class MainActivity extends AppCompatActivity {
    private SwitchCompat timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.switch_main);
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
            * pop up a message dialog when the switch button is checked
            *
            * @param buttonView the switch button view
             * @param isChecked the boolean value whether switch button is checked or not*/
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timer.setChecked(true);
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom)
                            .setTitle(Html.fromHtml("<font color='#388e3c'>TIMER</font>"))
                            .setMessage(Html.fromHtml("<font color='black'>By enabling this feature you set 20 second countdown timer for your each submission " +
                                    "in each game level.</font>"))
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    AlertDialog alertDialog = builder.create();
                    /**
                     * reference to get rid of BadTokenException error - https://stackoverflow.com/questions/9529504/unable-to-add-window-token-android-os-binderproxy-is-not-valid-is-your-activ*/
                    if(!((Activity) MainActivity.this).isFinishing()) {
                        alertDialog.show();
                        Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        ok.setTextColor(Color.BLACK);
                    }

                    Handler mHandler = new Handler();
                    Runnable mRunnable = new Runnable () {
                        public void run() {
                            if(alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                        }
                    };
                    mHandler.postDelayed(mRunnable,5000);

                    Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    ok.setTextColor(Color.BLACK);
                }
            }
        });


        ImageView logo = findViewById(R.id.logo_imageView);
        logo.setImageResource(R.drawable.logo);
    }

    public void identifyCarMakeLauncher(View view){
        Intent intent = new Intent(this, IdentifyCarMake.class);
        intent.putExtra("switchBtn", timer.isChecked());
        startActivity(intent);
    }

    public void hintsChallengeLauncher(View view){
        Intent intent = new Intent(this, HintsChallenge.class);
        intent.putExtra("switchBtn", timer.isChecked());
        startActivity(intent);
    }

    public void identifyCarImageLauncher(View view){
        Intent intent = new Intent(this, IdentifyCarImage.class);
        intent.putExtra("switchBtn", timer.isChecked());
        startActivity(intent);
    }

    public void advanceLevelLauncher(View view){
        Intent intent = new Intent(this, AdvanceLevel.class);
        intent.putExtra("switchBtn", timer.isChecked());
        startActivity(intent);
    }
}