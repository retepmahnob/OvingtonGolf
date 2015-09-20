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
 * Created by peter on 23/02/15.
 */
public class tee_list_adapter extends RecyclerView.Adapter<tee_list_adapter.teeViewHolder> {

    private LayoutInflater inflater;
    private Callbacks mCallbacks;
    private Cursor mCursor;
    ContentResolver databaseContext;
    private int selectedItem = 0;
    public tee_list_adapter(Context context, Callbacks c) {

        databaseContext = context.getContentResolver();
        mCallbacks = c;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public teeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tee_row, parent, false);
        teeViewHolder holder=new teeViewHolder(view);
        Log.d("Golf", "holeViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(teeViewHolder holder, int position) {

        holder.itemView.setSelected(selectedItem == position);
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){

                teeListItem course = new teeListItem();
                Log.d("Golf", "onB indViewHolder" + position);
                holder.title.setText(mCursor.getString(1));
                holder.course.setText(mCursor.getString(0));
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

    public interface Callbacks {

        public void onHoleSelected(String id);
    }

    public void swapCursor (Cursor cursor){

        mCursor = cursor;
        notifyItemRangeChanged(0, mCursor.getCount());
    }

    class teeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;
        TextView course;
        String currentId;

        public teeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tee_color);
            icon = (ImageView) itemView.findViewById(R.id.hole_image);
            course = (TextView) itemView.findViewById(R.id.tee_course_id);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClick(v);
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    final View parentView = v;
                    popup.getMenuInflater().inflate(R.menu.menu_course, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete_course) {
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                databaseContext.delete(sqlcontractGolf.Tees.buildUri(), sqlcontractGolf.Tees._ID + "=?", new String[]{currentId});
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;
                                        }
                                    }
                                };
                                //title= (TextView) v.findViewById(R.id.course_name);
                                String confirm = "Are you sure you wish to delete " + title.getText();
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
                                builder.setMessage(confirm)
                                        .setPositiveButton("Yes", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener)
                                        .show();

                            }
                            return true;
                        }
                    });
                    popup.show();
                    return false;
                }

            });
        }

        @Override
        public void onClick(View v) {

            notifyItemChanged(selectedItem);
            mCursor.moveToPosition(getPosition());
            currentId = mCursor.getString(0);
            selectedItem = getPosition();
            notifyItemChanged(selectedItem);
            mCallbacks.onHoleSelected(currentId);

        }
    }

    public static class teeListItem {
        int iconId;

        String courseName;

    }

}
