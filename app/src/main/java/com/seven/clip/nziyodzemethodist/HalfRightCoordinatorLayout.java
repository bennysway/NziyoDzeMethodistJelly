package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by bennysway on 06.11.17.
 */

public class HalfRightCoordinatorLayout extends AbsCoordinatorLayout {
    private View mBackgroundView;

    public HalfRightCoordinatorLayout(Context context) {
        super(context);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void doInitialViewsLocation() {
        SwipeLayout mForegroundView = findViewById(R.id.foregroundView);
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView.anchor(mBackgroundView.getRight(), mBackgroundView.getLeft());
        mBackgroundView.setTranslationX(-mBackgroundView.getWidth());
        mForegroundView.translateTo(0);
    }

    @Override
    public void onTranslateChange(float globalPercent, int index, float relativePercent) {
        mBackgroundView.setTranslationX((globalPercent - 1) * mBackgroundView.getWidth());
    }
}
