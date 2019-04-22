package com.seven.clip.nziyodzemethodist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.HymnNumberFilter;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.interfaces.DialogListener;

public class InputDialog extends DialogFragment implements TextView.OnEditorActionListener {
    DialogListener dialogListener;
    Context context;
    EditText input;
    String title;
    boolean keyboardShown = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        title = args.getString("title","Edit");
        String hint = args.getString("hint","Enter here");
        String type = args.getString("type","text");
        Boolean edit = args.getBoolean("edit",false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog).setTitle(title);
        input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(hint);
        input.setOnEditorActionListener(this);
        switch (type){
            case "text":
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                String begin = args.getString("begin","1");
                String end = args.getString("end","9223372036854775807");
                input.setFilters(new InputFilter[]{ new HymnNumberFilter(begin, end)});
                break;
        }
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.acceptDialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle args = new Bundle();
                        args.putString("title",title);
                        args.putString("input",input.getText().toString());
                        dialogListener.onPositiveResult("card",args);
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
        if(edit){
            input.setText(hint);
            input.setSelection(0, hint.length());
        }
        input.requestFocus();
        toggleKeyboard();
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(keyboardShown)
            toggleKeyboard();
    }

    private void toggleKeyboard(){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        keyboardShown = !keyboardShown;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Bundle args = new Bundle();
            args.putString("title",title);
            args.putString("input",input.getText().toString());
            dialogListener.onPositiveResult("card",args);
            toggleKeyboard();
            dismiss();
            return true;
        }
        return false;
    }
}
