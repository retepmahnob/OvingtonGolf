package com.peter.ovingtongolf.PlayerManager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.Data.PlayerItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;
import com.peter.ovingtongolf.utilities.sqlEditText;
import com.peter.ovingtongolf.utilities.sqlViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link frag_edit_player#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_edit_player extends Fragment {

    sqlViewModel sqlConnector = new sqlViewModel();
    sqlEditText textName;
    sqlEditText textAddress1;
    sqlEditText textAddress2;
    sqlEditText textPhone;
    sqlEditText textEmail;
    private PlayerItem currentPlayer = new PlayerItem();

/*
    private OnFragmentInteractionListener mListener;
*/

    public static frag_edit_player newInstance(String param1, String param2) {
        frag_edit_player fragment = new frag_edit_player();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void populateFields() {

        if (currentPlayer !=null) {

            textName.setSqlFieldData(currentPlayer.playerName);
            textAddress1.setSqlFieldData(currentPlayer.playerAddress1);
            textAddress2.setSqlFieldData(currentPlayer.playerAddress2);
            textPhone.setSqlFieldData(currentPlayer.playerPhone);
            textEmail.setSqlFieldData(currentPlayer.playerEMail);
        }
    }

    public void setCurrentPlayer(String id, PlayerItem currentPlayer){
        this.currentPlayer = currentPlayer;
        populateFields();

    }

    @Override
    public void onViewCreated(View view, Bundle saved){
        super.onViewCreated(view, saved);

        sqlConnector.SetContentResolver(getActivity().getContentResolver());

        sqlConnector.SetDBUniqueID(sqlcontractGolf.Player.buildUri(), sqlcontractGolf.Player._ID);
        sqlConnector.FindControls(view);

    }

    public frag_edit_player() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_edit_player, container, false);
        textName = (sqlEditText)view.findViewById(R.id.EditName);
        textAddress1 = (sqlEditText)view.findViewById(R.id.EditAddress1);
        textAddress2 = (sqlEditText)view.findViewById(R.id.EditAddress2);
        textPhone = (sqlEditText)view.findViewById(R.id.EditPhone);
        textEmail = (sqlEditText)view.findViewById(R.id.EditEMail);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
/*
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
/*
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}
