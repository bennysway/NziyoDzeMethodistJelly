package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by bennysway on 22.11.17.
 */

public class FavoriteCoordinatorLayout extends AbsCoordinatorLayout {

    private View mBackgroundView;
    private SwipeLayout mForegroundView;
    private OnDismissListener mOnDismissListener;

    public interface OnDismissListener {
        void onDismissed();
    }

    public FavoriteCoordinatorLayout(Context context) {
        super(context);
    }

    public FavoriteCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FavoriteCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void doInitialViewsLocation() {
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView = findViewById(R.id.foregroundView);
        mForegroundView.anchor(this.getLeft(),this.getRight());
    }

    @Override
    public void onTranslateChange(float global, int index, float relative) {
        mBackgroundView.setAlpha(global);
        if (global == 1) {
            mOnDismissListener.onDismissed();
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

}
