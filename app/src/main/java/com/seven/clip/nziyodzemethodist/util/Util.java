package com.seven.clip.nziyodzemethodist.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crystal.crystalpreloaders.interpolators.EaseCubicInOutInterpolator;
import com.google.android.flexbox.FlexboxLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    //UI Element
    public enum Element {
        home,
        church,
        organization,
        hymnList,
        hymnNumber,
        hymn,
        settings,
        UI
    }
    public enum Component{
        icon,
        iconBackground,
        text,
        context,
        textBackground,
        contextBackground,
        background
    }
    public enum Language{
        shona,
        english,
        ndebele,
        zulu
    }
    public enum Storage_old{
        favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption,
        colorflag,textsizeflag,accflag,faviterator,reciterator,themecolor,themename,
        bookmark,bibleoption,
        //firstTimes:biblepickerfirsttime,jsonhandler
    }

    public static String contextName(Context context){
        return context.getClass().getSimpleName();
    }
    public static void quickToast(Context packageContext, String text){
        Toast.makeText(packageContext, text,
                Toast.LENGTH_SHORT).show();
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
}
