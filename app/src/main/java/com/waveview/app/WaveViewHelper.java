package com.waveview.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddsc on 8/30/2016.
 */
public class WaveViewHelper {
    private ObjectAnimator mWaveShiftAnim;
    private WaveView mWaveView;

    public WaveViewHelper(WaveView waveView) {
        List<Animator> animators = new ArrayList<>();
        mWaveView = waveView;
        mWaveShiftAnim = ObjectAnimator.ofFloat(
                waveView, "marginLeftValue", 0f, 1f);
        mWaveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        mWaveShiftAnim.setDuration(1000);
        mWaveShiftAnim.setInterpolator(new LinearInterpolator());
        //mWaveShiftAnim.start();
    }

    public void start() {
        if (!mWaveShiftAnim.isRunning()) {
            mWaveShiftAnim.start();
            mWaveView.setVisibility(View.VISIBLE);
        }
    }

    public void stop() {
        if (mWaveShiftAnim.isRunning()) {
            mWaveView.setVisibility(View.GONE);
            mWaveShiftAnim.end();
        }
    }

    public void stopSlow() {
        ObjectAnimator waveLevel = ObjectAnimator.ofFloat(
                mWaveView, "waveLevel", 1f, 0f);
        waveLevel.setRepeatCount(ValueAnimator.INFINITE);
        waveLevel.setDuration(1000);
        waveLevel.setInterpolator(new LinearInterpolator());
        waveLevel.start();
        waveLevel.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                mWaveView.setVisibility(View.GONE);
                animator.end();
                mWaveShiftAnim.end();
            }
        });
    }
}
