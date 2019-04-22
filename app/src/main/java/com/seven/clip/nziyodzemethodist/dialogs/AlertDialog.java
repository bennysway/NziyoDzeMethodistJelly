package com.seven.clip.nziyodzemethodist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.interfaces.DialogListener;

public class AlertDialog extends DialogFragment {
    DialogListener dialogListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Dialog Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title","Edit");
        String message = args.getString("hint","Enter here");
        final String key = args.getString("key","result");
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getResources().getString(R.string.acceptDialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.onPositiveResult(key,true);
                    }
                });
        builder.setNegativeButton(getResources().getString(R.string.cancelDialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onNegativeResult();
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
