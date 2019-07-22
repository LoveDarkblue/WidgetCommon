package com.lcp.widgets.fragmenet;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.lcp.widgets.OnMyClickListener;
import com.lcp.widgets.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Aislli on 2017/12/26 0026.
 */

@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    private OnMyClickListener listener;

    @SuppressLint("ValidFragment")
    public MenuFragment(OnMyClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_menu, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                listener.onClick(0);
                break;
            case R.id.btn2:
                listener.onClick(1);
                break;
            case R.id.btn3:
                listener.onClick(2);
                break;
            case R.id.btn4:
                listener.onClick(3);
                break;
        }
    }
}
