package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PreviewPaymentActivity extends AppCompatActivity {
    private TextView name,phone,address,city,dilivery,pickUp,amount;
    private String nameS,phoneS,productsNameS,productCodeS,addressS,cityS,diliveryS,amountS;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_paiment);
        setTitle("");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        nameS=getIntent().getExtras().get("namee").toString();
        phoneS=getIntent().getExtras().get("phonee").toString();
        productsNameS=getIntent().getExtras().get("productsName").toString();
        productCodeS=getIntent().getExtras().get("productCode").toString();
        addressS=getIntent().getExtras().get("addresss").toString();
        cityS=getIntent().getExtras().get("cityy").toString();
        diliveryS=getIntent().getExtras().get("deliveryDate").toString();
        amountS=getIntent().getExtras().get("totalamountt").toString();

        name=findViewById(R.id.txt1name);
        phone=findViewById(R.id.txt2phone);
        address=findViewById(R.id.txt3add);
        city=findViewById(R.id.txt4city);
        dilivery=findViewById(R.id.txt5dilivery);
        amount=findViewById(R.id.txt7amount);

        name.setText(nameS);
        phone.setText(phoneS);
        address.setText(addressS);
        city.setText(cityS);
        dilivery.setText(diliveryS);
        amount.setText("Rs.  "+amountS);





    }

//
//    public void onlinePay(View view) {
//        Intent i=new Intent(getApplicationContext(),PaymentMethodActivity.class);
//        i.putExtra("nameee",nameS);
//        i.putExtra("phoneee",phoneS);
//        i.putExtra("productsNameee",productsNameS);
//        i.putExtra("productCodeee",productCodeS);
//        i.putExtra("addressss",addressS);
//        i.putExtra("cityyy",cityS);
//        i.putExtra("dilivery",diliveryS);
//        i.putExtra("pickUp",pickUpS);
//        i.putExtra("totalAmounttt",amountS);
//        startActivity(i);
//    }

    public void cashOnDilivery(View view) {
        confirmorder();
    }

    public void confirmorder()
    {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");

        saveCurrentTime=currentTime.format(calendar.getTime());

        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("confirm order").child(user.getUid());

        HashMap<String,Object> orderMap=new HashMap<>();

       // amount=Integer.toString(amount);


        orderMap.put("name", name.getText().toString().trim());
        orderMap.put("totalamount",amountS);
        orderMap.put("productName",productsNameS);
        orderMap.put("productCode",productCodeS);
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("phone",phone.getText().toString());
        orderMap.put("address", address.getText().toString());
        orderMap.put("city", city.getText().toString());
        orderMap.put("state", "not shipped");
        orderMap.put("deliveryDate",dilivery.getText().toString());

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "your final order has been placed successfully", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(), com.fyp.medicineshop.HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(user.getUid())
                            .child("medicines").removeValue();
                    startActivity(i);
                    finish();
                }
            }
        });

    }

}
