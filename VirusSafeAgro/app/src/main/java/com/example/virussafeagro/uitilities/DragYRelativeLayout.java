package com.example.virussafeagro.uitilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class DragYRelativeLayout extends RelativeLayout {
    private FragmentActivity fragmentActivity;
    private BottomNavigationViewEx bottomNavigationViewEx;

    private int mLastY;

    public void setFragmentActivityAndBottomNavigationViewEx(FragmentActivity fragmentActivity, BottomNavigationViewEx bottomNavigationViewEx) {
        this.fragmentActivity = fragmentActivity;
        this.bottomNavigationViewEx = bottomNavigationViewEx;
    }

    public DragYRelativeLayout(Context context) {
        this(context, null);
    }

    public DragYRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragYRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int lastX = 0, lastY = 0;
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN: // when touching
                // open virus check page
                this.bottomNavigationViewEx.setCurrentItem(MainActivity.INITIAL_PAGE_POSITION);
                FragmentOperator.replaceFragmentNoBackStack(fragmentActivity, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);

                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE: // when moving
                int offsetY = y - mLastY;
                if (offsetY < 0) { // deny slide down
                    layout(getLeft(), getTop() + offsetY,
                            getRight(), getBottom() + offsetY);
                }
                break;

            case MotionEvent.ACTION_UP: // when leaving
                MyAnimationBox.runSlideOutAnimationToTop(this, 500);
                new Handler().postDelayed(()->{
                    this.setVisibility(GONE);
                },600);
        }
        return true;
    }

    // example
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        int lastX = 0, lastY = 0;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN: // when touching
//                mLastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE: // when moving
//                int offsetX = x - mLastX;
//                int offsetY = y - mLastY;
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);
//                break;
//        }
//        return true;
//    }
}