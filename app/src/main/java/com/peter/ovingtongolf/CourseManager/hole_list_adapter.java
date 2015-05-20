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

import com.peter.ovingtongolf.R;

/**
 * Created by peter on 23/02/15.
 */
public class hole_list_adapter  extends RecyclerView.Adapter<hole_list_adapter.holeViewHolder> {

    private LayoutInflater inflater;
    private Callbacks mCallbacks;
    private Cursor mCursor;

    public hole_list_adapter(Context context, Callbacks c) {

        mCallbacks = c;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public holeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_row, parent, false);
        holeViewHolder holder=new holeViewHolder(view);
        Log.d("Golf", "holeViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(holeViewHolder holder, int position) {

        if (mCursor != null) {
            if (mCursor.moveToPosition(position)){

                holeListItem course = new holeListItem();
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

    public interface Callbacks {

        public void onHoleSelected(String id);
    }

    public void swapCursor (Cursor cursor){

        mCursor = cursor;
        notifyItemRangeChanged(0, mCursor.getCount());
    }

    class holeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public holeViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.course_name);
            icon = (ImageView) itemView.findViewById(R.id.course_image);
//            icon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCursor.moveToPosition(getPosition());
            String sGuid = mCursor.getString(1);

            mCallbacks.onHoleSelected(sGuid);

        }
    }

    public static class holeListItem {
        int iconId;

        String courseName;

    }

}
