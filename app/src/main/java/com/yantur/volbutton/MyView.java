package com.yantur.volbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Artur on 4/12/2016.
 */
public class MyView extends View {

    private Paint paintCircle = new Paint();
    private Paint paintLine = new Paint();
    private Paint paintText = new Paint();
    private float centerX = 0;
    private float centerY = 0;
    private float degree = 360;


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 180, paintCircle);
        canvas.drawText("0", getWidth() / 2 - 10, getHeight() / 2 + 230, paintText);
        canvas.drawText("50", getWidth() / 2 - 250, getHeight() / 2, paintText);
        canvas.drawText("75", getWidth() / 2 - 25, getHeight() / 2 - 200, paintText);
        canvas.drawText("100", getWidth() / 2 + 200, getHeight() / 2, paintText);
        canvas.save();
        canvas.rotate(degree, getWidth() / 2, getHeight() / 2);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, (getHeight() / 2) + 120, paintLine);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                calculate(event);
                Log.d("ART", String.valueOf(event.getX()));
                Log.d("ART", String.valueOf(event.getY()));
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("ART", String.valueOf(degree));
                calculate(event);
                invalidate();
                return false;

        }
        return false;
    }

    private void calculate(MotionEvent event) {
        float x = event.getX() - centerX;
        float y = centerY - event.getY();
        float angle = (float) Math.toDegrees(Math.atan2(x, y)) + 180;
        if (touchInButton(event.getX(),event.getY()) && angle >= 0 && angle <= 270) {
            degree = angle;
        }

    }

    public void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyView,
                0, 0);
        try {
            paintCircle.setColor(typedArray.getColor(R.styleable.MyView_buttonColor,Color.GRAY));
            paintLine.setColor(typedArray.getColor(R.styleable.MyView_pointerColor, Color.BLACK));
        } finally {
            typedArray.recycle();
        }
        paintLine.setStrokeWidth(6f);
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(50);
    }
    public boolean touchInButton(float x, float y) {
        float deltaX = centerX - x;
        float deltaY = centerY - y;

        return 180 * 180 >= deltaX * deltaX + deltaY * deltaY;
    }
}
