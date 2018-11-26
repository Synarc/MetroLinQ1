package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.QueueTimeName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinetNameActivity extends AppCompatActivity {


    private static final String[] COUNTRIES = new String[]{
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
            "Buka", "Manus", "Kerema", "Alotau", "Jamaica", "Alabana", "Alwyn", "Alex" +
            ""
    };

   private  Button nameOk;
    AutoCompleteTextView editText;
    List<String> clientList;
    DatabaseReference clientNameDB, requestDB_name;
    private  boolean clientExist = false;

    long iterateNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinet_name);


        nameOk = findViewById(R.id.OK_name);
         editText = findViewById(R.id.actv);


         /// change this data base with the one you use to store all client names
         clientNameDB = FirebaseDatabase.getInstance().getReference("Clients");
         requestDB_name = FirebaseDatabase.getInstance().getReference("TestRequest");

         clientList = new ArrayList<>();





         clientNameDB.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 clientList.clear();

                 for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                         String name = postSnapshot.getValue().toString();


                         clientList.add(name);

                 }


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,clientList);
        editText.setAdapter(adapter);


        nameOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DROPDOWN", "onClick: "+ editText.getText().toString());

                Log.d("CLIENTLIST", "onClick: "+clientList.get(3));

                iterateNumber = 0;

                clientNameDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            if (postSnapshot.getValue().toString().equals(editText.getText().toString())) {
                                clientExist = true;
                                break;

                            }

                        }


                        if (!clientExist){
                            String uploadId = clientNameDB.push().getKey();
                            clientNameDB.child(uploadId).setValue(editText.getText().toString());

                            clientExist = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                requestDB_name.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){


                            // iterate Number++ on top because getchildrenCount starts at one
                            iterateNumber++;
                            if (iterateNumber == dataSnapshot.getChildrenCount()) {

                                requestDB_name.child(postSnapshot.getKey()).child("clientName").setValue(editText.getText().toString());

                                Intent intent = new Intent(ClinetNameActivity.this, MapsActivity.class);
                                startActivity(intent);
                            }
                        }






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
