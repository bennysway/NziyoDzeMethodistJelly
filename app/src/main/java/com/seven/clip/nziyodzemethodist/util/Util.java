package com.seven.clip.nziyodzemethodist.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crystal.crystalpreloaders.interpolators.EaseCubicInOutInterpolator;
import com.google.android.flexbox.FlexboxLayout;
import com.seven.clip.nziyodzemethodist.NziyoDzeMethodist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class Util {

    public enum Storage_old{
        favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption,
        colorflag,textsizeflag,accflag,faviterator,reciterator,themecolor,themename,
        bookmark,bibleoption,
        //firstTimes:biblepickerfirsttime,jsonhandler
    }
    public enum Intents{
        AppFacebookPage,
        DevFacebookPage,
        AppYouTubePage,
        DevYouTubePage,
        AppMessengerPage,

    }

    public static void openExternalIntent(Context context, Intents intentName){
        switch (intentName){
            case AppFacebookPage:
                try {
                    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    String url = "https://www.facebook.com/nziyodzemethodistapp/";
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url)));
                }
                catch (Exception e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bennyswayofficial/")));
                    e.printStackTrace();
                }
                break;
            case AppYouTubePage:
                Intent intent;
                String url = "http://www.youtube.com/channel/UC4hJWMPLGaPA_qT92uWnbhg";
                try {
                    intent =new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
                break;
        }
    }

    public static String contextName(Context context){
        return context.getClass().getSimpleName();
    }
    public static void quickToast(Context packageContext, String text){
        Toast.makeText(packageContext, text,
                Toast.LENGTH_SHORT).show();
    }
    public static void quickToast( String text){
        Toast.makeText(NziyoDzeMethodist.getAppContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    public static void animatePadding(final View view, int top){
        ValueAnimator animator = ValueAnimator.ofInt(view.getPaddingTop(), top);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                view.setPadding(view.getPaddingLeft(), (Integer) valueAnimator.getAnimatedValue(),view.getPaddingRight(), view.getPaddingBottom());
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    public static void animatePadding(final View view, int left, int top, int right, int bottom){
        final ValueAnimator animator0 = ValueAnimator.ofInt(view.getPaddingLeft(), left);
        final ValueAnimator animator1 = ValueAnimator.ofInt(view.getPaddingTop(), top);
        final ValueAnimator animator2 = ValueAnimator.ofInt(view.getPaddingRight(), right);
        final ValueAnimator animator3 = ValueAnimator.ofInt(view.getPaddingBottom(), bottom);
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                view.setPadding(
                        (Integer) animator0.getAnimatedValue(),
                        (Integer) animator1.getAnimatedValue(),
                        (Integer) animator2.getAnimatedValue(),
                        (Integer) animator3.getAnimatedValue()
                );
            }
        });
        animator0.setDuration(200);
        animator0.start();

    }

    public static class CenterAnimate{
        View parent, anchoredView;
        View[] attachedViews;
        float vx,vy,px,py;
        public CenterAnimate(View parent,View anchoredView, View[] attatchedViews){
            this.parent = parent;
            this.anchoredView = anchoredView;
            this.attachedViews = attatchedViews;
            vx = anchoredView.getX();
            vy = anchoredView.getY();
        }
        public Runnable animatePositionElement(final boolean inCenterX,final boolean inCenterY){
            return new Runnable() {
                @Override
                public void run() {
                    int parentHeight = parent.getHeight();
                    int parentWidth = parent.getWidth();
                    int viewHeight = anchoredView.getHeight();
                    int viewWidth = anchoredView.getWidth();
                    px = (parentWidth / 2) - (viewWidth / 2);
                    py = (parentHeight / 2) - (viewHeight / 2);
                    float dx = vx - px;
                    float dy = vy - py;
                    float x1 = anchoredView.getX();
                    float y1 = anchoredView.getY();

                    anchoredView.animate().setInterpolator(new EaseCubicInOutInterpolator()).setDuration(250);
                    for(View view : attachedViews)
                        view.animate().setInterpolator(new EaseCubicInOutInterpolator()).setDuration(250);

                    if(x1!=vx && !inCenterX){
                        anchoredView.animate().xBy(dx);
                        for(View view : attachedViews)
                            view.animate().xBy(dx);
                    }
                    if(y1!=vy && !inCenterY){
                        anchoredView.animate().yBy(dy);
                        for(View view : attachedViews)
                            view.animate().yBy(dy);
                    }
                    if(x1==vx && inCenterX){
                        anchoredView.animate().xBy(-dx);
                        for(View view : attachedViews)
                            view.animate().xBy(-dx);
                    }
                    if(y1==vy && inCenterY){
                        anchoredView.animate().yBy(-dy);
                        for(View view : attachedViews)
                            view.animate().yBy(-dy);
                    }
                    Log.d("Animate", "vx:" + vx + " vy:" + vy + " px:" + px + " py:" + py);
                    Log.d("Animate", "dx:" + dx + " dy:" + dy + " x1:" + x1 + " y1:" + y1);
                    Log.d("Animate", "inCenterX:" + inCenterX + " inCenterY:" + inCenterY);

                }
            };
        }
    }

    public static Date getDate(String dtStart) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMMM dd",Locale.ENGLISH);
        try {
            return format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
            return Calendar.getInstance().getTime();
        }

    }
    public static void animateSize(final View view,int beginValue,int endValue){
        ValueAnimator animator = ValueAnimator.ofInt(beginValue, endValue);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator(1f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) view.getLayoutParams();
                //layoutParams.width = (Integer) animation.getAnimatedValue();
                layoutParams.height = (Integer) animation.getAnimatedValue();
                //sizeChangeListener(((Integer) animation.getAnimatedValue()-_defaultHeight)/(float)_travelDist);
                view.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }
    public static String getDate(Date dtStart) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMMM dd",Locale.ENGLISH);
        return format.format(dtStart);

    }
    public static int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
    public static AudioTrack generateTone(double freqHz, int durationMs)
    {
        int count = (int)(44100.0 * 2.0 * (durationMs / 1000.0)) & ~1;
        short[] samples = new short[count];
        for(int i = 0; i < count; i += 2){
            short sample = (short)(Math.sin(2 * Math.PI * i / (44100.0 / freqHz)) * 0x7FFF);
            samples[i + 0] = sample;
            samples[i + 1] = sample;
        }
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                count * (Short.SIZE / 8), AudioTrack.MODE_STATIC);
        track.write(samples, 0, count);
        return track;
    }
}
