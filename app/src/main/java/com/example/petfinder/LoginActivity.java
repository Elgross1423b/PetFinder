package com.example.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView registerText;
    private TextView forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.register_text);
        forgotPasswordText = findViewById(R.id.forgot_password_button);

        // Acción para el botón de login
        loginButton.setOnClickListener(view -> {
            //Salto temporal del login


            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();


            if (!user.isEmpty() && !pass.isEmpty()) {
                login(user, pass);
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            }
        });
        // Configuración para el botón de "Olvidé mi contraseña"
        forgotPasswordText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Acción para el enlace de registro
        registerText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Método para hacer login
    private void login(String username, String password) {
        String url = "http://192.168.1.1/petfinder/login.php"; // URL del servidor para hacer login
        if (username.equals("*") && password.equals("*")) {
            startActivity(new Intent(LoginActivity.this, PetListActivity.class));
            Toast.makeText(this,"Ingresando como usuario de pruebas", Toast.LENGTH_SHORT).show();
        }
        // Enviar solicitud de inicio de sesión
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d("LoginResponse", response);
                        JSONObject jsonResponse = new JSONObject(response);

                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        if (status.equals("success")) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, PetListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(LoginActivity.this,"Server communication failed", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                 Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
