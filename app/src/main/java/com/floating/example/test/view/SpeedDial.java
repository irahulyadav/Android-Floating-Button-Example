package com.floating.example.test.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.floating.example.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iprahul on 12/23/15.
 */
public class SpeedDial implements View.OnClickListener, ViewTreeObserver.OnPreDrawListener {
    private static final String TRANSLATION_Y = "translationY";
    private ViewGroup viewGroup;
    private boolean expanded = false;
    private FloatingActionButton fab;

    private HashMap<View, Float> viewMap = new HashMap<>();

    public SpeedDial(FloatingActionButton fab, ViewGroup viewGroup) {
        this.fab = fab;
        this.viewGroup = viewGroup;
        viewGroup.getViewTreeObserver().addOnPreDrawListener(this);
        fab.setOnClickListener(this);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getId() != fab.getId()) {
                viewMap.put(view, 0f);
            }
        }
    }


    @Override
    public void onClick(View v) {
        expanded = !expanded;
        if (expanded) {
            expandFab();
        } else {
            collapseFab();
        }
    }

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (View view : viewMap.keySet()) {
            animators.add(createCollapseAnimator(view, viewMap.get(view)));
        }
        animatorSet.playTogether(animators);
        animatorSet.start();
        animateFab();
    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (View view : viewMap.keySet()) {
            animators.add(createExpandAnimator(view, viewMap.get(view)));
        }
        animatorSet.playTogether(animators);
        animatorSet.start();
        animateFab();
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    @Override
    public boolean onPreDraw() {
        viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
        float offset = 0;
        for (View view : viewMap.keySet()) {
            offset = fab.getY() - view.getY();
            viewMap.put(view, offset);
            view.setTranslationY(offset);
        }
        return true;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
