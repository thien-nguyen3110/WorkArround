package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private TextView usernameText;  // define username textview variable
    private Button loginButton;     // define login button variable
    private Button signupButton;    // define signup button variable
    private Button newButton;
    private Button signoutButton;
    private boolean signedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        usernameText = findViewById(R.id.main_username_txt);// link to username textview in the Main activity XML
        loginButton = findViewById(R.id.main_login_btn);    // link to login button in the Main activity XML
        signupButton = findViewById(R.id.main_signup_btn);  // link to signup button in the Main activity XML

        signoutButton = findViewById(R.id.signout_btn);
        newButton = findViewById(R.id.new_btn);

        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            messageText.setText("Home Page");
            signoutButton.setVisibility(View.INVISIBLE);
            usernameText.setVisibility(View.INVISIBLE);             // set username text invisible initially
            loginButton.setVisibility(View.VISIBLE);              // set login button invisible
            signupButton.setVisibility(View.VISIBLE);             // set signup button invisible
            newButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.INVISIBLE);
        } else {
            messageText.setText("Welcome");
            usernameText.setText(extras.getString("USERNAME")); // this will come from LoginActivity
            loginButton.setVisibility(View.INVISIBLE);              // set login button invisible
            signupButton.setVisibility(View.INVISIBLE);             // set signup button invisible
            newButton.setVisibility(View.INVISIBLE);
            signoutButton.setVisibility(View.VISIBLE);

        }

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                signedin = true;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signedin = true;
                /* when signup button is pressed, use intent to switch to Signup Activity */
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signedin = true;
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signedin = false;
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
            }
        });
    }
}