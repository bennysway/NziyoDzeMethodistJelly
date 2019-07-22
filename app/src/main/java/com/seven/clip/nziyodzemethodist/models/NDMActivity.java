package com.seven.clip.nziyodzemethodist.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.customViews.TitleBarView;
import com.seven.clip.nziyodzemethodist.dialogs.PlayKeyDialog;
import com.seven.clip.nziyodzemethodist.interfaces.ColorChangeListener;
import com.seven.clip.nziyodzemethodist.interfaces.TitleBar;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile.Hymn.Captions;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.LinkedList;
import java.util.Queue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public abstract class NDMActivity extends AppCompatActivity implements TitleBar,ColorChangeListener {
    private static final String TAG = NDMActivity.class.getSimpleName();
    public TitleBarView titleBarView;
    public Theme theme;
    BroadcastReceiver NDMReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            broadcast(intent.getBundleExtra("NDMBundle"));
        }
    };
    public abstract void broadcast(Bundle bundle);
    @Override
    public void transform(Theme previousTheme, Theme newTheme) {
        Log.d(TAG, "transform: Theming titleBar...");
        titleBarView.transform(previousTheme,newTheme);
    }
    public void pushNotification(final Object object) {
        if(object instanceof Captions){
            displayCaptionNotification((Captions) object);
        }

    }

    private void displayCaptionNotification(final Captions caption){
        Queue<SnackPackage> queue = new LinkedList<>();
        Runnable keyRunnable = new Runnable() {
            @Override
            public void run() {
                PlayKeyDialog dialog = new PlayKeyDialog(NDMActivity.this,caption.key);
                dialog.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
                dialog.show();
            }
        };

        if(caption.subtitle!=null) queue.offer(new SnackPackage(caption.subtitle));
        if(caption.tonicSolfa!=null) queue.offer(new SnackPackage(caption.tonicSolfa));
        if(caption.key!=null) queue.offer(new SnackPackage(caption.key,"Listen",keyRunnable));
        if(caption.tune!=null) queue.offer(new SnackPackage(caption.tune));
        if(caption.meter!=null) queue.offer(new SnackPackage(caption.meter));
        if(caption.ndebele!=null) queue.offer(new SnackPackage(caption.ndebele));

        titleBarView.showSnack(queue);

    }
    
    public void changeTitleName(final String title){
        titleBarView.setTitle(title);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) titleBarView.getTitleSwitcherView().getCurrentView();
                int height = 50 + ((textView.getLineCount() - 1) * 30);
                int defaultHeight = Util.convertDpToPixel(50);
                titleBarView.calculateTitleBarDimens(height);
                if(!titleBarView.isTitleBarExpanded){
                    if(height>50){
                        titleBarView.animateSize(titleBarView,
                                titleBarView.getHeight(),
                                Util.convertDpToPixel(height));
                        titleBarView.adjustFragmentVisibleSize(Util.convertDpToPixel(height));
                    } else {
                        titleBarView.animateSize(titleBarView,
                                titleBarView.getHeight(),
                                defaultHeight);
                        titleBarView.adjustFragmentVisibleSize(defaultHeight);

                    }
                } else {
                    if(!titleBarView.menuLock)
                        titleBarView.showTabMenu();
                }

            }
        },1000);
    }
    public abstract NDMFragment getFragment();
    public void pushFragment(NDMFragment fragment, Bundle bundle) {
        FragmentTransaction ft;
        NDMFragment previousFragment =(NDMFragment) getSupportFragmentManager().findFragmentByTag("currentFragment");
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.frag_fade_zoom_in_enter,
                R.anim.frag_fade_zoom_in_exit,
                R.anim.frag_fade_zoom_out_enter,
                R.anim.frag_fade_zoom_out_exit);
        if(previousFragment != null) ft.hide(previousFragment);
        if(bundle != null) fragment.setArguments(bundle);
        ft.add(R.id.fragmentPlaceHolder, fragment, "currentFragment");
        if(previousFragment != null) ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(NDMReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(NDMReceiver,new IntentFilter("NDMAction"));
    }



}
