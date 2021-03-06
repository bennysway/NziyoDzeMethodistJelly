package com.seven.clip.nziyodzemethodist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.seven.clip.nziyodzemethodist.ClearData;
import com.seven.clip.nziyodzemethodist.Credits;
import com.seven.clip.nziyodzemethodist.MenuListFragment;
import com.seven.clip.nziyodzemethodist.Notifications;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.SandBox;
import com.seven.clip.nziyodzemethodist.Settings;
import com.seven.clip.nziyodzemethodist.fragments.pages.home.HomeTab;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.interfaces.TitleBar;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMConstants;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;
import com.simmorsal.recolor_project.ReColor;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class MainDrawer extends NDMActivity implements FabMenuListener {

    private static final String TAG = MainDrawer.class.getSimpleName();
    TitleBar.LeftClickMode mode = LeftClickMode.MENU;
    Intent toSettings,toClearData;
    private FlowingDrawer mDrawer;
    MenuListFragment mMenuFragment;
    ArrayList<HymnDatabaseFile.Hymn> Hymns;

    RelativeLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        getWindow().getDecorView().setBackground(null);

        final Intent toTest = new Intent(this, SandBox.class);
        toSettings = new Intent(this, Settings.class);
        toClearData = new Intent(this, ClearData.class);
        mDrawer = findViewById(R.id.activity_main_drawer);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        startFragments();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupMenu();
            }
        }, 400);
        //Button test = findViewById(R.id.test);
        parent = findViewById(R.id.activity_main);
        titleBarView = findViewById(R.id.titleBar);
    }

    private void startFragments() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                changeTitleName(getFragment().fragmentName);
            }
        });
        pushFragment(new HomeTab(),null);
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
                    "Available on Google Play\n" +
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
    public void openFB(MenuItem menuItem){
        Intent intent;
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String url = "https://www.facebook.com/nziyodzemethodistapp/";
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url));
            startActivity(intent);
        }

        catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nziyodzemethodistapp/"));
            startActivity(intent);
            e.printStackTrace();
        }
    }
    public void openNotifications(MenuItem menuItem){
        Intent intent = new Intent(this,Notifications.class);
        startActivity(intent);
    }
    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    @Override
    public Runnable onLeftIconClick() {
        return new Runnable() {
            @Override
            public void run() {
                mDrawer.toggleMenu();
            }
        };
    }


    @Override
    public void colapseTitleBar() {

    }

    @Override
    public void setRightIcon(ImageView icon) {

    }

    @Override
    public void setLeftClickMode(LeftClickMode mode) {

    }

    @Override
    public FabPackage getCircleMenu() {
        return null;
    }


    @Override
    public void broadcast(Bundle bundle) {
        switch ((NDMConstants.NDMBundleType)bundle.get("NDMBundle")){
            case TITLE_BAR_HEIGHT:
                int top = bundle.getInt("top",50);
                NDMFragment fragment = this.getFragment();
                if(fragment != null){
                    View view = fragment.getAdjustableView();
                    if(view != null)
                        Util.animateSize(view, view.getLayoutParams().height,top);
                }
                break;
        }
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {
        Log.d(TAG, "transform: Main activity");
        new ReColor(this).setViewBackgroundColor(parent, previousTheme.getTextBackgroundColor(), newTheme.getTextBackgroundColor(), 500);
        titleBarView.transform(previousTheme,newTheme);
    }

    @Override
    public NDMFragment getFragment() {
        return (NDMFragment) getSupportFragmentManager().findFragmentByTag("currentFragment");
    }

    @Override
    public FabPackage getMenu() {
        //Todo
        return null;
    }

}
