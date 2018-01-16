package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by bennysway on 22.11.17.
 */

public class RecentCoordinatorLayout extends AbsCoordinatorLayout {

    private View mBackgroundView;
    private View mBackgroundView2;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public interface OnDismissListener {
        void onLeftDismissed();
        void onRightDismissed();
    }

    public RecentCoordinatorLayout(Context context) {
        super(context);
    }

    public RecentCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecentCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecentCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void doInitialViewsLocation() {
        mBackgroundView = findViewById(R.id.backgroundView);
        mBackgroundView2 = findViewById(R.id.backgroundView2);
        mForegroundView = findViewById(R.id.foregroundView);
        mForegroundView.anchor(-this.getWidth(), 0, this.getWidth());
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        if (index == 0) {
            if (relative == 1) {
                mBackgroundView2.setAlpha(0);
            } else {
                mBackgroundView2.setAlpha(1-relative);
            }
        } else {
            if (relative == 0) {
                mBackgroundView.setAlpha(0);
            } else {
                mBackgroundView.setAlpha(relative);
            }
        }

        if (global == 1) {
            mOnDismissListener.onRightDismissed();
        } else if (global == 0) {
            mOnDismissListener.onLeftDismissed();
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

}
