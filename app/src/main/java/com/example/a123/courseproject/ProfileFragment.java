package com.example.a123.courseproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProfileFragment extends android.support.v4.app.Fragment {

    TextView name;
    TextView email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);

        Intent intent = getActivity().getIntent();

            name.setText("Name: "+intent.getStringExtra("UserName"));
            email.setText("Email: "+intent.getStringExtra("Email"));
        return view;
    }




}
