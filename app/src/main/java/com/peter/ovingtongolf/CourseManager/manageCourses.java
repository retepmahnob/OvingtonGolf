package com.peter.ovingtongolf.CourseManager;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.peter.ovingtongolf.R;


public class manageCourses extends ActionBarActivity implements frag_list_courses.OnCourseSelectedListener {

    private boolean mEditActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        Toolbar toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.editcourse_detail_container) != null){
            mEditActive = true;
//            frag_edit_course_details fragment = new frag_edit_course_details();
            frag_edit_course fragment = new frag_edit_course();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.editcourse_detail_container, fragment)
                    .commit();

        }
        else {
            mEditActive = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCourseSelected(String id) {
        Log.d("Golf", "Activity Selected course Guid:" + id);
    }
}
