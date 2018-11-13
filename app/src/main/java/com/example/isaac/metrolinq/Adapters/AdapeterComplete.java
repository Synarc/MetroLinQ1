package com.example.isaac.metrolinq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.DriverCar;
import com.example.isaac.metrolinq.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdapeterComplete extends RecyclerView.Adapter<AdapeterComplete.CompleteViewHolder> {

    private Context mContext;
    private List<DriverCar> mNameDB;
    private DatabaseReference mDB;
    private int iterate = 0;
    private OnItemClickListener mListener;

    public AdapeterComplete(Context context, List<DriverCar> nameDb) {

        mContext  = context;
        mNameDB = nameDb;
    }

    @NonNull
    @Override
    public CompleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_view_completed, viewGroup, false);
        return new CompleteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteViewHolder completeViewHolder, final int i) {

        final String nameComplete = mNameDB.get(i).getClient();
        final String carComplete = mNameDB.get(i).getCar();
        final String driverComplete = mNameDB.get(i).getDriver();

        completeViewHolder.completeName.setText(nameComplete);
        completeViewHolder.completeDriver.setText(driverComplete);
        completeViewHolder.completeCar.setText(carComplete);



    }

    @Override
    public int getItemCount() {
        return mNameDB.size();
    }

    public class CompleteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {

        TextView completeName, completeDriver, completeCar;


        public CompleteViewHolder(@NonNull View itemView) {
            super(itemView);


            completeName = itemView.findViewById(R.id.completedName);
            completeDriver = itemView.findViewById(R.id.completedDriver);
            completeCar = itemView.findViewById(R.id.completedCar);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

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
            MenuItem amend = menu.add(Menu.NONE,3,3,"Amend");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
            amend.setOnMenuItemClickListener(this);
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

                        case 3:
                            mListener.onAmendClick(position);
                    }

                }
            }
            return false;
        }

    }


    public interface OnItemClickListener{
        void onItemClick (int position);

        void onWhateverClick(int position);

        void onDeleteClick(int position);

        void onAmendClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
