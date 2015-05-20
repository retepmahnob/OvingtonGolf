package com.peter.ovingtongolf.Data;

import android.database.Cursor;
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

    public void fromCursor (Cursor c){
        courseId = c.getString((c.getColumnIndex(sqlcontractGolf.Course._ID)));
        courseGUID = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ID)));
        courseName = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_NAME)));
        courseAddress1 = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ADDR1)));
        courseAddress2 = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_ADDR2)));
        coursePhone = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_PHONE)));
        courseEMail = c.getString((c.getColumnIndex(sqlcontractGolf.Course.COURSE_EMAIL)));
    }


}
