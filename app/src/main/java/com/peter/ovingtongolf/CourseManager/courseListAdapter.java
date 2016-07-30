package com.peter.ovingtongolf.CourseManager;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
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
 * Created by peter on 2/02/15.
 */
public class courseListAdapter extends RecyclerView.Adapter<courseListAdapter.courseViewHolder> {

    private LayoutInflater inflater;
    private Callbacks mCallbacks;
    private Cursor mCursor;
    ContentResolver databaseContext;
    private int highLight;
    private int selectedItem = 0;
    public courseListAdapter(Context context, Callbacks c) {

        databaseContext = context.getContentResolver();
        highLight = context.getResources().getColor(R.color.icons);
        mCallbacks = c;
        inflater= LayoutInflater.from(context);
    }

    public interface Callbacks {

        public void onCourseSelected(String id);
    }

    public void swapCursor (Cursor cursor){

        mCursor = cursor;
        notifyItemRangeChanged(0, mCursor.getCount());
    }

    @Override
    public courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_row, parent, false);
        courseViewHolder holder=new courseViewHolder(view);
        Log.d("Golf", "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(courseViewHolder holder, int position) {

        holder.itemView.setSelected(selectedItem == position);
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){

                courseListItem course = new courseListItem();
                Log.d("Golf", "onB indViewHolder" + position);
                holder.title.setText(mCursor.getString(2));
                holder.icon.setImageResource(course.iconId);
                holder.id.setText(mCursor.getString(0));

            }
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        return 0;
    }

    class courseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;
        TextView id;
        String currentId;
        public courseViewHolder(View itemView) {
            super(itemView);

            title= (TextView) itemView.findViewById(R.id.course_name);
            icon = (ImageView) itemView.findViewById(R.id.course_image);
            id = (TextView) itemView.findViewById(R.id.course_id);

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
                            if (item.getItemId() == R.id.delete_course)
                            {
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                databaseContext.delete(sqlcontractGolf.Course.buildUri(), sqlcontractGolf.Course._ID + "=?", new String[]{currentId});
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;
                                        }
                                    }
                                };
                                //title= (TextView) v.findViewById(R.id.course_name);
                                String confirm  = "Are you sure you wish to delete " + title.getText();
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
            mCallbacks.onCourseSelected(currentId);
        }
    }

    public static class courseListItem {
        int iconId;

        String courseName;

    }

}
