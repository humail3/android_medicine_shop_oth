package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.fyp.medicineshop.LogIn.LogInActivity;

public class AccountDeleteActivity extends AppCompatActivity {
    private EditText etAccount;
    private FirebaseUser muser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_delete);
        setTitle("");
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();

        etAccount=findViewById(R.id.deleteAccount);
    }

    public void delAccountConfirm(View view) {
        String delAccount=etAccount.getText().toString();
        if (!fordelAccount())
        {
            return;
        }
        AuthCredential ac= EmailAuthProvider.getCredential(muser.getEmail(),delAccount);

        mAuth.signInWithCredential(ac)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            muser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Account has been deleted", Toast.LENGTH_SHORT).show();


                                                Intent i=new Intent(getApplicationContext(), LogInActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
    public boolean fordelAccount()
    {
        if (etAccount.getText().toString().trim().isEmpty())
        {
            etAccount.setError("Please Enter Your Password");
            etAccount.requestFocus();
            return false;
        }
        else
            return true;
    }
}
