package com.acemen.android.wasterz.view.contribute;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Audrik ! on 22/03/2017.
 */

public class ContributeViewPager extends ViewPager implements Contribute.Pager {

    public ContributeViewPager(Context context) {
        super(context);
    }

    public ContributeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Doesn't allow swiping on screen
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Doesn't allow swiping on screen
        return false;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    @Override
    public void next(int position) {
        setCurrentItem(position);
    }
}
