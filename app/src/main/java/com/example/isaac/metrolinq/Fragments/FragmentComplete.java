package com.example.isaac.metrolinq.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaac.metrolinq.Adapters.AdapterAllComplete;
import com.example.isaac.metrolinq.Adapters.AdapterQueue;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.DriverCar;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.QueueTimeName;
import com.example.isaac.metrolinq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentComplete extends Fragment  {


    View v;
    private RecyclerView recyclerView;
    private List<DriverCar> listComplete;
    private DatabaseReference clientNameDB, mDB;
    private AdapterAllComplete adapter;

    public FragmentComplete() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.complete_fragment,container, false);

        recyclerView = v.findViewById(R.id.recyclerViewComplete);

        adapter  = new AdapterAllComplete(getContext(),listComplete);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerView.setAdapter(adapter);

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listComplete = new ArrayList<>();


        clientNameDB = FirebaseDatabase.getInstance().getReference("TestCompleteJourney");
        mDB = FirebaseDatabase.getInstance().getReference("TestJourney");

        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComplete.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){


                    DriverCar name = new DriverCar(
                            postSnapshot.child("driver").getValue().toString(),
                            postSnapshot.child("plateNumber").getValue().toString(),
                            postSnapshot.child("clientName").getValue().toString());
                    listComplete.add(name);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
