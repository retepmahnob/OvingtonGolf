package com.peter.ovingtongolf.CourseManager;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.ovingtongolf.CursorRecyclerAdapter;
import com.peter.ovingtongolf.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by peter on 2/02/15.
 */
public class courseListAdapter extends RecyclerView.Adapter<courseListAdapter.courseViewHolder> {
//        CursorRecyclerAdapter <courseListAdapter.courseViewHolder> {
//          RecyclerView.Adapter<courseListAdapter.courseViewHolder> {

    private LayoutInflater inflater;
    private Callbacks mCallbacks;
    private Cursor mCursor;
    public courseListAdapter(Context context, Callbacks c) {

        mCallbacks = (Callbacks) c;
        inflater= LayoutInflater.from(context);
    }

    public interface Callbacks {

        public void onItemSelected(String id);
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

        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){


                courseListItem course = new courseListItem();

                Log.d("Golf", "onB indViewHolder" + position);
                holder.title.setText(mCursor.getString(1));
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

    class courseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public courseViewHolder(View itemView) {
            super(itemView);

            title= (TextView) itemView.findViewById(R.id.course_name);
            icon = (ImageView) itemView.findViewById(R.id.course_image);
            icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onItemSelected("1");
        }
    }


}
