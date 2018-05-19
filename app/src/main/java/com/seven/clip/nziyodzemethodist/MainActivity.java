package com.seven.clip.nziyodzemethodist;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static String userData(Context context, String stringKey, String mode, String data){

        //keys include: favlist,reclist,showsplash,color,image,textsize,recordflag,withcaptions
        SharedPreferences.Editor editor = context.getSharedPreferences(stringKey, MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences(stringKey, MODE_PRIVATE);
        String restoredText = prefs.getString("list", null);
        String list = "";
        if (restoredText != null) {
            list = prefs.getString("list", "");//"No name defined" is the default value.
        }


        switch (mode){
            case "pushBack":
                list = list + data + ",";
                editor.putString("list", list);
                editor.apply();
                break;

            case "pushFront":
                list =  data + "," + list;
                editor.putString("list", list);
                editor.apply();
                break;

            case "delete":
                list = list.replace(data+",","");
                editor.putString("list", list);
                editor.apply();
                break;

            case "deleteRecord":
                list = list.replace(data,"");
                editor.putString("list", list);
                editor.apply();
                break;

            case "deleteCaption":
                list = list.replace(data,"");
                editor.putString("list", list);
                editor.apply();
                break;

            case "deleteAll":
                list = "";
                editor.putString("list", list);
                editor.apply();
                break;

            case "update":
                list = data;
                editor.putString("list", list);
                editor.apply();
                break;

            case "find":
                list = "."+list;
                data +=",";
                if(list.indexOf(data) > 0)
                    return "true";
                else
                    return "false";


        }
        return list;
    }




}
