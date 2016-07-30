package com.peter.ovingtongolf.Data;

import android.database.Cursor;
import android.os.Bundle;

import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

/**
 * Created by peter on 30/09/15.
 */
public class PlayerItem {
    public String playerId;
    public String playerName;
    public String playerAddress1;
    public String playerAddress2;
    public String playerPhone;
    public String playerEMail;
    public float playerHandicap;


    public void assign (PlayerItem other)
    {
        if (other == null)
            return;

        playerId = other.playerId;
        playerName = other.playerName;
        playerAddress1 = other.playerAddress1;
        playerAddress2 = other.playerAddress2;
        playerPhone = other.playerPhone;
        playerEMail = other.playerEMail;
        playerHandicap = other.playerHandicap;
    }

    public void fromCursor (Cursor c){
        playerId = c.getString((c.getColumnIndex(sqlcontractGolf.Player._ID)));
        playerName = c.getString((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_NAME)));
        playerAddress1 = c.getString((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_ADDR1)));
        playerAddress2 = c.getString((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_ADDR2)));
        playerPhone = c.getString((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_PHONE)));
        playerEMail = c.getString((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_EMAIL)));
        playerHandicap = c.getFloat((c.getColumnIndex(sqlcontractGolf.Player.PLAYER_HANDICAP)));
    }
    public void toBundle (Bundle outState){

        outState.putString(sqlcontractGolf.Player._ID, playerId);
        outState.putString(sqlcontractGolf.Player.PLAYER_NAME, playerName);
        outState.putString(sqlcontractGolf.Player.PLAYER_ADDR1, playerAddress1);
        outState.putString(sqlcontractGolf.Player.PLAYER_ADDR2, playerAddress2);
        outState.putString(sqlcontractGolf.Player.PLAYER_PHONE, playerPhone);
        outState.putFloat(sqlcontractGolf.Player.PLAYER_HANDICAP, playerHandicap);
    }
    public void fromBundle (Bundle inState){

        playerId = inState.getString(sqlcontractGolf.Player._ID);
        playerName = inState.getString(sqlcontractGolf.Player.PLAYER_NAME);
        playerAddress1 = inState.getString(sqlcontractGolf.Player.PLAYER_ADDR1);
        playerAddress2 = inState.getString(sqlcontractGolf.Player.PLAYER_ADDR2);
        playerPhone = inState.getString(sqlcontractGolf.Player.PLAYER_PHONE);
        playerHandicap = inState.getFloat(sqlcontractGolf.Player.PLAYER_HANDICAP);
    }
}
