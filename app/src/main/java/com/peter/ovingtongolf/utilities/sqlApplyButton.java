package com.peter.ovingtongolf.utilities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.peter.ovingtongolf.R;

/**
 * Created by peter on 3/08/15.
 */
public class sqlApplyButton extends ImageView {

    public enum sqlButtonAction {baDelete, baRevert, baAdd, baApply};

    public sqlButtonAction currentAction = sqlButtonAction.baAdd;

    private static final int PRESSED_COLOR_LIGHTUP = 255 / 25;
    private static final int PRESSED_RING_ALPHA = 75;
    private static final int DEFAULT_PRESSED_RING_WIDTH_DIP = 4;
    private static final int ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime;

    private int centerY;
    private int centerX;
    private int outerRadius;
    private int pressedRingRadius;

    private Drawable drawDelete;
    private Drawable drawRevert;
    private Drawable drawAdd;
    private Drawable drawApply;

    private Paint circlePaint;
    private Paint focusPaint;

    private float animationProgress;

    private int pressedRingWidth;
    private int defaultColor = Color.BLACK;
    private int pressedColor;
    private ObjectAnimator pressedAnimator;

    public sqlApplyButton(Context context) {
        super(context);
        init(context, null);
    }

    public sqlApplyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public sqlApplyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void SetCurrentAction (sqlButtonAction newAction) {

        switch (newAction) {
            case baAdd:
                setImageDrawable(drawAdd);
                break;
            case baApply:
                setImageDrawable(drawApply);
                break;
            case baDelete:
                setImageDrawable(drawDelete);
                break;
            case baRevert:
                setImageDrawable(drawRevert);
                break;

        }
        currentAction = newAction;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);

        if (circlePaint != null) {
            circlePaint.setColor(pressed ? pressedColor : defaultColor);
        }

        if (pressed) {
            showPressedRing();
        } else {
            hidePressedRing();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, pressedRingRadius + animationProgress, focusPaint);
        canvas.drawCircle(centerX, centerY, outerRadius - pressedRingWidth, circlePaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        outerRadius = Math.min(w, h) / 2;
        pressedRingRadius = outerRadius - pressedRingWidth - pressedRingWidth / 2;
    }

    public float getAnimationProgress() {
        return animationProgress;
    }

    public void setAnimationProgress(float animationProgress) {
        this.animationProgress = animationProgress;
        this.invalidate();
    }

    public void setColor(int color) {
        this.defaultColor = color;
        this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);

        circlePaint.setColor(defaultColor);
        focusPaint.setColor(defaultColor);
        focusPaint.setAlpha(PRESSED_RING_ALPHA);

        this.invalidate();
    }

    private void hidePressedRing() {
        pressedAnimator.setFloatValues(pressedRingWidth, 0f);
        pressedAnimator.start();
    }

    private void showPressedRing() {
        pressedAnimator.setFloatValues(animationProgress, pressedRingWidth);
        pressedAnimator.start();
    }

    private void init(Context context, AttributeSet attrs) {
        this.setFocusable(true);
        this.setScaleType(ScaleType.CENTER_INSIDE);
        setClickable(true);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);

        focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        focusPaint.setStyle(Paint.Style.STROKE);

        drawDelete = getResources().getDrawable(R.drawable.ic_action_delete);
        drawRevert = getResources().getDrawable(R.drawable.ic_action_revert);
        drawAdd = getResources().getDrawable(R.drawable.ic_action_add);
        drawApply = getResources().getDrawable(R.drawable.ic_action_apply);

        pressedRingWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PRESSED_RING_WIDTH_DIP, getResources()
                .getDisplayMetrics());

        int color = Color.BLACK;
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sqlApplyButton);
            color = a.getColor(R.styleable.sqlApplyButton_cb_color, color);
            pressedRingWidth = (int) a.getDimension(R.styleable.sqlApplyButton_cb_pressedRingWidth, pressedRingWidth);
            a.recycle();
        }
        setImageDrawable(drawAdd);
        setColor(color);

        focusPaint.setStrokeWidth(pressedRingWidth);
        final int pressedAnimationTime = getResources().getInteger(ANIMATION_TIME_ID);
        pressedAnimator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 0f);
        pressedAnimator.setDuration(pressedAnimationTime);
    }

    private int getHighlightColor(int color, int amount) {
        return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
                Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount));
    }
    
}
