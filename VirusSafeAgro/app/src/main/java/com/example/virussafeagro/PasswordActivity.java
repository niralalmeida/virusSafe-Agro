package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.virussafeagro.uitilities.AppAuthentication;

public class PasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private Button passwordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        // initialize views
        this.initializeViews();

        // set authentication
        AppAuthentication.serAuthentication(this);

        // set Password Button On Click Listener
        this.setPasswordButtonOnClickListener();
    }

    private void initializeViews() {
        this.passwordEditText = findViewById(R.id.et_app_password);
        this.passwordButton = findViewById(R.id.btn_launch_password);
    }

    private void setPasswordButtonOnClickListener() {
        this.passwordButton.setOnClickListener(view -> {
            // check input password
            AppAuthentication.checkPassword(this, this.passwordEditText.getText().toString());
        });
    }
}
