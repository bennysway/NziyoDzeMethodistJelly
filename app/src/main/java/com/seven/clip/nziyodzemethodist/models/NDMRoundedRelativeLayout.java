package com.seven.clip.nziyodzemethodist.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.seven.clip.nziyodzemethodist.R;

import androidx.annotation.RequiresApi;

public class NDMRoundedRelativeLayout extends RelativeLayout {
    private final Path mPath = new Path();
    private Paint paint = new Paint();

    public NDMRoundedRelativeLayout(Context context) {
        super(context);
        init();

    }

    public NDMRoundedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NDMRoundedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NDMRoundedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setAntiAlias(true);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        float round = getResources().getDimension(R.dimen.ndm_rounded_linear_layout_corner_radius);
        mPath.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom()), round, round, Path.Direction.CW);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
    }
}
