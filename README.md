模仿蚂蚁聚宝的水波纹加载效果

核心代码:
 for (int beginX = 0; beginX <= getWidth(); beginX++) {
            double wx = beginX * 2 * Math.PI / getWidth();
            float beginY = (float) (getHeight() * 0.5 + getHeight() * 0.4 * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, getHeight(), frontPaint);
            Log.d(TAG, "beginX=" + beginX + ",y=" + beginY + ",height=" + getHeight());
            waveY[beginX] = beginY;
            Log.d(TAG, "onSizeChanged: " + beginY);
        }

通过正弦曲线计算y位置，然后从该坐标向控件最底部画线，然后通过属性动画实现水波效果。
