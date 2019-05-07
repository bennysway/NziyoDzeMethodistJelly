package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.clip.nziyodzemethodist.models.Hymn;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class HymnsDB {
    private Context context;

    HymnsDB(Context cxt) {
        this.context=cxt;
        (new LoadDatabase()).execute();
    }



    private class LoadDatabase extends AsyncTask<Void, String, ArrayList<Hymn>> {

        @Override
        protected ArrayList<Hymn> doInBackground(Void... strings) {
            Type collection = new TypeToken<ArrayList<Hymn>>() {}.getType();
            return (new Gson()).fromJson(readJSONFromAsset(),collection);
        }

        @Override
        protected void onPostExecute(ArrayList<Hymn> hymns) {
            super.onPostExecute(hymns);
            Util.quickToast(context, hymns.size() + "");
        }
    }


    public String readJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("hymnsDB.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
