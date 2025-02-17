package com.example.petfinder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email);
        resetButton = findViewById(R.id.reset_button);

        resetButton.setOnClickListener(view -> {
            String userEmail = email.getText().toString().trim();

            if (!userEmail.isEmpty()) {
                resetPassword(userEmail);
            } else {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(String email) {
        String url = "http://192.168.1.11/petfinder/reset_password.php";  // URL para el servicio PHP

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(ForgotPasswordActivity.this, "Error sending reset link", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
