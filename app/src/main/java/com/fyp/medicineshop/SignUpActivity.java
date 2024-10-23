package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.JavaClass.Details;
import com.fyp.medicineshop.LogIn.LogInActivity;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser muser;
    private DatabaseReference df;
    private ProgressBar progressBar;
    private TextInputLayout inputemail, inputpass, inputcpass, inputphoneNO, inputName;
    private EditText etmail, etpassword, etconfirmpassword, etPhoneNo, etName;
    private String Uemail, Upassword, UcPassword, UPhoneNo, UName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");


        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();

        if (muser == null) {
            setContentView(R.layout.activity_sign_up);
        } else {
            Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }


        progressBar = findViewById(R.id.signUpProgressBar);
        etmail = findViewById(R.id.emailSignup_id);
        etpassword = findViewById(R.id.passSighup_id);
        etconfirmpassword = findViewById(R.id.conPasssSighup_id);
        etPhoneNo = findViewById(R.id.connumberSighup_id);
        etName = findViewById(R.id.nameSignup_id);

        inputemail = findViewById(R.id.inputEmail_signup);
        inputpass = findViewById(R.id.inputPass_sighup);
        inputcpass = findViewById(R.id.inputconPass_sighnup);
        inputphoneNO = findViewById(R.id.inputconnum_sighnup);
        inputName = findViewById(R.id.inputname_signup);

        etmail.addTextChangedListener(new myText(etmail));
        etpassword.addTextChangedListener(new myText(etpassword));
        etconfirmpassword.addTextChangedListener(new myText(etconfirmpassword));
        etPhoneNo.addTextChangedListener(new myText(etPhoneNo));
        etName.addTextChangedListener(new myText(etName));

    }


    public void sighnup(View view) {
        disableAllEditTextAndShowLoading(view);
        Uemail = etmail.getText().toString().trim();
        Upassword = etpassword.getText().toString().trim();
        UcPassword = etconfirmpassword.getText().toString().trim();
        UPhoneNo = etPhoneNo.getText().toString().trim();
        UName = etName.getText().toString().trim();

        if (!forcname()) {
            EnableAllEditTextAndHideLoading(view);
            return;
        }
        if (!formail()) {
            EnableAllEditTextAndHideLoading(view);
            return;
        }

        if (!forpass()) {
            EnableAllEditTextAndHideLoading(view);
            return;
        }
        if (!forcpass()) {
            EnableAllEditTextAndHideLoading(view);
            return;
        }
        if (!forcPhone()) {
            EnableAllEditTextAndHideLoading(view);
            return;
        }

        mAuth.createUserWithEmailAndPassword(Uemail, Upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Details d = new Details(Uemail, UName, Upassword, UPhoneNo);
                    FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid()).setValue(d);
                    Toast.makeText(getApplicationContext(), "Logged in...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EnableAllEditTextAndHideLoading(view);
                Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disableAllEditTextAndShowLoading(View view) {
        etmail.setEnabled(false);
        etpassword.setEnabled(false);
        etconfirmpassword.setEnabled(false);
        etName.setEnabled(false);
        etPhoneNo.setEnabled(false);
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void EnableAllEditTextAndHideLoading(View view) {
        progressBar.setVisibility(View.INVISIBLE);
        view.setEnabled(true);
        etmail.setEnabled(true);
        etpassword.setEnabled(true);
        etconfirmpassword.setEnabled(true);
        etName.setEnabled(true);
        etPhoneNo.setEnabled(true);

    }


    public boolean formail() {
        String emailmatch = etmail.getText().toString().trim();
        if (etmail.getText().toString().trim().isEmpty()) {
            inputemail.setError("Email should not be Empty");
            etmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailmatch).matches()) {
            inputemail.setError("Email Pattern is wrong");
            etmail.requestFocus();
            return false;
        } else {
            inputemail.setErrorEnabled(false);
        }
        return true;

    }

    public boolean forpass() {
        if (etpassword.getText().toString().trim().isEmpty()) {
            inputpass.setError("Please Enter your Password");
            etpassword.requestFocus();
            return false;
        } else {
            inputpass.setErrorEnabled(false);
        }
        return true;
    }

    public boolean forcpass() {

        String pass = etpassword.getText().toString().trim();
        String cpass = etconfirmpassword.getText().toString().trim();

        if (cpass.isEmpty()) {
            inputcpass.setError("Please Enter Confirm Password");
            etconfirmpassword.requestFocus();
            return false;
        } else if (!cpass.equals(pass)) {
            inputcpass.setError("Password should be same");
            etconfirmpassword.requestFocus();
            return false;
        } else {
            inputcpass.setErrorEnabled(false);
        }

        return true;

    }

    public boolean forcPhone() {

        String phone = etPhoneNo.getText().toString().trim();

        if (phone.isEmpty()) {
            inputphoneNO.setError("Please Enter Phone No");
            etPhoneNo.requestFocus();
            return false;
        } else {
            inputphoneNO.setErrorEnabled(false);
        }

        return true;

    }

    public boolean forcname() {

        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            inputName.setError("Please Enter Name");
            etName.requestFocus();
            return false;
        } else {
            inputName.setErrorEnabled(false);
        }

        return true;

    }

    public void signin(View view) {
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        finish();
    }

    public class myText implements TextWatcher {

        View v;

        public myText(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (v.getId()) {
                case R.id.email_id:
                    formail();
                    break;
                case R.id.passSighup_id:
                    forpass();
                    break;
                case R.id.conPasssSighup_id:
                    break;

            }

        }
    }

}
