package com.peter.ovingtongolf.PlayerManager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.ovingtongolf.R;

/**
 * Created by peter on 28/09/15.
 */
public class playerListAdapter extends RecyclerView.Adapter<playerListAdapter.playerViewHolder> {

    private LayoutInflater inflater;
    private Cursor mCursor;
    ContentResolver databaseContext;
    private PlayerSelectedListener mPlayerSelectedListener = null;
    private int selectedItem = 0;

    public interface PlayerSelectedListener {

        public void onPlayerSelected(String id);
    }

    public playerListAdapter(Context context, PlayerSelectedListener listener){

        mPlayerSelectedListener = listener;
        databaseContext = context.getContentResolver();
        inflater= LayoutInflater.from(context);
    }

    @Override
    public playerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.player_row, parent, false);
        playerViewHolder holder = new playerViewHolder(view);
        Log.d("Golf", "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(playerViewHolder holder, int position) {
        holder.itemView.setSelected(selectedItem == position);
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){

                playerListItem course = new playerListItem();
                Log.d("Golf", "onB indViewHolder" + position);
                holder.name.setText(mCursor.getString(1));
                holder.icon.setImageResource(course.iconId);
                holder.id.setText(mCursor.getString(0));

            }
        }

    }

    public void swapCursor (Cursor cursor){

        mCursor = cursor;
        notifyItemRangeChanged(0, mCursor.getCount());
    }

    @Override
    public int getItemCount() {

        if (mCursor != null)
            return mCursor.getCount();
        return 0;
    }

    class playerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView icon;
        TextView id;
        String currentId;

        public playerViewHolder(View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.player_name);
            icon = (ImageView) itemView.findViewById(R.id.player_image);
            id = (TextView) itemView.findViewById(R.id.player_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedItem);
            mCursor.moveToPosition(getPosition());
            currentId = mCursor.getString(0);
            selectedItem = getPosition();
            notifyItemChanged(selectedItem);
            mPlayerSelectedListener.onPlayerSelected(currentId);
        }
    }

    public static class playerListItem {
        int iconId;

        String playerName;

    }

}
