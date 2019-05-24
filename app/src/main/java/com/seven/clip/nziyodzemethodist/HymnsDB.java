package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile;
import com.seven.clip.nziyodzemethodist.util.ProgressInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;


class HymnsDB extends AsyncTask<Void, Integer, Void> {
    Thread sortThread;

    HymnsDB() {
        sortThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(NziyoDzeMethodist.databaseFile!= null){
                    Collections.sort(NziyoDzeMethodist.databaseFile.database, new Comparator<HymnDatabaseFile.Hymn>() {
                        @Override
                        public int compare(HymnDatabaseFile.Hymn o1, HymnDatabaseFile.Hymn o2) {
                            return o1.header.hymnName.compareTo(o2.header.hymnName);
                        }
                    });
                }
            }
        });
    }


    @Override
    protected Void doInBackground(Void... voids) {
        ProgressInputStream raw;
        final Intent intent = new Intent("HymnDatabaseFile");
        try {
            raw = new ProgressInputStream(
                    NziyoDzeMethodist.getAppContext().getAssets().open("sn_ZW.json")
            );
            raw.addListener(new ProgressInputStream.Listener() {
                @Override
                public void onProgressChanged(int percentage) {
                    intent.putExtra("percentage",percentage);
                    NziyoDzeMethodist.getAppContext().sendBroadcast(intent);
                }
            });
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            NziyoDzeMethodist.databaseFile = new Gson().fromJson(rd,HymnDatabaseFile.class);
            sortThread.start();
            intent.putExtra("percentage",101);
            intent.putExtra("databaseLanguageTextView",NziyoDzeMethodist.databaseFile.manifest.language);
            NziyoDzeMethodist.getAppContext().sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
