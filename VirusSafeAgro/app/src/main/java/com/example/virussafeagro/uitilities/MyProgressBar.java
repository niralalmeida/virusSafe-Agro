package com.example.virussafeagro.uitilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {
    String text;
    Paint mPaint;
    private int mWidth;
    private int mHeight;

    private int mViewHeight;

    public MyProgressBar(Context context) {
        super(context);
        initText();
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initText();
    }


    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    @Override
    public synchronized void setProgress(int progress) {
        setText(progress);
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX() + 40;
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    // initialization, drawer
    private void initText(){
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);

        this.mPaint.setTextSize(sp2px(20));
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setAntiAlias(true);
    }

    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }


    //set text content
    private void setText(int progress){
//        int i = (progress * 100)/this.getMax();
//        this.text = String.valueOf(i) + "%";

        this.text = progress + " / 5";
    }


}
