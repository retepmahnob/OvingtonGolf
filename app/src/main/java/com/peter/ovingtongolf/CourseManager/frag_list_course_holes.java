package com.peter.ovingtongolf.CourseManager;

import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;
import com.peter.ovingtongolf.utilities.sqlApplyButton;

/**
 * Created by peter on 18/02/15.
 */
public class frag_list_course_holes extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, tee_list_adapter.Callbacks, sqlApplyButton.OnSqlActionListener {

    private static final int URL_TEE_LOADER = 1;
    private static final int URL_HOLE_LOADER = 2;
    private RecyclerView recyclerTeeView;
    private RecyclerView recyclerHoleView;
    private LinearLayout addTeeGroup;
    private tee_list_adapter adapterTee ;
    private hole_list_adapter adapterHole;
    private EditText editColor;
    private EditText editSex;
    private EditText editSlope;

    private static final String[] PROJECTION = new String[] { "hole_tee_colour", "hole_number", "hole_par" };
    private OnHoleSelectedListener mListener;
    private String currentTeeId = "";
    private String currentCourseId = "";
    private String currentHoleId = "";

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
        getLoaderManager().initLoader(URL_HOLE_LOADER, null, this);

        recyclerTeeView = (RecyclerView) layout.findViewById(R.id.tee_lister);
        adapterTee = new tee_list_adapter(getActivity(), this);
        recyclerTeeView.setAdapter(adapterTee);
        recyclerTeeView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addTeeGroup = (LinearLayout) layout.findViewById(R.id.AddTeeGroup);
        sqlApplyButton button = (sqlApplyButton) layout.findViewById(R.id.SqlApplyTee);
        if (button != null){
            button.SetCurrentAction(sqlApplyButton.sqlButtonAction.baAdd);
            button.setSqlListener(this);
        }
        editColor = (EditText) layout.findViewById(R.id.AddTeeColor);
        editSex = (EditText) layout.findViewById(R.id.AddTeeSex);
        editSlope = (EditText) layout.findViewById(R.id.AddTeeColor);

        Log.d(this.getClass().getName(), "onCreate called ");

        recyclerHoleView  = (RecyclerView) layout.findViewById(R.id.hole_lister);
        adapterHole = new hole_list_adapter(getActivity());
        recyclerHoleView.setAdapter(adapterHole);
        recyclerHoleView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            CursorLoader cursorLoader = new CursorLoader(getActivity(), sqlcontractGolf.Tees.buildUri(), projection, sqlcontractGolf.Tees.TEE_COURSE_ID+"=?", new String[]{currentCourseId}, null);
            return cursorLoader;
        }
        else if (id == URL_HOLE_LOADER){
            String[] projection = {sqlcontractGolf.Holes._ID, sqlcontractGolf.Holes.HOLE_NUMBER, sqlcontractGolf.Holes.HOLE_PAR};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), sqlcontractGolf.Holes.buildUri(), projection, sqlcontractGolf.Holes.HOLE_TEE_ID+"=?", new String[]{currentTeeId}, null);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (loader.getId() == URL_TEE_LOADER) {

                if (data.moveToFirst()) {
                    do {
                        Log.d("Golf Tees", data.getString(0) + "->" + data.getString(1) + "->" + data.getString(2));
                    } while (data.moveToNext());
                }
                adapterTee.swapCursor(data);
            }
            else if (loader.getId() == URL_HOLE_LOADER) {

                if (data.moveToFirst()) {
                    do {
                        Log.d("Golf Holes", data.getString(0) + "->" + data.getString(1) + "->" + data.getString(2));
                    } while (data.moveToNext());
                }
                adapterHole.swapCursor(data);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onHoleSelected(String id) {
        mListener.onHoleSelected(id);
        currentTeeId = id;
        getLoaderManager().restartLoader(URL_HOLE_LOADER, null, this);
    }

    @Override
    public void OnActionRequested(sqlApplyButton button, sqlApplyButton.sqlButtonAction action, boolean isPressed) {
        if (isPressed == false) {
            if (action == sqlApplyButton.sqlButtonAction.baAdd) {
                ViewGroup.LayoutParams params = addTeeGroup.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                addTeeGroup.setLayoutParams(params);
                button.SetCurrentAction(sqlApplyButton.sqlButtonAction.baApply);
            }
            if (action == sqlApplyButton.sqlButtonAction.baApply) {
                ViewGroup.LayoutParams params = addTeeGroup.getLayoutParams();
                params.height = 0;
                addTeeGroup.setLayoutParams(params);
                AddNewTee();
                button.SetCurrentAction(sqlApplyButton.sqlButtonAction.baAdd);
            }
        }

    }

    private void AddNewTee (){
        ContentResolver databaseContext = getActivity().getContentResolver();
        if (databaseContext != null){

            ContentValues teeValues = new ContentValues();
            teeValues.put(sqlcontractGolf.Tees.TEE_COURSE_ID, currentCourseId);
            teeValues.put(sqlcontractGolf.Tees.TEE_COLOUR, editColor.getText().toString());
            teeValues.put(sqlcontractGolf.Tees.TEE_SEX, editSex.getText().toString());
            teeValues.put(sqlcontractGolf.Tees.TEE_SLOPE, editSlope.getText().toString());
            Uri row = databaseContext.insert(sqlcontractGolf.Tees.buildUri(), teeValues);

            String id = teeValues.getAsString(sqlcontractGolf.Tees._ID);
            ContentValues holeValues = new ContentValues();
            holeValues.put(sqlcontractGolf.Holes.HOLE_TEE_ID, id);
            for (int i=1 ; i<=18 ; i++){
                holeValues.put(sqlcontractGolf.Holes.HOLE_NUMBER, String.valueOf(i));
                holeValues.put(sqlcontractGolf.Holes.HOLE_PAR, 4);
                databaseContext.insert(sqlcontractGolf.Holes.buildUri(), holeValues);
                holeValues.remove(sqlcontractGolf.Holes._ID);
            }

            Log.d(this.getClass().getName(), "Add Tee " + row.toString() + "  Id=" + id);
        }
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
