package com.olsttech.myalarm.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.olsttech.myalarm.R;

/**
 * Created by adetunji on 13/01/2018.
 */

public class DeleteAlarmDialog extends DialogFragment {

    private String setMessage;
    private OnAlarmDeleteClicked mOnAlarmDeleteClicked;

    public void setSetMessage(String setMessage, OnAlarmDeleteClicked onAlarmDeleteClicked) {
        this.mOnAlarmDeleteClicked = onAlarmDeleteClicked;
        this.setMessage = setMessage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class because this dialog has a simple UI
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(getResources().getDrawable(R.drawable.delete_red_icon));
        builder.setTitle(setMessage);
        //builder.setView();

        builder
                // An OK button dismiss the dialog
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mOnAlarmDeleteClicked.onAlarmDelete();
                        // dismiss the dialog box
                        dialog.dismiss();
                    }
                })

                // A "Cancel" button that does nothing
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }

                });

        // Create the object and return it
        return builder.create();
    }

    public interface OnAlarmDeleteClicked{
        void onAlarmDelete();
    }
}
