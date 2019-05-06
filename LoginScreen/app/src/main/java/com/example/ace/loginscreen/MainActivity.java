/*********************************************************************************
 * MAP524-C1C(Mobile App Development - Android) – Assignment 2
 * I declare that this assignment is my own work in accordance with Seneca Academic Policy. No part of this
 * assignment has been copied manually or electronically from any other source (including web sites) or
 * distributed to other students.
 *
 * Name: Ularbek Choloev Student ID: 118-346-162 Date: 11/12/2018
 *
 ********************************************************************************/
package com.example.ace.loginscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onClickLoginButton);
    }

    private View.OnClickListener onClickLoginButton = new View.OnClickListener()
    {
        private int attempt = 3;
        @Override
        public void onClick(View v)
        {
            EditText userNameEditText = findViewById(R.id.usernameEditText);
            EditText passwordEditText = findViewById(R.id.passwordEditText);
            TextView errorTextView = findViewById(R.id.errorTextView);

            String userName = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            LoginManager loginManager = new LoginManager(userName, password);

            if(loginManager.hasValidCredentials())
            {
                // Success
                errorTextView.setVisibility(View.INVISIBLE);
                // TODO: 2. The failed attempts should reset if they get the login information correct
                attempt = 3;
            }else
            {
                // Decreasing number of attempts
                attempt--;
                // Failure
                errorTextView.setText(R.string.error_text);
                // TODO: 1. Modify the error message to show the user how many attempts they have left (of 3 total attempts)
                // Modifying the error message
                errorTextView.append(Integer.toString(attempt));
                errorTextView.setVisibility(View.VISIBLE);

                // TODO: 3. After 3 failed attempts lock the user out of the app (a simple way to do this is to disable the login button)
                if (attempt < 1)
                {
                    Button loginButton = findViewById(R.id.loginButton);
                    loginButton.setOnClickListener(onClickLoginButton);

                    loginButton.setEnabled(false);
                }
            }
        }
    };
}
