package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText etEmail;
    @BindView(R.id.password) EditText etPassword;
    @BindView(R.id.name) EditText etName;
    @BindView(R.id.school) EditText etSchool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignUp)
    public void userSignUp(View view){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String school = etSchool.getText().toString().trim();

        if (email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            etPassword.setError("Password should be atleast 6 character long");
            etPassword.requestFocus();
            return;
        }

        if (name.isEmpty()){
            etName.setError("Name is required");
            etName.requestFocus();
            return;
        }

        if (school.isEmpty()){
            etSchool.setError("School is required");
            etSchool.requestFocus();
            return;
        }
    }

    @OnClick(R.id.txtAlreadySignedIn)
    public void alreadySignedIn(View view){

    }
}
