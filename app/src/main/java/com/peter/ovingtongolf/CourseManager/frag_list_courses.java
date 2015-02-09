package com.peter.ovingtongolf.CourseManager;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_list_courses.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_list_courses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_list_courses extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, courseListAdapter.Callbacks{

    private RecyclerView recyclerView;
    private courseListAdapter adapter;

    private static final String[] PROJECTION = new String[] { "name", "name" };
    private static final int URL_LOADER = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_list_courses.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_list_courses newInstance(String param1, String param2) {
        frag_list_courses fragment = new frag_list_courses();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public frag_list_courses() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.frag_list_courses, container, false);

        getLoaderManager().initLoader(URL_LOADER, null, this);

        recyclerView = (RecyclerView) layout.findViewById(R.id.course_lister);
        adapter = new courseListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    public static List<courseListItem> getData(){

        List<courseListItem> data = new ArrayList<>();

        String[] titles = {"one potato", "two potato", "three potato", "four", "one potato", "two potato", "three potato", "four", "one potato", "two potato", "three potato", "four"};

        for (int i=0 ; i<100 ; i++){
            courseListItem c = new courseListItem();
            c.courseName = titles[i%titles.length];
            c.iconId = R.drawable.ic_launcher;
            data.add(c);
        }
        return data;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {sqlcontractGolf.Course._ID, sqlcontractGolf.Course.COURSE_ID, sqlcontractGolf.Course.COURSE_NAME, sqlcontractGolf.Course.COURSE_ADDR1};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), sqlcontractGolf.Course.buildUri(), projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    Log.d("Golf", data.getString(0) + "->"+data.getString(1) + "->"+data.getString(2));
                } while (data.moveToNext());
            }
        }
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemSelected(String id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}