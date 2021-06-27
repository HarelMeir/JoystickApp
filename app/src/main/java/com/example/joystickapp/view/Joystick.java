package com.example.joystickapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.lang.UCharacter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;
import java.util.jar.Attributes;

public class Joystick extends View {
    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    private float innerCircleX;
    private float innerCircleY;
    private float aileron;
    private float  elevator;
    public OnJoystickChange service;

    public Joystick(Context context, AttributeSet atts) {
        super(context,atts);
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.BLACK);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCircleX = getWidth() / 2.0f;
        innerCircleY = getHeight() / 2.0f;


    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle(
                getWidth() / 2.0f,
                getHeight() / 2.0f,
                300F,
                outerCirclePaint
        );
        canvas.drawCircle(
                getWidth() / 2.0f,
                getHeight() / 2.0f,
                100F,
                innerCirclePaint
        );
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if(event == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                calculateMove(event.getX(), event.getY());
            }
        }
        return true;
    }

    public void calculateMove(float x, float y) {
        double value = Math.sqrt(Math.pow((getHeight() / 2f) - x ,2)
                * Math.pow((getHeight() / 2f) - x ,2));
        if(value < 320f) {
            aileron = ((x - getWidth()) / 2.0f) / 300f;
            elevator = (y - (getHeight() / 2f)) / 300f;
            innerCircleX = x;
            innerCircleY = y;
            service.onChange(aileron, elevator);
        }
    }




}
