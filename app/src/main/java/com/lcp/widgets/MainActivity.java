package com.lcp.widgets;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lcp.widgets.fragmenet.LadderFragment;
import com.lcp.widgets.fragmenet.LadderLayoutFragment;
import com.lcp.widgets.fragmenet.MenuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new MenuFragment(listener));
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    OnMyClickListener listener = new OnMyClickListener() {
        @Override
        public void onClick(int index) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            switch (index) {
                case 0:
                    break;
                case 1:
                    fragmentTransaction.replace(R.id.content, new LadderFragment());
                    break;
                case 2:
                    fragmentTransaction.replace(R.id.content, new LadderLayoutFragment());
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
