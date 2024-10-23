package com.fyp.medicineshop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser muser;
    private EditText etemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        setTitle("");


        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();

        etemail=findViewById(R.id.request_email);

    }

    public void ReqForgetPass(View view) {
        view.setEnabled(false);
        etemail.setEnabled(false);
        String email=etemail.getText().toString().trim();

        if (!foremail())
        {
            return;
        }
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Snackbar.make(findViewById(android.R.id.content), "Email has been Sent", Snackbar.LENGTH_LONG).show();
                            finish();
                        }

                        else {
                            Toast.makeText(ForgetPassActivity.this, "Error Occured!!!", Toast.LENGTH_SHORT).show();
                            view.setEnabled(true);
                            etemail.setEnabled(true);
                        }
                    }
                });
    }

    public boolean foremail(){
        if(etemail.getText().toString().trim().isEmpty()){
            etemail.setError("Email should not be Empty");
            etemail.requestFocus();
            return false;
        }
        else {
            return true;

        }
    }
}
