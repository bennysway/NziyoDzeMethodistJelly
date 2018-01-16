package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.ms_square.etsyblur.BlurConfig;
import com.ms_square.etsyblur.BlurDialogFragment;

/**
 * Created by bennysway on 09.01.18.
 */

public class CreateDialogDialogFragment extends BlurDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new PickNumDialog(getContext());
    }

    @NonNull
    protected BlurConfig blurConfig() {
        return new BlurConfig.Builder()
                .debug(true)
                .build();
    }
}

