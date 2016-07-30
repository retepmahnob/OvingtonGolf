package com.peter.ovingtongolf.PlayerManager;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

public class frag_list_players extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, playerListAdapter.PlayerSelectedListener{


    public interface OnPlayerSelectedListener {

        public void onPlayerSelected(String id);
    }


    private RecyclerView recyclerView;
    private playerListAdapter adapter;
    private OnPlayerSelectedListener mListener;
    private static final int URL_PLAYER_LOADER = 10;


    public static frag_list_players newInstance() {
        frag_list_players fragment = new frag_list_players();
        return fragment;
    }

    public frag_list_players() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.frag_list_players, container, false);
        getLoaderManager().initLoader(URL_PLAYER_LOADER, null, this);
        recyclerView = (RecyclerView) layout.findViewById(R.id.player_lister);
        adapter = new playerListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(recyclerView);
        return layout;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlayerSelectedListener) activity;
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
        String[] projection = {sqlcontractGolf.Player._ID, sqlcontractGolf.Player.PLAYER_NAME, sqlcontractGolf.Player.PLAYER_HANDICAP};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), sqlcontractGolf.Player.buildUri(), projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onPlayerSelected(String id) {

        if (mListener != null)
            mListener.onPlayerSelected(id);

    }
}
