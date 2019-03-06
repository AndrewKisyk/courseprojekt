package com.example.a123.courseproject;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

public class AuthorizationActivity extends AppCompatActivity {
        TextView link_forgot_password;
        Log_in log_in;
        Forgot_password forgot_password;
        android.support.v4.app.FragmentTransaction transaction;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_authorization);


            forgot_password = new Forgot_password();
            log_in =  new Log_in();

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.log, log_in);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

            link_forgot_password = (TextView)findViewById(R.id.link_forgotpassword);
            link_forgot_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.log, forgot_password)
                    .addToBackStack(null)
                    .commit();
                }
            });

        }

    }

