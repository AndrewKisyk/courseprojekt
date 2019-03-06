package com.example.a123.courseproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123.courseproject.model.User;
import com.example.a123.courseproject.sql.DatabaseHelper;


public class Sign_up extends android.support.v4.app.Fragment {

    TextView login_link;
    Log_in log_in;
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupbutton;
    Intent intent;
    private User user;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        log_in = new Log_in();

        databaseHelper = new DatabaseHelper(getActivity());
        user = new User();

        _nameText = (EditText) view.findViewById(R.id.input_name);
        _emailText = (EditText) view.findViewById(R.id.input_email);
        _passwordText = (EditText) view.findViewById(R.id.input_password);
        _signupbutton = (Button) view.findViewById(R.id.btn_sign);
        intent = new Intent(getActivity(), MainActivity.class);


        ((TextView)getActivity().findViewById(R.id.link_forgotpassword)).setText("Forgot password?");
        login_link =(TextView)view.findViewById(R.id.link_login);
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.log, log_in, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        _signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        return view;
    }
    public void signup() {
        Log.d(null, "Login");

        if (!validate()) {
            onSignFailed();
            return;
        }

        _signupbutton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.

        startActivity(intent);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignSuccess() {
        _signupbutton.setEnabled(true);

    }

    public void onSignFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        _signupbutton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String name = _nameText.getText().toString();
        boolean trust = false;
        for (int i = 0; i != email.length(); i++) {
            if (email.charAt(i) == '@') {
                trust = true;
            }
        }
        if (email.isEmpty() || trust == false) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if (name.isEmpty() || name.length() < 4) {
            _nameText.setError("the name must contain more than 4 letters");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if(registerUser()==true){
            valid =true;
        } else valid = false;
        return valid;
    }

    private boolean registerUser(){
        if (!databaseHelper.checkUser(_emailText.getText().toString().trim())) {
            user.setName(_nameText.getText().toString().trim());
            user.setEmail(_emailText.getText().toString().trim());
            user.setPassword(_passwordText.getText().toString().trim());
            databaseHelper.addUser(user);
            intent.putExtra("Email",_emailText.getText().toString().trim() );
            intent.putExtra("UserName", _nameText.getText().toString().trim());
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            emptyInputEditText();
        return true;
        } else {
            return false;
        }
    }
    private void emptyInputEditText(){
        _nameText.setText(null);
        _emailText.setText(null);
        _passwordText.setText(null);
    }

}
