package com.example.muhammedraheezrahman.todolist.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammedraheezrahman.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends RootActivity {

    TextInputEditText emailEt,passEt;
    Button login,signup;
    String email,password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.loginBut);
        signup = (Button) findViewById(R.id.signupBut);
        emailEt = (TextInputEditText) findViewById(R.id.email_et);
        passEt = (TextInputEditText) findViewById(R.id.passEt);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEt.getText().toString();
                password = passEt.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){

                    auth.signInWithEmailAndPassword(email,password).
                            addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(!task.isSuccessful()){

                                        Log.d("RanLogin","Login failed");
                                        Toast.makeText(getApplicationContext(),"Not successfull",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d("RanLogin","Login success");
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                }


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });
    }
}
