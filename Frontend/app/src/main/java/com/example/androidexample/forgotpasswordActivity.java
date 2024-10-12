package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class forgotpasswordActivity extends AppCompatActivity {

    private Button back_button;
    private EditText email_input;
    private Button submit_button;
    private TextView messageText;

    String url = "https://304b2c41-4ef3-4e62-a2f8-e40348b54d5e.mock.pstmn.io";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        back_button = findViewById(R.id.backButton);
        email_input = findViewById(R.id.usernameInput);
        submit_button = findViewById(R.id.submitButton);
        messageText = findViewById(R.id.messageText);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotpasswordActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_input.getText().toString().trim();

                //Calling in submit button to check email in other function
                checkEmail(email);

                if(email.equals("admin123@gmail.com")){
                    //SEND RESET PASSWORD TO EMAIL

                    //Send user to email sent page
                    Intent intent = new Intent(forgotpasswordActivity.this, resetPasswordActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                }

                //Email not in database send message letting them know
                else{
                    messageText.setText("");
                }
            }
        });
    }

    //check if the email exists in the database
    private void checkEmail(String email) {
        String url = "http://your-server-url/api/userprofile/checkemail?email=" + email;

        // Create a request
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // If email exists, send the user to the reset password page
                    Intent intent = new Intent(forgotpasswordActivity.this, resetPasswordActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                },
                error -> {
                    // Handle error - email does not exist
                    messageText.setText("An account could not be found for the given email ID.");
                }
        );

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);
    }
}
