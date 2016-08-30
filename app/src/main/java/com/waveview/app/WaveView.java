package com.waveview.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by ddsc on 8/29/2016.
 */
public class WaveView extends ImageView {
    private String TAG = "MyView";
    private float marginLeftValue = 0;
    // shader containing repeated waves
    private BitmapShader mWaveShader;
    // paint to draw wave
    private Paint mViewPaint;
    private Matrix mShaderMatrix;
    private float[] waveY;
    private Context mContext;
    private float waveLevel = 0.1f;

    public WaveView(Context context) {
        super(context);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void setMarginLeftValue(float marginLeftValue) {
        this.marginLeftValue = marginLeftValue;
        Log.d(TAG, "setMarginLeftValue: " + marginLeftValue);
        invalidate();
    }

    void setWaveLevel(float waveLevel) {
        Log.d(TAG, "setWaveLevel: " + waveLevel);
        this.waveLevel = waveLevel;
        // invalidate();
    }

    float getWaveLevel() {
        return waveLevel;
    }

    float getMarginLeftValue() {
        return marginLeftValue;
    }

    private void init(Context context) {
        mContext = context;
        mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);
        mViewPaint.setShader(mWaveShader);
        mShaderMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint frontPaint = new Paint();
        frontPaint.setStrokeWidth(2);
        frontPaint.setAntiAlias(true);
        //   frontPaint.setColor(ActivityCompat.getColor(mContext, R.color.behind_wave));
        frontPaint.setColor(Color.RED);
        waveY = new float[getWidth() + 1];
        for (int beginX = 0; beginX <= getWidth(); beginX++) {
            double wx = beginX * 2 * Math.PI / getWidth();
            float beginY = (float) (getHeight() * 0.5 + getHeight() * 0.5 * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, getHeight(), frontPaint);
            Log.d(TAG, "beginX=" + beginX + ",y=" + beginY + ",height=" + getHeight());
            waveY[beginX] = beginY;
            Log.d(TAG, "onSizeChanged: " + beginY);
        }
        Paint behindPaint = new Paint();
        behindPaint.setAntiAlias(true);
        behindPaint.setStrokeWidth(2);
        behindPaint.setColor(Color.BLUE);
        for (int beginX = 0; beginX <= getWidth(); beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + getWidth() / 2) % getWidth()], beginX, getHeight(), behindPaint);
        }
        // use the bitamp to create the shader
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        if (mWaveShader != null) {
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }
            mShaderMatrix.setScale(1f, waveLevel, 0, getHeight() / 2);
            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix.postTranslate(
                    marginLeftValue * getWidth(),
                    0);
            mWaveShader.setLocalMatrix(mShaderMatrix);
            canvas.drawRect(0, 0, getWidth(),
                    getHeight(), mViewPaint);
        } else {
            mViewPaint.setShader(null);
        }
    }
}
