package com.peter.ovingtongolf.CourseManager;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;
import com.peter.ovingtongolf.utilities.sqlApplyButton;
import com.peter.ovingtongolf.utilities.sqlEditText;
import com.peter.ovingtongolf.utilities.sqlViewModel;

import java.util.UUID;

/**
 * Created by peter on 7/02/15.
 */

public class frag_edit_course_details extends Fragment {

    public ContentValues mDataRecord;
    private Button mButtonSave;
    sqlViewModel sqlConnector = new sqlViewModel();
    sqlEditText textName;
    sqlEditText textAddress1;
    sqlEditText textAddress2;
    sqlEditText textPhone;
    sqlEditText textEmail;
    CourseItem CurrentCourse = new CourseItem();

    public static frag_edit_course_details newInstance(CourseItem courseItem){

        frag_edit_course_details courseDetails = new frag_edit_course_details();
        courseDetails.CurrentCourse.assign(courseItem);
        return courseDetails;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        if (savedInstance != null){
        }
    }
    @Override
    public void onViewCreated(View view, Bundle saved){
        super.onViewCreated(view, saved);

        sqlConnector.SetContentResolver(getActivity().getContentResolver());

        sqlConnector.SetDBUniqueID(sqlcontractGolf.Course.buildUri(), sqlcontractGolf.Course.COURSE_ID);
        sqlConnector.FindControls(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.frag_edit_course_details, container, false);
        textName = (sqlEditText)view.findViewById(R.id.EditName);
        textAddress1 = (sqlEditText)view.findViewById(R.id.EditAddress1);
        textAddress2 = (sqlEditText)view.findViewById(R.id.EditAddress2);
        textPhone = (sqlEditText)view.findViewById(R.id.EditPhone);
        textEmail = (sqlEditText)view.findViewById(R.id.EditEMail);

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
        populateFields();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CurrentCourse.toBundle(outState);
        Log.d(this.getClass().getName(), "onSaveInstanceState called ");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            CurrentCourse.fromBundle(savedInstanceState);
        }
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
    public void populateFields() {

        if (CurrentCourse !=null) {

            textName.setSqlFieldData(CurrentCourse.courseName);
            textAddress1.setSqlFieldData(CurrentCourse.courseAddress1);
            textAddress2.setSqlFieldData(CurrentCourse.courseAddress2);
            textPhone.setSqlFieldData(CurrentCourse.coursePhone);
            textEmail.setSqlFieldData(CurrentCourse.courseEMail);
        }
    }
    public void setCurrentCourse(String id, CourseItem currentCourse) {
        CurrentCourse.assign(currentCourse);
        populateFields();
        sqlConnector.OnCurrentRecordChanged(id);
    }
}
