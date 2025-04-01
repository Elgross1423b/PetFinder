package com.example.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView registerText;
    private TextView forgotPasswordText;
    private PetDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar el helper de la base de datos
        dbHelper = new PetDatabaseHelper(this);

        // Forzar inicialización de la base de datos
        dbHelper.getWritableDatabase().close();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.register_text);
        forgotPasswordText = findViewById(R.id.forgot_password_button);

        loginButton.setOnClickListener(view -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (!user.isEmpty() && !pass.isEmpty()) {
                login(user, pass);
            } else {
                showToast("Please enter both username and password");
            }
        });

        forgotPasswordText.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        registerText.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login(String username, String password) {
        try {
            boolean isValid = dbHelper.validateUser(username, password);

            if (isValid) {
                showToast("Login successful");
                startActivity(new Intent(LoginActivity.this, PetListActivity.class));
                finish();
            } else {
                showToast("Invalid username or password");
            }
        } catch (Exception e) {
            Log.e("LoginActivity", "Login error", e);
            showToast("Login error. Please try again.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}