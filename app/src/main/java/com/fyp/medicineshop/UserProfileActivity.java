package com.fyp.medicineshop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    private TextView etname, etmail, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setTitle("My Profile");


        etname = findViewById(R.id.name_id);
        etmail = findViewById(R.id.email_id);
        etPhone = findViewById(R.id.phone_id);
//        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("name").toString(), Toast.LENGTH_SHORT).show();
        etname.setText(getIntent().getStringExtra("name"));
        etmail.setText(getIntent().getStringExtra("email"));
        etPhone.setText(getIntent().getStringExtra("phone"));


    }


}
