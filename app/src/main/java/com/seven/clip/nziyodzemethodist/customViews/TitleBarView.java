package com.seven.clip.nziyodzemethodist.customViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.ramotion.circlemenu.CircleMenuView;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.interfaces.ColorChangeListener;
import com.seven.clip.nziyodzemethodist.interfaces.TitleBar;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.models.NDMPageAdapter;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;
import static com.seven.clip.nziyodzemethodist.util.Util.quickToast;

public class TitleBarView extends RelativeLayout implements ColorChangeListener {
    private static final String TAG = TitleBar.class.getSimpleName();
    View rootView;
    TextSwitcher titleTextView;
    ImageView leftIcon;
    ImageView rightIcon;
    ViewPager viewPager;
    NDMPageAdapter pageAdapter;
    LinearLayout linearLayout;
    FlexboxLayout flexboxLayout;
    Drawable flexBackground;
    CircleMenuView menuView;

    private Runnable leftIconRunnable;
    private Runnable rightIconRunnable;
    private int _yDelta,_defaultHeight,_height,_travelDist;
    private float startPos;
    public boolean isTitleBarExpanded;
    public boolean isMenuAvailable;


    public TitleBarView(Context context) {
        super(context);
        init(context,null);
        initFunctions();
        applyColors();
        addTouchListener();
    }
    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        initFunctions();
        applyColors();
        addTouchListener();
    }
    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
        initFunctions();
        applyColors();
        addTouchListener();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
        initFunctions();
        applyColors();
        //addTouchListener();
    }
    private void init(Context context, @Nullable AttributeSet set) {
        rootView = inflate(context, R.layout.view_title_bar, this);
        titleTextView = rootView.findViewById(R.id.titleBarTitle);
        leftIcon = rootView.findViewById(R.id.leftIcon);
        rightIcon = rootView.findViewById(R.id.rightIcon);
        linearLayout = rootView.findViewById(R.id.titleBarContainer);
        linearLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        leftIconRunnable = ((TitleBar) context).onLeftIconClick();
        rightIconRunnable = new Runnable() {
            @Override
            public void run() {
                toggleTitleBar(true);
            }
        };
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.TitleBarView);
        setTitle(typedArray.getString(R.styleable.TitleBarView_title));
        typedArray.recycle();
    }
    private void initFunctions() {
        leftIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                leftIconRunnable.run();
            }
        });
        rightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rightIconRunnable.run();
            }
        });

    }
    private void applyColors() {
        //titleTextView.setTextColor(parseColor(currentTheme.getTextBackgroundColor()));
        ColorThemes.addDrawableFilter(leftIcon.getDrawable(),currentTheme.getTextBackgroundColor());
        ColorThemes.addDrawableFilter(rightIcon.getDrawable(),currentTheme.getTextBackgroundColor());
    }
    private void addTouchListener() {
        calculateTitleBarDimens(50);
        View.OnTouchListener topDrag = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        //_xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.height;
                        startPos = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        if(startPos-event.getY()<0) {
                            animateSize(view,layoutParams2.height,_height/2);
                            if(!isTitleBarExpanded) {
                                addFlexBox();
                            }

                        } else {
                            animateSize(view,layoutParams2.height,_defaultHeight);
                        }
                        isTitleBarExpanded = startPos-event.getY()<0;
                        view.setLayoutParams(layoutParams2);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        //layoutParams.leftMargin = X - _xDelta;
                        layoutParams.height = Y - _yDelta;
                        //layoutParams.rightMargin = (-1*250);
                        //layoutParams.bottomMargin = (-1*250);
                        sizeChangeListener((float) (Y-_yDelta-_defaultHeight)/_travelDist);
                        view.setLayoutParams(layoutParams);
                        break;
                }
                //_root.invalidate();
                return true;
            }
        };
        rootView.setOnTouchListener(topDrag);
    }


    private void loadTabMenu() {
        menuView = rootView.findViewWithTag("circleMenu");
        NDMFragment fragment = ((NDMActivity)getContext()).getFragment();
        if(fragment != null){
            final FabPackage fabPackage = fragment.getMenu();
            if(menuView != null) {
                isMenuAvailable = false;
                this.removeView(menuView);
                menuView = null;
            }
            if(fabPackage != null){
                menuView = new CircleMenuView(getContext(),fabPackage.iconResources,fabPackage.colorResources);
                RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                menuView.setLayoutParams(params);
                menuView.setTag("circleMenu");
                menuView.setAlpha(0);
                menuView.setScaleX(1.5f);
                menuView.setScaleY(1.5f);
                menuView.setEventListener(new CircleMenuView.EventListener(){
                    @Override
                    public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                        super.onButtonClickAnimationEnd(view, buttonIndex);
                        new Handler().postDelayed(fabPackage.runnables.get(buttonIndex),100);
                        toggleTitleBar(false);
                    }
                });
                this.addView(menuView);
                isMenuAvailable = true;
                menuView.animate().alpha(1f).scaleX(1f).scaleY(1f);
            }

        }
    }
    private void removeTabMenu(){
        menuView = rootView.findViewWithTag("circleMenu");
        if(menuView != null) {
            isMenuAvailable = false;
            this.removeView(menuView);
            menuView = null;
        }
    }
    public void removeCustomViews(){
        for(int i=2; i<linearLayout.getChildCount(); i++)
                linearLayout.removeViewAt(i);
    }
    public void calculateTitleBarDimens(int layoutHeight) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(dm);
        Resources r = getResources();
        _defaultHeight = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, layoutHeight,r.getDisplayMetrics()));
        _height = dm.heightPixels;
        _travelDist = (_height/2) -_defaultHeight;
    }
    public void setLeftIconAction(Runnable runnable){
        leftIconRunnable = runnable;
    }
    public void setRightIconAction(Runnable runnable){
        rightIconRunnable = runnable;
    }
    public void setTitle(String title){
        titleTextView.setText(title);
    }
    public void animateSize(final View view,int beginValue,int endValue){
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
                sizeChangeListener(((Integer) animation.getAnimatedValue()-_defaultHeight)/(float)_travelDist);
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!isTitleBarExpanded) removeTabMenu();
                else loadTabMenu();
            }
        });
        animator.start();
    }
    public void sizeChangeListener(float value){
        rightIcon.setRotation(value*180);
        if(isMenuAvailable) menuView.setAlpha(value);
    }
    private void toggleTitleBar(boolean defaultMenu) {
        if(isTitleBarExpanded){
            animateSize(rootView,rootView.getLayoutParams().height,_defaultHeight);
            //removeTabMenu();

        } else {
            animateSize(rootView,rootView.getLayoutParams().height,_defaultHeight + _travelDist);
            if(defaultMenu){
                addFlexBox();
            }
        }
        isTitleBarExpanded = !isTitleBarExpanded;
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isTitleBarExpanded)
                    blurListener.onClosed();
            }
        }, 500);*/

    }
    @Override
    public void transform(Theme previousTheme, final Theme newTheme) {
        //new ReColor(getContext()).setTextViewColor(titleTextView,previousTheme.getTextColor(),newTheme.getTextColor(),500);
    }
    private void addFlexBox(){
        if(flexboxLayout != null)
            return;
        flexboxLayout = new FlexboxLayout(getContext());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15,15,15,15);
        flexboxLayout.setLayoutParams(params);
        flexboxLayout.setBackgroundResource(R.drawable.white_round);
        flexboxLayout.setPadding(4,4,4,4);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexBackground = flexboxLayout.getBackground();
        linearLayout.addView(flexboxLayout);
    }
    private void removeFlexBox(){
        if(flexboxLayout != null)
            linearLayout.removeView(flexboxLayout);
        flexboxLayout = null;
    }
    public TextSwitcher getTitleSwitcherView() {
        return titleTextView;
    }
}
