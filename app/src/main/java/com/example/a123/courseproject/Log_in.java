package com.example.a123.courseproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a123.courseproject.helper.InputValidation;
import com.example.a123.courseproject.model.User;
import com.example.a123.courseproject.sql.DatabaseHelper;


public class Log_in extends android.support.v4.app.Fragment {
    private TextView _signupLink;
    Log_in log_in;
    Sign_up sign_up;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    private DatabaseHelper databaseHelper;
    private User user;
    Intent intent;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        ((TextView)getActivity().findViewById(R.id.link_forgotpassword)).setText("Forgot password?");
        databaseHelper = new DatabaseHelper(getActivity());
        user = new User();

        _signupLink = (TextView) view.findViewById(R.id.link_signup);
        _loginButton = (Button) view.findViewById(R.id.btn_login);
        _emailText = (EditText) view.findViewById(R.id.input_email);
        _passwordText = (EditText) view.findViewById(R.id.input_password);

        intent = new Intent(getActivity(), MainActivity.class);
        sign_up =  new Sign_up();
        log_in = new Log_in();

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.log, sign_up, "findThisFragment")
                        .addToBackStack(null)
                        .commit();


            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return view;
    }
    public void login() {
        Log.d(null, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.



        startActivity(intent);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

    }
    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
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

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if(verifyFromSQLite()==true){
            valid = true;
        } else  valid = false;
        return valid;
    }
    private boolean verifyFromSQLite(){
        if (databaseHelper.checkUser(_emailText.getText().toString().trim()
                , _passwordText.getText().toString().trim())) {
            intent.putExtra("UserName", databaseHelper.getColumnUserName(_emailText.getText().toString().trim()
                    , _passwordText.getText().toString().trim()));
            intent.putExtra("Email",_emailText.getText().toString().trim() );
            emptyInputEditText();
            return true;
        } else {
            return false;
        }
    }
    private void emptyInputEditText(){
        _emailText.setText(null);
        _passwordText.setText(null);
    }

}
