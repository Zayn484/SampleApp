package com.example.zayn.sampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button submitButton;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSession();

        submitButton = (Button)findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Getting response from server",Toast.LENGTH_SHORT).show();
                login();
                // Saving in offline database
                databaseHelper = new DatabaseHelper(MainActivity.this);

                int random = (int)Math.random() * 50+1;
                String id = String.valueOf(random);
                boolean isInserted = databaseHelper.insertData(id,
                        usernameEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());

                if (isInserted)
                    Log.v("MainActivity","inserted");
                else
                    Toast.makeText(MainActivity.this,"not inserted",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void checkSession(){

        Boolean check = Boolean.valueOf(SharedPref.readSharefPref(MainActivity.this,
                                                                    "loginPref",
                                                                    "false"));

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("loginPref",check);

        if (check){
            startActivity(intent);
            finish();
        }

    }


    private void login(){

        usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);

        String url ="https://sharp-edged-dock.000webhostapp.com/validate-user.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("success")) {
                    //Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                    SharedPref.writeSharedPref(MainActivity.this, "loginPref","true");
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameEditText.getText().toString().trim());
                params.put("password", passwordEditText.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(request);


    }

}
