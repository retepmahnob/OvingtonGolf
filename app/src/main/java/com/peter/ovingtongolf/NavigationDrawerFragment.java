package com.peter.ovingtongolf;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.peter.ovingtongolf.navigationDrawer.NavDrawerItem;
import com.peter.ovingtongolf.navigationDrawer.NavDrawerListAdapter;
import com.peter.ovingtongolf.utilities.BusAction;
import com.peter.ovingtongolf.utilities.BusProvider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILENAME="golfpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mContainerView;
    private boolean mUserSeenDrawer;
    private boolean mFromSavedInstanceState;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ManageCurrentGameListener mListener;

    public interface ManageCurrentGameListener {
        public void onManageCurrentGame(int Action);
    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserSeenDrawer = Boolean.getBoolean(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false" ));
        if (savedInstanceState != null){
            mFromSavedInstanceState = true;
        }

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
/*
        try {
            mListener = (ManageCurrentGameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
*/
        Log.d(this.getClass().getName(), "onAttach called ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navagation_drawer, container, false);
    }


    public void Setup(int fragment_navigation_drawer, DrawerLayout layout, final Toolbar bar) {

        mContainerView = getActivity().findViewById(fragment_navigation_drawer);
        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, bar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.8)
                    bar.setAlpha(1-slideOffset);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserSeenDrawer){
                    mUserSeenDrawer=true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserSeenDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerList = (ListView) getActivity().findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));

        navMenuIcons.recycle();
        adapter = new NavDrawerListAdapter(getActivity(), navDrawerItems);
        mDrawerList.setAdapter(adapter);


        if (mUserSeenDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(mContainerView);
        }

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BusAction.CreateGame b = new BusAction.CreateGame();
                BusProvider.getInstance().post(b);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    public void saveToPreferences(Context context, String prefName, String prefValue){

        SharedPreferences sharedPrefs=context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPrefs.edit();
        editor.putString(prefName, prefValue);
        editor.apply();

    }
    public String readFromPreferences(Context context, String prefName, String defValue){

        SharedPreferences sharedPrefs=context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        return sharedPrefs.getString(prefName, defValue);
    }


    public class DoSomeShit{
        public String theShit;
    }
}
