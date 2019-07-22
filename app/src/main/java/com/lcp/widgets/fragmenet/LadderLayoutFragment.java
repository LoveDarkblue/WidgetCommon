package com.lcp.widgets.fragmenet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lcp.widgets.R;
import com.lcp.widgets.widget.LadderLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Aislli on 2017/12/26 0026.
 */

public class LadderLayoutFragment extends BaseFragment implements View.OnFocusChangeListener {

    Unbinder unbinder;
    private static final int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
            0xff7FFF00, 0xff6495ED, 0xffDC143C,
            0xff008B8B, 0xff006400, 0xff2F4F4F,
            0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
            0xff90EE90, 0xff87CEFA, 0xff800000};
    @BindView(R.id.rootview)
    RelativeLayout rootview;
    @BindView(R.id.ldder1)
    LadderLayout ldder1;
    @BindView(R.id.ldder2)
    LadderLayout ldder2;
    @BindView(R.id.ldder3)
    LadderLayout ldder3;
    @BindView(R.id.ldder4)
    LadderLayout ldder4;

    @Override
    protected void initView() {
        addView1();
    }


    private void addView1() {
        LadderLayout ladderLayout = getLadderLayout(150, 240, 300, true, false);
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setFocusableInTouchMode(true);
        relativeLayout.setOnFocusChangeListener(this);
        relativeLayout.setClickable(true);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300);
        relativeLayout.addView(ladderLayout, layoutParams2);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 200;
        layoutParams.topMargin = 600;
        rootview.addView(relativeLayout, layoutParams);

        LadderLayout ladderLayout11 = getLadderLayout(240, 150, 300, false, false);
        RelativeLayout relativeLayout11 = new RelativeLayout(getActivity());
        relativeLayout11.setFocusableInTouchMode(true);
        relativeLayout11.setOnFocusChangeListener(this);
        relativeLayout11.setClickable(true);
        RelativeLayout.LayoutParams layoutParams12 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300);
        relativeLayout11.addView(ladderLayout11, layoutParams12);

        RelativeLayout.LayoutParams layoutParams13 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams13.leftMargin = 440 - (240 - 150) / 2 + 5;
        layoutParams13.topMargin = 600;
        rootview.addView(relativeLayout11, layoutParams13);

        LadderLayout ladderLayout21 = getLadderLayout(150, 240, 300, false, true);
        RelativeLayout relativeLayout21 = new RelativeLayout(getActivity());
        relativeLayout21.setFocusableInTouchMode(true);
        relativeLayout21.setOnFocusChangeListener(this);
        relativeLayout21.setClickable(true);
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300);
        relativeLayout21.addView(ladderLayout21, layoutParams22);

        RelativeLayout.LayoutParams layoutParams23 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams23.leftMargin = 680 - ((240 - 150) / 2) * 2 + 5 * 2;
        layoutParams23.topMargin = 600;
        rootview.addView(relativeLayout21, layoutParams23);
    }

    @NonNull
    private LadderLayout getLadderLayout(int topWidth, int bottomWidth, int mHeight, boolean leftRect, boolean rightRect) {
        LadderLayout ladderLayout = new LadderLayout(getActivity());
        ladderLayout.setViewSize(topWidth, bottomWidth, mHeight);
        ladderLayout.setLeftRect(leftRect);
        ladderLayout.setRightRect(rightRect);
        ladderLayout.init();
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ladderLayout.addView(imageView);
        imageView.setImageResource(R.mipmap.ic2);

        TextView textView = new TextView(getActivity());
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textView.setText("title");
        ladderLayout.addView(textView, layoutParams1);
        return ladderLayout;
    }

    @Override
    protected void initData() {
        ldder1.post(new Runnable() {
            @Override
            public void run() {
                ldder1.getImageView().setImageResource(R.mipmap.ic1);
                ldder1.getTextView().setText("西湖风光");
            }
        });
        ldder1.setOnFocusChangeListener(this);
        ldder2.setOnFocusChangeListener(this);
        ldder3.setOnFocusChangeListener(this);
        ldder4.setOnFocusChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_ladder_layout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            enlargeAnim(view);
        } else {
            reduceAnim(view);
        }
    }

    protected void enlargeAnim(View v) {
        Animation a = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale1);
        a.setInterpolator(new OvershootInterpolator());
        a.setFillAfter(true);
        v.clearAnimation();
        v.setAnimation(a);
        v.bringToFront();
        a.start();
    }

    protected void reduceAnim(View v) {
        Animation a = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale0);
        a.setInterpolator(new OvershootInterpolator());
        a.setFillAfter(true);
        v.clearAnimation();
        v.startAnimation(a);
        a.start();
    }

    @OnClick({R.id.ldder1, R.id.ldder2, R.id.ldder3, R.id.ldder4})
    public void onViewClicked(View view) {
        Toast.makeText(getActivity(), "id:" + view.getId(), Toast.LENGTH_SHORT).show();
    }
}
