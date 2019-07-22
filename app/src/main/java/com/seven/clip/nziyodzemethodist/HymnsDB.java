package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile;
import com.seven.clip.nziyodzemethodist.models.NDMConstants;
import com.seven.clip.nziyodzemethodist.util.ProgressInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;


public class HymnsDB extends AsyncTask<Void, Integer, Void> {
    Thread sortThread;
    public Thread indexList;
    String sortingBy = "alphabet";

    public HymnsDB() {
        sortThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(NziyoDzeMethodist.databaseFile!= null){
                    String temp = "";
                    if(sortingBy.equals("alphabet")){
                        Collections.sort(NziyoDzeMethodist.databaseFile.database, new Comparator<HymnDatabaseFile.Hymn>() {
                            @Override
                            public int compare(HymnDatabaseFile.Hymn o1, HymnDatabaseFile.Hymn o2) {
                                return o1.header.hymnName.compareTo(o2.header.hymnName);
                            }
                        });
                        for (int i=0; i< NziyoDzeMethodist.databaseFile.database.size(); i++) {
                            if(!NziyoDzeMethodist.databaseFile.database.get(i).header.hymnName.substring(0,1).equals(temp)){
                                temp = NziyoDzeMethodist.databaseFile.database.get(i).header.hymnName.substring(0,1);
                                NziyoDzeMethodist.databaseFile.index.put(i,temp);
                            }
                        }

                    }
                }
            }
        });
    }


    @Override
    protected Void doInBackground(Void... voids) {
        ProgressInputStream raw;
        final Intent intent = new Intent("NDMAction");
        try {
            raw = new ProgressInputStream(
                    NziyoDzeMethodist.getAppContext().getAssets().open("sn_ZW.json")
            );
            raw.addListener(new ProgressInputStream.Listener() {
                @Override
                public void onProgressChanged(int percentage) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bundleType",NDMConstants.NDMBundleType.PROGRESS);
                    bundle.putInt("percentage",percentage);
                    intent.putExtra("NDMBundle",bundle);
                    NziyoDzeMethodist.getAppContext().sendBroadcast(intent);
                }
            });
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            NziyoDzeMethodist.databaseFile = new Gson().fromJson(rd,HymnDatabaseFile.class);
            sortThread.start();
            Bundle bundle = new Bundle();
            bundle.putSerializable("bundleType",NDMConstants.NDMBundleType.PROGRESS);
            bundle.putInt("percentage",101);
            bundle.putString("databaseLanguageTextView",NziyoDzeMethodist.databaseFile.manifest.language);
            intent.putExtra("NDMBundle",bundle);
            NziyoDzeMethodist.getAppContext().sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
