package com.peter.ovingtongolf.CourseManager;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;
import com.peter.ovingtongolf.utilities.sqlEditText;

import java.util.UUID;

/**
 * Created by peter on 7/02/15.
 */

public class frag_edit_course_details extends Fragment {

    public ContentValues mDataRecord;
    private Button mButtonSave;

    EditText textName;
    EditText textAddress1;
    EditText textAddress2;
    EditText textPhone;
    sqlEditText textEmail;

    public static frag_edit_course_details newInstance(CourseItem courseItem){

        frag_edit_course_details courseDetails = new frag_edit_course_details();

        return courseDetails;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        if (savedInstance != null){
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.frag_edit_course_details, container, false);

        textName = (EditText)view.findViewById(R.id.EditName);
        textAddress1 = (EditText)view.findViewById(R.id.EditAddress1);
        textAddress2 = (EditText)view.findViewById(R.id.EditAddress2);
        textPhone = (EditText)view.findViewById(R.id.EditPhone);
        textEmail = (sqlEditText)view.findViewById(R.id.EditEMail);

        mButtonSave =  (Button)view.findViewById(R.id.SaveCourse);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            final UUID courseUUId = UUID.randomUUID();

            ContentValues values = new ContentValues();
            values.put(sqlcontractGolf.Course.COURSE_ID, courseUUId.toString());
            values.put(sqlcontractGolf.Course.COURSE_NAME, textName.getText().toString());
            values.put(sqlcontractGolf.Course.COURSE_ADDR1, textAddress1.getText().toString());
            values.put(sqlcontractGolf.Course.COURSE_ADDR2, textAddress2.getText().toString());
            values.put(sqlcontractGolf.Course.COURSE_PHONE, textPhone.getText().toString());
            values.put(sqlcontractGolf.Course.COURSE_EMAIL, textEmail.getText().toString());
            getActivity().getContentResolver().insert(sqlcontractGolf.Course.buildUri(), values);
//                mListener.onCourseAdded(courseUUId);
            ContentValues tees = new ContentValues();
            tees.put(sqlcontractGolf.Tees.TEE_COURSE_ID, courseUUId.toString());
            tees.put(sqlcontractGolf.Tees.TEE_COLOUR, "White");
            tees.put(sqlcontractGolf.Tees.TEE_SLOPE, 116);
            tees.put(sqlcontractGolf.Tees.TEE_SEX, "Male");
            getActivity().getContentResolver().insert(sqlcontractGolf.Tees.buildUri(), tees);
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getClass().getName(), "onStart called ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getName(), "onResume called ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().getName(), "onSaveInstanceState called ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getName(), "onPause called ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getClass().getName(), "onStop called ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getName(), "onDestroy called ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(this.getClass().getName(), "onDetach called ");
    }

    public void setCurrentCourse(CourseItem currentCourse) {

        textName.setText(currentCourse.courseName);
        textAddress1.setText(currentCourse.courseAddress1);
        textAddress2.setText(currentCourse.courseAddress2);
        textPhone.setText(currentCourse.coursePhone);
        textEmail.setText(currentCourse.courseEMail);

    }
}
