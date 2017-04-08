package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainDrawer extends AppCompatActivity {
    Intent toHymnNums,toSettings,toClearData;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    ImageView appPic;
    TextView appOwner;
    boolean request = false;
    Zvinokosha moreFeatures;
    MenuItem HelpMenu;
    int fromPrevActivity=0;


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
        final Intent toCaptionList = new Intent(this,CaptionList.class);
        final Intent toTest = new Intent(this, SandBox.class);
        final Intent toSearchBox = new Intent(this,SearchDialogue.class);
        moreFeatures = new Zvinokosha(this);
        toSettings = new Intent(this, Settings.class);
        toClearData = new Intent(this, ClearData.class);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        navView = (NavigationView) findViewById(R.id.startNavigationView);

        View headerLayout = navView.inflateHeaderView(R.layout.nav_header_layout);
        appOwner = (TextView) headerLayout.findViewById(R.id.navHeaderSubTitle);
        appPic = (ImageView) headerLayout.findViewById(R.id.imageOwner);
        Button hymnNumBut = (Button) findViewById(R.id.hymnNumberBut);
        Button hymnListBut = (Button) findViewById(R.id.hymnListBut);
        TextView mainAppTile = (TextView) findViewById(R.id.mainAppTitle);
        Button favBut = (Button) findViewById(R.id.favBut);
        Button recentBut = (Button) findViewById(R.id.recentBut);
        Button captionsBut = (Button) findViewById(R.id.captionsListBut);
        final View startSearch =  findViewById(R.id.searchButton);
        final View startSearch_on =  findViewById(R.id.searchButton_on);
        Button test = (Button) findViewById(R.id.test);
        final View opDrawer =findViewById(R.id.openMainDrawer);
        final View opDrawer_on =findViewById(R.id.openMainDrawer_on);
        MenuItem settings = (MenuItem) findViewById(R.id.navSettings);
        HelpMenu = (MenuItem) findViewById(R.id.Help);

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

        final Data withCaptions = new Data(this, "withcaption");
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickToast(withCaptions.get());
            }
        });

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

        appPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChangePic = new Intent(MainDrawer.this,ChoosePic.class);
                startActivity(toChangePic);
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
            public void onClick(View view) { startActivity(toHymnNums);
            }
        });

        hymnListBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {startActivity(toHymnList);
            }
        });

        favBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFavList.putExtra("push","no");
                startActivity(toFavList);
            }
        });

        recentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toRecList);
            }
        });

        captionsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toCaptionList);
            }
        });

        fromPrevActivity = getIntent().getIntExtra("option",0);
        Options(fromPrevActivity);
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Data picFlag = new Data(this,"picflag");
        String clr = preferences.getString("example_text","Set name");
        appOwner.setText(clr);
        appOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGeneral();
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updatePicture();

    }
    public void updatePicture(){
        Data image = new Data(this,"image");
        String imagePath =  image.get();
        if(imagePath.equals("")){
            appPic.setImageDrawable(getResources().getDrawable(R.drawable.nouser));

        }
        else{
            appPic.setImageBitmap(decodeSampledBitmapFromResource(
                    getResources(),
                    R.id.imageOwner,
                    appPic.getMeasuredWidth(),
                    appPic.getMeasuredHeight()
            ));
        }

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
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {
        Data image = new Data(this,"image");
        String imagePath =  image.get();

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
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
    public void OpenHelp(MenuItem menuItem){
        //Todo
        //Make help Intent
    }
    public void Options(int o){
        switch (o){
            case 1:
                mDrawerLayout.openDrawer(navView);
                break;
            default:
                break;
        }
    }
}
