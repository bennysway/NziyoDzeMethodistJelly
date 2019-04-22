package com.seven.clip.nziyodzemethodist.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * Created by bennysway on 19.08.17.
 */
public class Firebase {
    private static FirebaseUser currentUser;
    private static FirebaseDatabase mDatabase;
    public static FirebaseDatabase getRealTimeDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static FirebaseFirestore getFirestoreDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public static void setNetwork(boolean online){
        if (online) {
            getRealTimeDatabase().goOnline();
            getFirestoreDatabase().enableNetwork();
        } else {
            getRealTimeDatabase().goOffline();
            getFirestoreDatabase();
        }
    }

    public static boolean init(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
            Log.v("Firebase:checkUser","User found");
        else
            Log.v("Firebase:checkUser","User not found");
        return currentUser != null;
    }

}