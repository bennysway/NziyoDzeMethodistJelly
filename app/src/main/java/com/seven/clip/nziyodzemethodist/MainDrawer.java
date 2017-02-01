package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class MainDrawer extends AppCompatActivity {
    Intent toHymnNums,toSettings,toClearData;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_drawer);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            Intent intro = new Intent(MainDrawer.this,IntroActivity.class);
            startActivity(intro);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        toHymnNums = new Intent(this, pickNum2.class);
        toHymnNums.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        final Intent toHymnList = new Intent(this, hymnList2.class);
        toHymnNums.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        final Intent toFavList = new Intent(this, FavList.class);
        final Intent toRecList = new Intent(this, RecList.class);
        final Intent toTest = new Intent(this, SandBox.class);
        final Intent toSearchBox = new Intent(this,SearchDialogue.class);
        toSettings = new Intent(this, Settings.class);
        toClearData = new Intent(this, ClearData.class);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        navView = (NavigationView) findViewById(R.id.startNavigationView);

        View headerLayout = navView.inflateHeaderView(R.layout.nav_header_layout);
        TextView appOwner = (TextView) headerLayout.findViewById(R.id.navHeaderSubTitle);
        ImageView appPic = (ImageView) headerLayout.findViewById(R.id.imageOwner);
        Button hymnNumBut = (Button) findViewById(R.id.hymnNumberBut);
        Button hymnListBut = (Button) findViewById(R.id.hymnListBut);
        TextView mainAppTile = (TextView) findViewById(R.id.mainAppTitle);
        Button favBut = (Button) findViewById(R.id.favBut);
        Button recentBut = (Button) findViewById(R.id.recentBut);
        final View startSearch =  findViewById(R.id.searchButton);
        final View startSearch_on =  findViewById(R.id.searchButton_on);
        final View loadNum = (View) findViewById(R.id.loadingNums);
        final View loadList = (View) findViewById(R.id.loadingList);
        final View loadFav = (View) findViewById(R.id.loadingFavs);
        final View loadRec = (View) findViewById(R.id.loadingRecents);
        Button test = (Button) findViewById(R.id.test);
        final View opDrawer =findViewById(R.id.openMainDrawer);
        final View opDrawer_on =findViewById(R.id.openMainDrawer_on);
        MenuItem settings = (MenuItem) findViewById(R.id.navSettings);

        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vis(startSearch_on);
                invis(startSearch);
                startActivity(toSearchBox);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        invis(startSearch_on);
                        vis(startSearch);
                    }
                },3000);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toTest);
            }
        });




        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Data image = new Data(this,"image");
        String clr = preferences.getString("example_text","Set name");
        appOwner.setText(clr);
        appOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGeneral();
            }
        });
        String imagePath =  image.get();
        if(imagePath.equals(""))
            appPic.setImageDrawable(getResources().getDrawable(R.drawable.nouser));
        else{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);
            appPic.setImageBitmap(bitmap);
        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        mainAppTile.setTypeface(custom_font);


        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invis(opDrawer);
                vis(opDrawer_on);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                vis(opDrawer);
                invis(opDrawer_on);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        opDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vis(opDrawer_on);
                invis(opDrawer);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.openDrawer(navView);
                    }
                },600);
            }
        });

        hymnNumBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNum.animate().alpha(1f).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(toHymnNums);
                                loadNum.animate().alpha(0f).setDuration(7000);
                            }
                        },1000);
                    }
                });

            }
        });

        hymnListBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadList.animate().alpha(1f).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(toHymnList);
                                loadList.animate().alpha(0f).setDuration(7000);

                            }
                        },1000);
                    }
                });


            }
        });

        favBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFav.animate().alpha(1f).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toFavList.putExtra("push","no");
                                startActivity(toFavList);
                                loadFav.animate().alpha(0f).setDuration(7000);

                            }
                        },1000);
                    }
                });


            }
        });

        recentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toRecList);
            }
        });

    }
    public Context getActivity() {
        return this;
    }
    public void QuickToast(String s){
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }
    public void vis(View v){
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().alpha(1f);
    }
    public void invis(final View v) {
        v.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void openSettings(MenuItem item) {
        startActivity(toSettings);
    }
    public void openClearData(MenuItem item) {
        startActivity(toClearData);
    }
    public void shareApp(MenuItem item) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            String sAux = "Methodist Church in Zimbabwe\n" +
                    "Nziyo dzeMethodist\n" +
                    "HymnBook\n" +
                    "Now available on Google Play\n" +
                    "\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share the Gospel:"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    public void openCredits(MenuItem menuItem){
        Intent intent = new Intent(this,Credits.class);
        startActivity(intent);
    }
    public void openNotifications(MenuItem menuItem){
        Intent intent = new Intent(this,Notifications.class);
        startActivity(intent);
    }
    public void openGeneral(){
        toSettings.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, Settings.GeneralPreferenceFragment.class.getName() );
        toSettings.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
        startActivity(toSettings);
        QuickToast("Click Display Name to change.");
    }
}
