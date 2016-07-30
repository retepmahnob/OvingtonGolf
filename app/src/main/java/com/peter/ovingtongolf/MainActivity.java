package com.peter.ovingtongolf;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



import com.peter.ovingtongolf.CourseManager.manageCourses;
import com.peter.ovingtongolf.PlayerManager.managePlayers;
import com.peter.ovingtongolf.utilities.BusAction;
import com.peter.ovingtongolf.utilities.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

public class MainActivity extends ActionBarActivity{//} implements NavigationDrawerFragment.ManageCurrentGameListener {

    private android.support.v7.widget.Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainappbar);

        BusProvider.getInstance().register(this);

        toolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.golf_view));
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.Setup(R.id.fragment_navigation_drawer, (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout), toolBar);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(this, "got it mojo", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.manage_courses){
            Intent intent = new Intent(this, manageCourses.class);
            startActivity(intent);
        }

        if (id == R.id.manage_players){
            Intent intent = new Intent(this, managePlayers.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void getMessage(BusAction.CreateGame g){
        Toast.makeText(this, "Create Game", Toast.LENGTH_LONG).show();
    }

/*
    @Override
    public void onManageCurrentGame(int Action) {
        if (Action == 0) {

        }
    }
*/
}
