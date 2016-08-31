package com.waveview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private final String TAG="MainActivity";
    private int startY, padding;
    private WaveViewHelper mWaveViewHelper;
    private float scale = 1f;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                padding = tempY - startY;
                Log.d(TAG, "onTouchEvent: padding = "+padding);
                //padding在0-100dp之间可以滑动
                if (padding > 0 && padding < dpToPx(100)) {
                    mWaveViewHelper.start();
                    scale = ((float) padding / dpToPx(600)) + 1f;
                    //背景图片横向放大
                    mImageView.setScaleX(scale);
                    mImageView.setPadding(mImageView.getPaddingLeft(), padding, mImageView.getPaddingRight(), mImageView.getPaddingBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                mWaveViewHelper.stopSlow();
                mImageView.setScaleX(1);
                mImageView.setPadding(mImageView.getPaddingLeft(), 0, mImageView.getPaddingRight(), mImageView.getPaddingBottom());
                break;
        }
        return super.onTouchEvent(event);
    }

    private void init() {
        WaveView waveView = (WaveView) findViewById(R.id.waveView);
        mWaveViewHelper = new WaveViewHelper(waveView);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  mWaveViewHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveViewHelper.stop();
    }

    private int dpToPx(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
