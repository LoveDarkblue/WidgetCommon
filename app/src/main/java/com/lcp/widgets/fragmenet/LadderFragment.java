package com.lcp.widgets.fragmenet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;


import com.lcp.widgets.R;
import com.lcp.widgets.widget.PaintView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Aislli on 2017/12/26 0026.
 */

public class LadderFragment extends BaseFragment implements View.OnFocusChangeListener {

    Unbinder unbinder;
    private static final int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
            0xff7FFF00, 0xff6495ED, 0xffDC143C,
            0xff008B8B, 0xff006400, 0xff2F4F4F,
            0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
            0xff90EE90, 0xff87CEFA, 0xff800000};
    @BindView(R.id.ldder1)
    PaintView ldder1;
    @BindView(R.id.ldder2)
    PaintView ldder2;
    @BindView(R.id.ldder3)
    PaintView ldder3;
    @BindView(R.id.ldder4)
    PaintView ldder4;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        ldder1.setOnFocusChangeListener(this);
        ldder2.setOnFocusChangeListener(this);
        ldder3.setOnFocusChangeListener(this);
        ldder4.setOnFocusChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_ladder;
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

    @OnClick({R.id.ldder1, R.id.ldder2, R.id.ldder3, R.id.ldder4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ldder1:
                Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ldder2:
                Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ldder3:
                Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ldder4:
                Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
                break;
        }
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
        Animation a = android.view.animation.AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale1);
        a.setInterpolator(new OvershootInterpolator());
        a.setFillAfter(true);
        v.clearAnimation();
        v.setAnimation(a);
        v.bringToFront();
        a.start();
    }

    protected void reduceAnim(View v) {
        Animation a = android.view.animation.AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale0);
        a.setInterpolator(new OvershootInterpolator());
        a.setFillAfter(true);
        v.clearAnimation();
        v.startAnimation(a);
        a.start();
    }
}
