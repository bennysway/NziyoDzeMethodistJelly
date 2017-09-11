package com.seven.clip.nziyodzemethodist;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bennysway on 05.09.17.
 */
public class ReadWriteJsonVerseFileUtils {
    Activity activity;
    Context context;

    public ReadWriteJsonVerseFileUtils(Context context) {
        this.context = context;
    }

    public void createJsonFileData(String filename, String mJsonResponse) {
        try {
            File checkFile = new File(context.getApplicationInfo().dataDir + "/verses_store/");
            if (!checkFile.exists()) {
                checkFile.mkdir();
            }
            FileWriter file = new FileWriter(checkFile.getAbsolutePath() + "/" + filename);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readJsonFileData(String filename) {
        try {
            File f = new File(context.getApplicationInfo().dataDir + "/verses_store/" + filename);
            if (!f.exists()) {
                //onNoResult();
                return null;
            }
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //onNoResult();
        return null;
    }

    public void deleteFile() {
        File f = new File(context.getApplicationInfo().dataDir + "/verses_store/");
        File[] files = f.listFiles();
        for (File fInDir : files) {
            fInDir.delete();
        }
    }

    public void deleteFile(String fileName) {
        File f = new File(context.getApplicationInfo().dataDir + "/verses_store/" + fileName);
        if (f.exists()) {
            f.delete();
        }
    }

    public boolean fileExists(String fileName) {
        File f = new File(context.getApplicationInfo().dataDir + "/verses_store/" + fileName);
        return f.exists();
    }
}