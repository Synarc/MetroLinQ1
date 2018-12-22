package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.isaac.metrolinq.Fragments.FragmentComplete;
import com.example.isaac.metrolinq.Fragments.FragmentMap;
import com.example.isaac.metrolinq.Fragments.FragmentPending;
import com.example.isaac.metrolinq.Fragments.FragmentRequested;
import com.example.isaac.metrolinq.Fragments.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        tabLayout = findViewById(R.id.tab_id_layout);
        viewPager = findViewById(R.id.view_pager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.AddFragment(new FragmentRequested(), "Requested");
        adapter.AddFragment(new FragmentPending(), "Pending");
        adapter.AddFragment(new FragmentComplete(), "Complete");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setElevation(0);



    }
}
