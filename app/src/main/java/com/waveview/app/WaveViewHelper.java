package com.waveview.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by ddsc on 8/30/2016.
 */
public class WaveViewHelper {
    private final String TAG="WaveViewHelper";
    private ObjectAnimator mWaveShiftAnim;
    private WaveView mWaveView;

    public WaveViewHelper(WaveView waveView) {
        mWaveView = waveView;
        mWaveShiftAnim = ObjectAnimator.ofFloat(
                waveView, "marginLeftValue", 1f, 0f);
        mWaveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        mWaveShiftAnim.setDuration(800);
        mWaveShiftAnim.setInterpolator(new LinearInterpolator());
        //mWaveShiftAnim.start();
    }

    //开启动画
    public void start() {
        if (!mWaveShiftAnim.isRunning()) {
            mWaveShiftAnim.start();
            mWaveView.setWaveLevel(1f);//waveLevel的动画结束后会变成0，此处重设1
            mWaveView.setVisibility(View.VISIBLE);
        }
    }

    //立即结束
    public void stop() {
        if (mWaveShiftAnim.isRunning()) {
            mWaveView.setVisibility(View.GONE);
            mWaveShiftAnim.end();
        }
    }

    //缓慢动画结束
    public void stopSlow() {
        ObjectAnimator waveLevel = ObjectAnimator.ofFloat(
                mWaveView, "waveLevel", 1f, 0f);
        waveLevel.setDuration(200);
        waveLevel.setInterpolator(new LinearInterpolator());
        waveLevel.start();
        waveLevel.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG, "onAnimationEnd: ");
                mWaveView.setVisibility(View.GONE);
                animator.cancel();
                mWaveShiftAnim.cancel();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
