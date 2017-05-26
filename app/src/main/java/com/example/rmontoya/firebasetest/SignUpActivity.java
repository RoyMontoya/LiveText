package com.example.rmontoya.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @BindView(R.id.signup_text)
    TextView signUpText;
    @BindView(R.id.email_input)
    EditText emailInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.signin_button)
    Button signInButton;
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupActivity();
    }

    private void setupActivity() {
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        setupListeners();
    }

    private void setupListeners() {
        RxView.clicks(signInButton).subscribe(aVoid -> {
            if(isEmailAndPasswordInvalid()) return;
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                       if(task.isSuccessful())startMainActivity();
                    });
        });
        RxView.clicks(signUpText).subscribe(aVoid -> {
            if(isEmailAndPasswordInvalid()) return;
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if(task.isSuccessful())startMainActivity();
                    });
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isEmailAndPasswordInvalid(){
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        return (email.isEmpty() || password.isEmpty());
    }
}
