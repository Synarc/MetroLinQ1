package com.example.isaac.metrolinq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapterQueue extends RecyclerView.Adapter <AdapterQueue.QueueViewHolder> {



    private DatabaseReference mDB;
    private Context mContext;
    private List<QueueTimeName> mNameDB;
    private OnItemClickListener mListener;


    private final int NUM = 200;
    private int iterate = 0;
    private int iterate2 = 0;


    /*
    * try extending this constructor to take in a hash key from
     * user auth FB so it can pass to SelectDriverCarAtivity
     * for comparsion to display in QueueActivity or not
     *
     * */
    AdapterQueue(Context context, List<QueueTimeName> NameDB) {
        mContext = context;
        mNameDB = NameDB;
    }


    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.queue_item_list, viewGroup, false);
        return new QueueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final QueueViewHolder queueViewHolder, final int i) {
        final String DBqueue = mNameDB.get(i).getName();

        queueViewHolder.clientNames.setText(DBqueue);
        queueViewHolder.time.setText(mNameDB.get(i).getTime());
    }


    @Override
    public int getItemCount() {
        return mNameDB.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener{


        TextView clientNames;
        TextView time;



        public QueueViewHolder(@NonNull View itemView) {
            super(itemView);

            clientNames = itemView.findViewById(R.id.clientNameQueue);
            time = itemView.findViewById(R.id.timeQueue);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null){
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhateverClick(position);
                            return true;

                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }

                }
            }
            return false;
        }

        @Override
        public void onClick(View v) {

            if (mListener != null){
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

          menu.setHeaderTitle("Select Action");

            MenuItem doWhatever = menu.add(Menu.NONE,1,1,"Journey Info");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Cancel");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }
    }


    public interface OnItemClickListener{
        void onItemClick (int position);

        void onWhateverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }



}
