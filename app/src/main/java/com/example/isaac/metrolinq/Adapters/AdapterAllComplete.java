package com.example.isaac.metrolinq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.DriverCar;
import com.example.isaac.metrolinq.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdapterAllComplete extends RecyclerView.Adapter<AdapterAllComplete.AllCompleteViewHolder> {

    private Context mContext;
    private List<DriverCar> mNameDB;
    private DatabaseReference mDB;
    private int iterate = 0;


    public AdapterAllComplete(Context context, List<DriverCar> nameDb) {

        mContext  = context;
        mNameDB = nameDb;
    }

    @NonNull
    @Override
    public AllCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.all_complete, viewGroup, false);
        return new AllCompleteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCompleteViewHolder completeViewHolder, final int i) {

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

    public class AllCompleteViewHolder extends RecyclerView.ViewHolder  {

        TextView completeName, completeDriver, completeCar;


        public AllCompleteViewHolder(@NonNull View itemView) {
            super(itemView);


            completeName = itemView.findViewById(R.id.completedName1);
            completeDriver = itemView.findViewById(R.id.completedDriver1);
            completeCar = itemView.findViewById(R.id.completedCar1);



        }

    }
}
