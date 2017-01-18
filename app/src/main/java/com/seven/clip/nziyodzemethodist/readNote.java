package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class readNote extends AppCompatActivity {
    String fullFile,key,record;
    Data note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        key = getIntent().getStringExtra("key");
        fullFile = getIntent().getStringExtra("fullFile");
        record = getIntent().getStringExtra("record");
        final TextView displaytext = (TextView) findViewById(R.id.displayNoteText);
        Button share = (Button) findViewById(R.id.shareOpenedNote);
        final Button edit = (Button) findViewById(R.id.editDisplayedNote);
        final Button delete = (Button) findViewById(R.id.deleteDisplayedNote);
        final EditText editor = (EditText) findViewById(R.id.editOpenedNote);
        editor.setVisibility(View.INVISIBLE);
        editor.setAlpha(0f);
        note = new Data(this,key+fullFile);


        displaytext.setText(note.get());
        editor.setText(note.get());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(displaytext.getText().toString());
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaytext.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        displaytext.setVisibility(View.INVISIBLE);
                        editor.setVisibility(View.VISIBLE);
                        editor.animate().alpha(1f).setDuration(1000);
                        edit.setText("Save");
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String newText = editor.getText().toString();
                                if(!newText.equals("")){
                                    note.update(newText);
                                    QuickToast("Saved");
                                    finish();
                                }
                                else
                                {
                                    QuickToast("Cannot save. Write text or Click cancel");
                                }
                            }
                        });
                        delete.setText("Cancel");

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDelete = new Intent(readNote.this,DeleteCaption.class);
                toDelete.putExtra("key",key);
                toDelete.putExtra("record",record);
                toDelete.putExtra("content",displaytext.getText().toString());
                toDelete.putExtra("path",fullFile);
                toDelete.putExtra("type","note");
                startActivity(toDelete);            }
        });


    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public void copy(String s){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hymn", s);
        clipboard.setPrimaryClip(clip);
        QuickToast("Note copied to clipboard");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            i.putExtra(Intent.EXTRA_TEXT, s);
            startActivity(Intent.createChooser(i, "Share note via:"));
        } catch(Exception e) {
            //e.toString();
        }
    }

}
