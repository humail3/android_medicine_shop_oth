package com.fyp.medicineshop.LogIn;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.fyp.medicineshop.AdminActivity.AdminCategoryActivity;
import com.fyp.medicineshop.ForgetPassActivity;
import com.fyp.medicineshop.HomeActivity;
import com.fyp.medicineshop.R;
import com.fyp.medicineshop.SignUpActivity;

public class LogInActivity extends AppCompatActivity {
    private EditText etmail, etpassword;
    private TextInputLayout inputemail, inputpass;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private Button loginBtn;
    private TextView forgotPasstext, guestAccountText, signUpText;
    private FirebaseUser muser;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Login");
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();

        if (muser != null) {
            Intent i = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        progressBar = findViewById(R.id.loginProgressbAr);
        etmail = findViewById(R.id.email_sighin);
        etpassword = findViewById(R.id.pass_sighin);
        loginBtn = findViewById(R.id.loginBTn);
        guestAccountText = findViewById(R.id.guestAccount);
        signUpText = findViewById(R.id.signup);
        forgotPasstext = findViewById(R.id.forgotPassword);

        inputemail = findViewById(R.id.tvinputemailSignin);
        inputpass = findViewById(R.id.tvinputPassSignin);

        forgotPasstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword(v);
            }
        });
        guestAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useAsGuest(v);
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(v);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInternetConnection(LogInActivity.this)){
                    ShowinternetError();
                    return;
                }
                sighnIn(v);
            }
        });

    }


    public void sighnIn(View view) {
        disableAllEditTextAndShowLoading();
        final String email = etmail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (email.equals("admin") && password.equals("123456")) {
            startActivity(new Intent(getApplicationContext(), AdminCategoryActivity.class));
            finish();
        } else {
            if (!foremail()) {
                EnableAllEditTextAndHideLoading();
                return;
            }
            if (!forpass()) {
                EnableAllEditTextAndHideLoading();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Successfully Logged in...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email or Password is not correct", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    EnableAllEditTextAndHideLoading();
                    Toast.makeText(LogInActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean foremail() {
        if (etmail.getText().toString().trim().isEmpty()) {
            inputemail.setError("Email should not be Empty");
            etmail.requestFocus();
            return false;
        } else {
            inputemail.setErrorEnabled(false);
        }
        return true;
    }

    public boolean forpass() {
        if (etpassword.getText().toString().trim().isEmpty()) {
            inputpass.setError("Password should not be Empty");
            etmail.requestFocus();
            return false;
        } else {
            inputemail.setErrorEnabled(false);
        }
        return true;
    }

    public void clear() {
        etmail.setText("");
        etpassword.setText("");
    }

    public void signup(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();

    }

    public void useAsGuest(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();

    }
    private void disableAllEditTextAndShowLoading(){
        etmail.setEnabled(false);
        etpassword.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);
    }private void EnableAllEditTextAndHideLoading(){
        progressBar.setVisibility(View.INVISIBLE);
        loginBtn.setEnabled(true);
        etmail.setEnabled(true);
        etpassword.setEnabled(true);
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassActivity.class));
    }
    private boolean checkInternetConnection(LogInActivity logInActivity){
           ConnectivityManager connectivityManager = (ConnectivityManager) logInActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobileData !=null && mobileData.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    void ShowinternetError(){
        Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
    }
}
