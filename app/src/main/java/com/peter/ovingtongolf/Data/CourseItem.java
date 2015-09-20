package com.peter.ovingtongolf.Data;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;

import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

/**
 * Created by peter on 15/03/15.
 */
public class CourseItem {

    public String courseId;
    public String courseGUID;
    public String courseName;
    public String courseAddress1;
    public String courseAddress2;
    public String coursePhone;
    public String courseEMail;
    public int courseRating;
    public int courseSlope;

    public void assign (CourseItem other)
    {
        if (other == null)
            return;

        courseId = other.courseId;
        courseGUID = other.courseGUID;
        courseName = other.courseName;
        courseAddress1 = other.courseAddress1;
        courseAddress2 = other.courseAddress2;
        coursePhone = other.coursePhone;
        courseEMail = other.courseEMail;
        courseRating = other.courseRating;
        courseSlope = other.courseSlope;
    }

    public void fromCursor (Cursor c){
        courseId = c.getString((c.getColumnIndex(sqlcontractGolf.Course._ID)));
        courseGUID = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ID)));
        courseName = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_NAME)));
        courseAddress1 = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ADDR1)));
        courseAddress2 = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ADDR2)));
        coursePhone = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_PHONE)));
        courseEMail = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_EMAIL)));
    }
    public void toBundle (Bundle outState){

        outState.putString(sqlcontractGolf.Course._ID, courseId);
        outState.putString(sqlcontractGolf.Course.COURSE_ID, courseGUID);
        outState.putString(sqlcontractGolf.Course.COURSE_NAME, courseName);
        outState.putString(sqlcontractGolf.Course.COURSE_ADDR1, courseAddress1);
        outState.putString(sqlcontractGolf.Course.COURSE_ADDR2, courseAddress2);
        outState.putString(sqlcontractGolf.Course.COURSE_PHONE, coursePhone);
        outState.putString(sqlcontractGolf.Course.COURSE_EMAIL, courseEMail);
    }
    public void fromBundle (Bundle inState){

        courseId = inState.getString(sqlcontractGolf.Course._ID);
        courseGUID = inState.getString(sqlcontractGolf.Course.COURSE_ID);
        courseName = inState.getString(sqlcontractGolf.Course.COURSE_NAME);
        courseAddress1 = inState.getString(sqlcontractGolf.Course.COURSE_ADDR1);
        courseAddress2 = inState.getString(sqlcontractGolf.Course.COURSE_ADDR2);
        coursePhone = inState.getString(sqlcontractGolf.Course.COURSE_PHONE);
        courseEMail = inState.getString(sqlcontractGolf.Course.COURSE_EMAIL);
    }

}
