package com.example.sentiscape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton, signUpButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.emailLogIn);
        password = findViewById(R.id.passwordLogIn);
        loginButton = findViewById(R.id.logIn);
        signUpButton = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = email.getText().toString().trim();
                String sPassword = password.getText().toString().trim();

                // If the fields are empty, a Toast will display to make users aware of the issue.
                if(sEmail.isEmpty() || sPassword.isEmpty())
                    Toast.makeText(LogInActivity.this, "Username or password not entered.", Toast.LENGTH_SHORT).show();
                // Otherwise Firebase will authenticate the sign in data and start the main activity if details are correct.
                else
                    firebaseAuth.signInWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LogInActivity.this, "Email or Password is Incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

        // Pressing theis button brings user to Profile Activity.
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}