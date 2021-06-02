package com.example.caridentifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ShowMessageDialogFragment extends DialogFragment {

    String title;
    String message;

    public ShowMessageDialogFragment(String title, String message)  {
        this.title = title;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setTitle(Html.fromHtml("<font color='#00c853'>"+title+"</font>"))
                .setMessage(Html.fromHtml("<font color='#ffd600'>"+message+"</font>"))
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
}
