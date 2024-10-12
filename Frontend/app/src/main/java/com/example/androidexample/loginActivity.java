package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD

=======
>>>>>>> e5441b70751d6a8d23521143890aad1fa74d10f0

public class loginActivity extends AppCompatActivity {

    private TextView messageText;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;
    private ImageButton showPassword;
    private Button forgotPasswordButton;
    private Button newUserButton;

    boolean isPasswordVisible = false;

    String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/login";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        /*Initialize UI Elements*/
        submitButton = findViewById(R.id.submitButton);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        messageText = findViewById(R.id.mainMessage);

        forgotPasswordButton = findViewById(R.id.forgotButton);
        newUserButton = findViewById(R.id.newUserJoin);

        showPassword = findViewById(R.id.showPassword);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                // Get the user input from the text fields
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Check if both fields are filled
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Call loginRequest() to send the request
                    loginRequest();
                } else {
                    // Display a message if fields are empty
                    messageText.setText("Please enter both username and password.");
                }
=======
                Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                startActivity(intent);
                loginRequest();
>>>>>>> e5441b70751d6a8d23521143890aad1fa74d10f0
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible){
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword.setImageResource(R.drawable.eyehide);
                }
                else{
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword.setImageResource(R.drawable.eyeshow);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, forgotpasswordActivity.class);
                startActivity(intent);
            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, joinNowActivity.class);
                startActivity(intent);
            }
        });


    }

    public void loginRequest() {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/login";
        JSONObject loginData = new JSONObject();

        try {
            loginData.put("username", usernameInput.getText().toString());
            loginData.put("password", passwordInput.getText().toString());
            Log.i("hidden", loginData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating a custom request to allow for GET requests with a body
        JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // Pass null for body since we can't send a body in GET
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Login Response", response.toString());
                        if (response.optString("message").equals("Login successfully")) {
                            Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                            startActivity(intent);
                        } else {
                            messageText.setText("Unexpected response: " + response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Login Error", error.toString());
                        messageText.setText(error.toString());
                    }
<<<<<<< HEAD
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameInput.getText().toString());
                params.put("password", passwordInput.getText().toString());
                return params; // This is where parameters get sent
            }
        };
=======
                }
                )
                {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        return params;
                    }
                };
>>>>>>> e5441b70751d6a8d23521143890aad1fa74d10f0

        Volley.newRequestQueue(this).add(loginRequest);
    }

}
