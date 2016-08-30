package com.waveview.app;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int startY, padding, paddingTop;
    WaveViewHelper mWaveViewHelper;
    private float scale = 1f;
    ImageView mImageView;
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
                if (padding > 0 && padding < 300) {
                    mWaveViewHelper.start();
                    scale = (padding / 800f) + 1f;
                    mImageView.setScaleX(scale);
                    mImageView.setPadding(mImageView.getPaddingLeft(), padding, mImageView.getPaddingRight(), mImageView.getPaddingBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                mWaveViewHelper.stopSlow();
                mImageView.setScaleX(1);
                mImageView.setPadding(mImageView.getPaddingLeft(), paddingTop, mImageView.getPaddingRight(), mImageView.getPaddingBottom());
                break;
        }
        return super.onTouchEvent(event);
    }
    private void init() {
        WaveView waveView=(WaveView)findViewById(R.id.waveView);
        mWaveViewHelper=new WaveViewHelper(waveView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
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
}
