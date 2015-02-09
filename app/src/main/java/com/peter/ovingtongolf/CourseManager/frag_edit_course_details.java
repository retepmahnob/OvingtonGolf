package com.peter.ovingtongolf.CourseManager;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

import java.util.UUID;

/**
 * Created by peter on 7/02/15.
 */
public class frag_edit_course_details extends Fragment {

    private String title;
    private int pageNo;
    private Button mButtonSave;

    public static frag_edit_course_details newInstance(int pageNo, String title){

        frag_edit_course_details courseDetails = new frag_edit_course_details();
        Bundle args = new Bundle();
        args.putInt("Page", pageNo);
        args.putString("Title", title);
        courseDetails.setArguments(args);
        return courseDetails;
    }
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        if (savedInstance != null){
            pageNo = savedInstance.getInt("Page");
            title = savedInstance.getString("Title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.frag_edit_course_details, container, false);

        mButtonSave =  (Button)view.findViewById(R.id.SaveCourse);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final UUID courseUUId = UUID.randomUUID();

                EditText textName = (EditText)getActivity().findViewById(R.id.EditTextName);
                EditText textAddress1 = (EditText)getActivity().findViewById(R.id.EditAddress1);
                ContentValues values = new ContentValues();
                values.put(sqlcontractGolf.Course.COURSE_ID, courseUUId.toString());
                values.put(sqlcontractGolf.Course.COURSE_NAME, textName.getText().toString());
                values.put(sqlcontractGolf.Course.COURSE_ADDR1, textAddress1.getText().toString());
                values.put(sqlcontractGolf.Course.COURSE_ADDR2, textAddress1.getText().toString());
                values.put(sqlcontractGolf.Course.COURSE_PHONE, textAddress1.getText().toString());
                values.put(sqlcontractGolf.Course.COURSE_EMAIL, textAddress1.getText().toString());
                values.put(sqlcontractGolf.Course.COURSE_RATING, 0);
                values.put(sqlcontractGolf.Course.COURSE_SLOPE, 0);
                getActivity().getContentResolver().insert(sqlcontractGolf.Course.buildUri(), values);
//                mListener.onCourseAdded(courseUUId);
            }
        });
        return view;

    }
}
