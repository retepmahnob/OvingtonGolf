package com.peter.ovingtongolf.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
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

    public sqlEditText(Context context) {
        super(context);

        setListeners();
    }

    public sqlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListeners();
        getAttributes (context, attrs);
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

    public sqlEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setListeners();
        getAttributes (context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        originalText = text;
    }


    public void setListeners() {
        originalText = getText().toString();
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(this.getClass().getName(), "onFocusChange " + originalText);
            }
        });

        setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "OnClickListener");
            }
        });

    }
}
