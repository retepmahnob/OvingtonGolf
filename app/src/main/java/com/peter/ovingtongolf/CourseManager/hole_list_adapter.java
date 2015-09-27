package com.peter.ovingtongolf.CourseManager;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;

/**
 * Created by peter on 27/09/15.
 */
public class hole_list_adapter extends RecyclerView.Adapter<hole_list_adapter.holeViewHolder> {

    private LayoutInflater inflater;
    private Cursor mCursor;
    private Callbacks mCallbacks;
    ContentResolver databaseContext;
    private int selectedItem = 0;

    public interface Callbacks {

        public void onHoleSelected(String id);
    }
    public hole_list_adapter(Context context) {

        databaseContext = context.getContentResolver();
//        mCallbacks = c;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public holeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hole_row, parent, false);
        holeViewHolder holder = new holeViewHolder(view);
        Log.d("Golf", "holeViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(hole_list_adapter.holeViewHolder holder, int position) {
        holder.itemView.setSelected(selectedItem == position);
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){

                holeListItem course = new holeListItem();
                Log.d("Golf", "onB indViewHolder" + position);
                holder.par.setText(mCursor.getString(2));
                holder.number.setText(mCursor.getString(1));
                holder.icon.setImageResource(course.iconId);

            }
        }

    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        return 0;
    }

    public void swapCursor (Cursor cursor){

        mCursor = cursor;
        notifyItemRangeChanged(0, mCursor.getCount());
    }

    class holeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView number;
        ImageView icon;
        TextView par;
        String currentId;

        public holeViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.hole_image);
            number = (TextView) itemView.findViewById(R.id.hole_number);
            par = (TextView) itemView.findViewById(R.id.hole_par);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            notifyItemChanged(selectedItem);
            mCursor.moveToPosition(getPosition());
            currentId = mCursor.getString(0);
            selectedItem = getPosition();
            notifyItemChanged(selectedItem);
            if (mCallbacks != null)
                mCallbacks.onHoleSelected(currentId);

        }
    }
    public static class holeListItem {

        int iconId;
        int holeNumber;
        int holePar;

    }

}
