package com.peter.ovingtongolf.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.ovingtongolf.R;

/**
 * Created by peter on 31/03/15.
 */

public class sqlEditText extends EditText{

    private CharSequence originalText;

    private String sqlField;
    private String sqlTable;
    private boolean hasFocus;
    private boolean hasDeleted;
    private Drawable drawDelete;
    private Drawable drawRevert;
    private OnFieldChangedListener mListener;
    private boolean mTriggerListeners = true;

    public void GetSqlConfiguration(StringBuilder sTable, StringBuilder sField) {

        sTable.append(sqlTable);
        sField.append(sqlField);

    }

    private enum clickAction {caDelete, caRevert};
    clickAction currentAction = clickAction.caDelete;

    public interface OnFieldChangedListener {

        public void OnFieldChanged(sqlEditText editField, String originalValue, String currentValue);
    }

    public sqlEditText(Context context) {
        super(context);
        init(context);
    }

    public sqlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init(context);
    }

    public sqlEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setListeners();

        drawDelete = getCompoundDrawables()[2];             // if we don't already have an image set on the right side of the edit box [0] = left, [1] = top [2]=right
        if (drawDelete == null) {
            drawDelete = getResources().getDrawable(android.R.drawable.ic_menu_delete);
        }
        if (drawRevert == null) {
            drawRevert = getResources().getDrawable(android.R.drawable.ic_menu_revert);
        }
        setClearIconVisible(true);
    }


    protected void setClearIconVisible(boolean visible) {

        boolean wasVisible = (getCompoundDrawables()[2] != null);

//        if (visible != wasVisible) {

            Drawable x = null;
            if (visible) {
                if (getText().length()>0) {
                    String current = getText().toString();
                    if (current.equals(originalText)) {
                        currentAction = clickAction.caDelete;
                        x = drawDelete;
                    }
                    else {
                        currentAction = clickAction.caRevert;
                        x = drawRevert;
                    }
                }
                else if (hasDeleted) {
                    currentAction = clickAction.caRevert;
                    x = drawRevert;
                }
            }
            setCompoundDrawablesWithIntrinsicBounds(null, null, x, null);
  //      }
    }

    private void getAttributes(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sqlEditText);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.sqlEditText_field:
                    sqlField = a.getString(attr);
                    break;
                case R.styleable.sqlEditText_table:
                    sqlTable = a.getString(attr);
                    break;
            }
        }
        a.recycle();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(this.getClass().getName(), "onDetachedFromWindow called ");
    }

    public void setSqlFieldData(CharSequence text) {
        mTriggerListeners = false;
        super.setText(text);
        originalText = text;
        mTriggerListeners = true;
    }

    public void onTextChanged()
    {
        if (mListener != null && mTriggerListeners)
            mListener.OnFieldChanged(this, getText().toString(), originalText.toString());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void setSqlListener(OnFieldChangedListener l){
        mListener = (OnFieldChangedListener) l;
    }

    public void setListeners() {
        originalText = getText().toString();

        //if text changes, take care of the button
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                sqlEditText.this.onTextChanged();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "OnClickListener");
                if (mListener != null) {
                 //   mListener.OnFieldChanged(sqlTable, sqlField);
                }
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                setClearIconVisible(hasFocus);
                Log.d(this.getClass().getName(), "onFocusChange " + hasFocus);
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (getCompoundDrawables()[2] != null) {
                    boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - drawDelete.getIntrinsicWidth());
                    if (tappedX) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (currentAction == clickAction.caDelete) {
                                setText("");
                                hasDeleted = true;
                                setClearIconVisible(true);
                            } else if (currentAction == clickAction.caRevert) {
                                setText(originalText);
                                hasDeleted = false;
                                setClearIconVisible(false);
                            }

                        }
                        return true;
                    }
                }
                return false;
            }

        });

    }
}