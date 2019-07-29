package com.lcp.widgets.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcp.widgets.BuildConfig;
import com.lcp.widgets.R;


/**
 * Created by Aislli on 2017/12/26 0026.
 */
public class LadderLayout extends RelativeLayout {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Region mRegion;
    private Path mDrawPath;
    private int topWidth;
    private int bottomWidth;
    private int mHeight;
    private final int strokWidth = 20;
    private boolean leftRect;
    private boolean rightRect;
    private PorterDuffXfermode porterDuffXfermodeIn;
    private PorterDuffXfermode porterDuffXfermodeOut;

    public void setViewSize(int topWidth, int bottomWidth, int mHeight) {
        this.topWidth = topWidth;
        this.bottomWidth = bottomWidth;
        this.mHeight = mHeight;
    }

    public void setLeftRect(boolean leftRect) {
        this.leftRect = leftRect;
    }

    public void setRightRect(boolean rightRect) {
        this.rightRect = rightRect;
    }

    public LadderLayout(Context context) {
        this(context, null);
    }

    public LadderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LadderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LadderLayout, defStyleAttr, 0);
        topWidth = ta.getDimensionPixelSize(R.styleable.LadderLayout_topWidth, 100);
        bottomWidth = ta.getDimensionPixelSize(R.styleable.LadderLayout_bottomWidth, 100);
//        mHeight = ta.getLayoutDimension(R.styleable.LadderLayout_android_layout_height, "layout_height");
        mHeight = ta.getLayoutDimension(R.styleable.LadderLayout_android_layout_height, 100);
        leftRect = ta.getBoolean(R.styleable.LadderLayout_leftRect, false);
        rightRect = ta.getBoolean(R.styleable.LadderLayout_rightRect, false);
        ta.recycle();

        init();
    }

    public void init() {
        mRegion = new Region();
        mDrawPath = new Path();

        int offset = strokWidth / 2;//偏移stroke的一半以正好显示出圆角
        int tTopWidth = topWidth - strokWidth;//归还偏移的尺寸
        int tBottomWidth = bottomWidth - strokWidth;
        int tHeight = mHeight - strokWidth;
        if (topWidth > bottomWidth) {
            mDrawPath.moveTo(offset, offset);
            mDrawPath.rLineTo(tTopWidth, 0);
            mDrawPath.lineTo(tBottomWidth + (tTopWidth - tBottomWidth) / (rightRect ? 1 : 2) + offset, tHeight + offset);
            mDrawPath.lineTo((leftRect ? 0 : (tTopWidth - tBottomWidth) / 2) + offset, tHeight + offset);
            mDrawPath.close();
        } else {
            mDrawPath.moveTo((leftRect ? 0 : (tBottomWidth - tTopWidth) / 2) + offset, offset);
            mDrawPath.lineTo((tBottomWidth - tTopWidth) / (rightRect ? 1 : 2) + tTopWidth + offset, offset);
            mDrawPath.lineTo(tBottomWidth + offset, tHeight + offset);
            mDrawPath.lineTo(offset, tHeight + offset);
            mDrawPath.close();
        }
    }

    {
        porterDuffXfermodeIn = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        porterDuffXfermodeOut = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        setWillNotDraw(false);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//设置STROKE才能设置成圆角
        paint.setStrokeWidth(strokWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    ImageView imageView;
    TextView textView;

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = Math.max(topWidth, bottomWidth);
        int measuredHeight = mHeight;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            if (childView instanceof ImageView) {
                imageView = (ImageView) childView;
                ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
                layoutParams.width = measuredWidth;
                layoutParams.height = measuredHeight;
            }

            if (childView instanceof TextView) {
                textView = (TextView) childView;
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            paint.setXfermode(porterDuffXfermodeIn);
        } else {
            paint.setXfermode(porterDuffXfermodeOut);
            if (!mDrawPath.isInverseFillType()) {
                mDrawPath.toggleInverseFillType();
            }
        }
        canvas.drawPath(mDrawPath, paint);
        canvas.restoreToCount(saved);
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
        if (mDrawPath.isInverseFillType()) {
            mDrawPath.toggleInverseFillType();
        }
        RectF rectF = new RectF();
        mDrawPath.computeBounds(rectF, true);
        mRegion.setPath(mDrawPath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return mRegion.contains((int) event.getX(), (int) event.getY());
    }
}
