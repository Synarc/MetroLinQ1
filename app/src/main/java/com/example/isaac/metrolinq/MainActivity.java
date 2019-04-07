package com.example.isaac.metrolinq;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.isaac.metrolinq.Fragments.FragmentComplete;
import com.example.isaac.metrolinq.Fragments.FragmentMap;
import com.example.isaac.metrolinq.Fragments.FragmentPending;
import com.example.isaac.metrolinq.Fragments.FragmentRequested;
import com.example.isaac.metrolinq.Fragments.ViewPagerAdapter;
import com.example.isaac.metrolinq.MapsClasses.MapCarTrackActivity;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_main);



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String [] mapOption = { "Car Track","Fare Calculator"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)  ;
//                builder.setCancelable(true);
//
//                builder.setSingleChoiceItems(mapOption, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                // open up map and get the cordinates of new points with new fares
//
//                                Toast.makeText(MainActivity.this, "open map for drivers", Toast.LENGTH_SHORT).show();
//
//                                Intent intent1 = new Intent(MainActivity.this, MapCarTrackActivity.class);
//                                startActivity(intent1);
//
//                                break;
//
//
//                            case 1:
//
//
//
//                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                                startActivity(intent);
//
//                                break;
//                        }
//                    }
//
//
//
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//
//
//
//            }
//        });

        tabLayout = findViewById(R.id.tab_id_layout);
        viewPager = findViewById(R.id.view_pager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.AddFragment(new FragmentRequested(), getString(R.string.Req));
        adapter.AddFragment(new FragmentPending(), getString(R.string.Pending));
        adapter.AddFragment(new FragmentComplete(), getString(R.string.complete));


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
