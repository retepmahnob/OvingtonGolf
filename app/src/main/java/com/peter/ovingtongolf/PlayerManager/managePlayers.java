package com.peter.ovingtongolf.PlayerManager;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.peter.ovingtongolf.CourseManager.frag_edit_course;
import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.Data.PlayerItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

public class managePlayers extends ActionBarActivity implements frag_list_players.OnPlayerSelectedListener {

    private boolean mEditActive;
    private frag_edit_player playerFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_players);
        Toolbar toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.golf_view));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.editplayer_container) != null){
            mEditActive = true;
            playerFragment = new frag_edit_player();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.editplayer_container, playerFragment)
                    .commit();

        }
        else {
            mEditActive = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_players, menu);

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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlayerSelected(String id) {
        if (mEditActive){
            Cursor c = getContentResolver().query(sqlcontractGolf.Player.buildUri(),
                    null,
                    sqlcontractGolf.Player._ID+"=?",
                    new String[]{id},
                    null);

            if (c.moveToFirst()) {
                PlayerItem player = new PlayerItem();
                player.fromCursor(c);
                playerFragment.setCurrentPlayer(id, player);
            }
            c.close();
        }
    }
}
