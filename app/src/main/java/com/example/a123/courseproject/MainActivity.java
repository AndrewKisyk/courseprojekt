package com.example.a123.courseproject;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    android.support.v4.app.FragmentTransaction transaction;
    ProfileFragment pf;
    private TextView name;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.name);


        pf=new ProfileFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.profile,pf);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
