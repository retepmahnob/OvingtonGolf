package com.peter.ovingtongolf.CourseManager;

import android.app.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

/**
 * Created by peter on 18/02/15.
 */
public class frag_list_course_holes extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, tee_list_adapter.Callbacks {

    private static final int URL_TEE_LOADER = 0;
    private RecyclerView recyclerTeeView;
    private tee_list_adapter adapterTee ;

    private static final String[] PROJECTION = new String[] { "hole_tee_colour", "hole_number", "hole_par" };
    private OnHoleSelectedListener mListener;
    private String currentTeeId;
    private String currentCourseId = "";

    public static frag_list_course_holes newInstance(CourseItem courseItem){

        frag_list_course_holes courseHoles = new frag_list_course_holes();
        if (courseItem!=null)
            courseHoles.currentCourseId = courseItem.courseId;
        return courseHoles;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.frag_list_holes, container, false);

        getLoaderManager().initLoader(URL_TEE_LOADER, null, this);

        recyclerTeeView = (RecyclerView) layout.findViewById(R.id.tee_lister);
        adapterTee = new tee_list_adapter(getActivity(), this);
        recyclerTeeView.setAdapter(adapterTee);
        recyclerTeeView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(this.getClass().getName(), "onCreate called ");
        return layout;
    }

    public void SetCurrentCourseId (String courseId){
        currentCourseId = courseId;
        getLoaderManager().restartLoader(URL_TEE_LOADER, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHoleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.d(this.getClass().getName(), "onAttach called ");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(this.getClass().getName(), "onDetach called ");

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == URL_TEE_LOADER){
            String[] projection = {sqlcontractGolf.Tees._ID, sqlcontractGolf.Tees.TEE_COLOUR, sqlcontractGolf.Tees.TEE_SEX};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), sqlcontractGolf.Tees.buildUri(), projection, sqlcontractGolf.Tees.COURSE_ID+"=?", new String[]{currentCourseId}, null);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    Log.d("Golf Holes", data.getString(0) + "->" + data.getString(1) + "->" + data.getString(2));
                } while (data.moveToNext());
            }
        }
        adapterTee.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onHoleSelected(String id) {
        mListener.onHoleSelected(id);
    }

    public interface OnHoleSelectedListener {
        public void onHoleSelected(String id);
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
}
