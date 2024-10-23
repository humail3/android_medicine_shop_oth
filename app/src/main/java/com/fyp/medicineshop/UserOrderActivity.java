package com.fyp.medicineshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserOrderActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView txtUserName,txtOrderRent,txtdilivery,txtpickUp,txtphone,
                    txt1,txt2,txt3,txt4,txt5,txt6;
    private ProgressDialog pd;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        setTitle("My Orders");

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        txt5=findViewById(R.id.txt5);
        txt6=findViewById(R.id.txtNoOrderYet);

        txtUserName=findViewById(R.id.txtorderName);
        txtOrderRent=findViewById(R.id.txtorderRent);
        txtphone=findViewById(R.id.txtorderphone);
        txtdilivery=findViewById(R.id.txtorderDilivery);
        btn=findViewById(R.id.btncancel);


    pd=new ProgressDialog(this);

        DatabaseReference dff=  FirebaseDatabase.getInstance().getReference().child("confirm order").child(auth.getCurrentUser().getUid());


        dff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("totalamount").exists())
                    {
                        String name=dataSnapshot.child("name").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();
                        String price=dataSnapshot.child("totalamount").getValue().toString();
                        String dilivery=dataSnapshot.child("deliveryDate").getValue().toString();


                        txtUserName.setText(name);
                        txtphone.setText(phone);
                        txtOrderRent.setText("Rs "+price);
                        txtdilivery.setText(dilivery);
                        txt6.getText().toString();
                        if (txtUserName.equals(""))
                        {
                            btn.setVisibility(View.INVISIBLE);
                            txt6.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            txt6.setVisibility(View.INVISIBLE);
                            btn.setVisibility(View.VISIBLE);
                            txt1.setVisibility(View.VISIBLE);
                            txt2.setVisibility(View.VISIBLE);
                            txt3.setVisibility(View.VISIBLE);
                            txt5.setVisibility(View.VISIBLE);

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void cancelorder(View view) {
        pd.setMessage("Cancel Order");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("confirm order").child(user.getUid()).removeValue().
        addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    Toast.makeText(UserOrderActivity.this, "Successfully Cancel Order ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });



        pd.dismiss();
    }
}
