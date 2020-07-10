package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.models.DefaultResponse;
import com.example.retrofit.R;
import com.example.retrofit.api.RetrofitClient;
import com.example.retrofit.storage.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    @OnClick(R.id.btnSignUp)
    public void userSignUp(View v){
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

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name, school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {
                    DefaultResponse df = response.body();
                    Toast.makeText(MainActivity.this, df.getMsg(), Toast.LENGTH_LONG).show();
                } else if(response.code() == 422){
                    Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }


        }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }


//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                String s = null;
//
//
//                try {
//                    if (response.code() == 201){
//                        s = response.body().string();
////                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//                    } else {
//                        s = response.errorBody().string();
////                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (s != null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

        @OnClick(R.id.txtAlreadySignedIn)
        public void alreadySignedIn(View v){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
       });
    }
}