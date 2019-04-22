package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AlertDialog;

import android.widget.Toast;

/**
 * Created by bennysway on 07.06.17.
 */

public class ChangePictureDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] options = {"Gallery","Remove picture"};
        builder.setTitle("Display picture")
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent toChangePic = new Intent(ChangePictureDialog.super.getContext(),ChoosePic.class);
                                startActivity(toChangePic);
                                break;
                            case 1:
                                Data image = new Data(ChangePictureDialog.super.getContext(),"image");
                                image.update("");
                                QuickToast("Picture will be removed soon");
                                break;
                        }

                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }
    public void QuickToast(String s){
        Toast.makeText(this.getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}
