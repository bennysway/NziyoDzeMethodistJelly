package com.seven.clip.nziyodzemethodist;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by bennysway on 19.08.17.
 */
public class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}