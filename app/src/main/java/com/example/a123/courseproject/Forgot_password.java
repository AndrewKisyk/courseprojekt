package com.example.a123.courseproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Forgot_password extends android.support.v4.app.Fragment {
    EditText _emailText;
    Log_in log_in;
    Button sendpassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ((TextView)getActivity().findViewById(R.id.link_forgotpassword)).setText("");
        log_in = new Log_in();

        _emailText = view.findViewById(R.id.input_email);
        sendpassword = view.findViewById(R.id.btn_sendpassword);
        sendpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void forgotPassword(){
        if(validate() == true){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.log, log_in, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
    private boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();

        boolean trust = false;
        for(int i=0; i!=email.length(); i++){
            if(email.charAt(i) == '@') {
                trust = true;
            }
        }
        if (email.isEmpty() || trust==false ) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        return valid;
    }

}
