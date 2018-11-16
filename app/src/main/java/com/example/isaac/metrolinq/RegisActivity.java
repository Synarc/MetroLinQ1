package com.example.isaac.metrolinq;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisActivity extends AppCompatActivity {


    EditText clientName;
    Button addClientBtn;
    DatabaseReference clientDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);


        clientName = findViewById(R.id.add_client_db);
        addClientBtn = findViewById(R.id.addButton);

        clientDB = FirebaseDatabase.getInstance().getReference("Clients");


        addClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                clientDB.child("clientName").setValue(clientName.getText().toString());
//                clientDB.push();

                String uploadId = clientDB.push().getKey();
                clientDB.child(uploadId).setValue(clientName.getText().toString());
                clientName.setText("");

            }
        });
    }
}
