package com.lcp.widgets.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lcp.widgets.R;


/**
 * Created by Aislli on 2017/12/26 0026.
 */

public class PaintView extends View {

    private Paint paint;
    private Region mRegion;
    private Path mDrawPath;
    private int topWidth;
    private int bottomWidth;
    private int mHeight;
    private final int strokWidth = 20;
    private boolean leftRect;
    private boolean rightRect;
    private int defaultColor;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(strokWidth);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LadderLayout, defStyleAttr, 0);
        topWidth = ta.getDimensionPixelSize(R.styleable.LadderLayout_topWidth, 100);
        bottomWidth = ta.getDimensionPixelSize(R.styleable.LadderLayout_bottomWidth, 100);
        mHeight = ta.getDimensionPixelSize(R.styleable.LadderLayout_height, 100);
        leftRect = ta.getBoolean(R.styleable.LadderLayout_leftRect, false);
        rightRect = ta.getBoolean(R.styleable.LadderLayout_rightRect, false);
        defaultColor = ta.getColor(R.styleable.LadderLayout_pcolor, 0);
        ta.recycle();
        paint.setColor(defaultColor);

        mRegion = new Region();
        mDrawPath = new Path();

        paint.setStrokeJoin(Paint.Join.ROUND);

        int offset = strokWidth / 2;
        if (topWidth > bottomWidth) {
            mDrawPath.moveTo(offset, offset);
            mDrawPath.rLineTo(topWidth, 0);
            mDrawPath.lineTo(bottomWidth + (topWidth - bottomWidth) / (rightRect ? 1 : 2) + offset, mHeight + offset);
            mDrawPath.lineTo((leftRect ? 0 : (topWidth - bottomWidth) / 2) + offset, mHeight + offset);
            mDrawPath.close();
        } else {
            mDrawPath.moveTo((leftRect ? 0 : (bottomWidth - topWidth) / 2) + offset, offset);
            mDrawPath.lineTo((bottomWidth - topWidth) / (rightRect ? 1 : 2) + topWidth + offset, offset);
            mDrawPath.lineTo(bottomWidth + offset, mHeight + offset);
            mDrawPath.lineTo(offset, mHeight + offset);
            mDrawPath.close();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Math.max(topWidth, bottomWidth) + strokWidth, mHeight + strokWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mDrawPath, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isInRect(event)) {
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean isInRect(MotionEvent event) {
        RectF rectF = new RectF();
        mDrawPath.computeBounds(rectF, true);
        mRegion.setPath(mDrawPath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return mRegion.contains((int) event.getX(), (int) event.getY());
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(defaultColor);
        }
        invalidate();
    }
}
