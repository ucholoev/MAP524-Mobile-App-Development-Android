/*********************************************************************************
 * MAP524-C1C(Mobile App Development - Android) – Assignment 1
 * I declare that this assignment is my own work in accordance with Seneca Academic Policy. No part of this
 * assignment has been copied manually or electronically from any other source (including web sites) or
 * distributed to other students.
 *
 * Name: Ularbek Choloev Student ID: 118-346-162 Date: 11/12/2018
 *
 ********************************************************************************/
package com.example.ace.tallycounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countTextView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;

        // "Click me!" button activates when it has been clicked
        Button countButton = findViewById(R.id.countButton);
        countTextView = findViewById(R.id.countTextView);

        countButton.setOnClickListener(onClickCountButton);

        // Reset button
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(onClickResetButton);
    }

    // Implementing the function so ClickButton can work
    private View.OnClickListener onClickCountButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count++;
            countTextView.setText(String.valueOf(count));
        }
    };

    private View.OnClickListener onClickResetButton = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
           count = 0;
           countTextView.setText(String.valueOf(count));
        }
    };
}
