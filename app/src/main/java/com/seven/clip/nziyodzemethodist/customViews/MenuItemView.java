package com.seven.clip.nziyodzemethodist.customViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluehomestudio.animationplus.animation.HeightAnimation;
import com.seven.clip.nziyodzemethodist.CircularTextView;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.CustomMenuItem;
import com.seven.clip.nziyodzemethodist.models.NDMRoundedRelativeLayout;
import com.seven.clip.nziyodzemethodist.util.Util;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

public class MenuItemView extends NDMRoundedRelativeLayout {
    TextView title,subtitle,notificationCount;
    AppCompatImageView icon;
    //int icon;
    String toolTip;
    GradientDrawable gradient;
    ViewGroup rootView;
    int delay;

    public MenuItemView(Context context) {
        super(context);
        init(context,null);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    private void init(Context context,@Nullable AttributeSet set){
        rootView = (ViewGroup) inflate(context, R.layout.view_menu_item, this);
        initIds();
        initAttrs(set);
    }

    private void initAttrs(@Nullable AttributeSet set) {
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.MenuItemView);
        title.setText(typedArray.getString(R.styleable.MenuItemView_menu_title));
        notificationCount.setText(typedArray.getString(R.styleable.MenuItemView_menu_notification_count));
        icon.setImageDrawable(typedArray.getDrawable(R.styleable.MenuItemView_menu_icon));
        typedArray.recycle();
    }

    private void initIds() {
        title = rootView.findViewById(R.id.title);
        subtitle = rootView.findViewById(R.id.subtitle);
        notificationCount = rootView.findViewById(R.id.notificationCount);
        icon = rootView.findViewById(R.id.icon);
    }

    public void setCustomMenuDetails(CustomMenuItem customMenuItem) {
        this.title.setText(customMenuItem.title);
        this.subtitle.setText(customMenuItem.subtitle);
        this.icon.setImageResource(customMenuItem.itemIconRes);
        //this.icon=customMenuItem.itemIconRes;
    }

    public void setText(String title){
        this.title.setText(title);
    }
    public void setText(String title, String subtitle){
        this.title.setText(title);
    }
    public void setText(String title, String subtitle, String toolTip){
        this.title.setText(title);
    }
    public void setIcon(int resId,int delay){
        //this.icon=resId;
        this.icon.setImageResource(resId);
        this.delay = delay;
    }
    public void setDelay(int delay){
        this.delay = delay;
    }
    public void setGradient(int startColor, int endColor){

        gradient = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {startColor,endColor});
        gradient.setCornerRadius(0f);

        title.setBackground(gradient);
    }
    public void showIcon() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Util.animateSize(icon,0,Util.convertDpToPixel(100));
                }
            }, delay);

    }
    public void hideIcon(){}
    public void setAnimationInterpolator(Interpolator interpolator){

    }

    public void setNotificationCount(int notifications) {
        if(notifications != -1)
            notificationCount.setText(String.valueOf(notifications));
        else
            notificationCount.setText("");
    }
}
