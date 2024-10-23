package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.fyp.medicineshop.JavaClass.CartProduct;
import com.fyp.medicineshop.ViewHolder.CartItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    private EditText shipmentname,shipmentphone,shipmentaddress,shipmentcity;
    private TextView ordertotalprice;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference df;
    private CartItemAdapter adapter;
    private List<CartProduct> cartProductList=new ArrayList<>();
    private int cost=0;
    private String getDeliveryDate,getPickUpDate;
    private TextView deliveryDate,pickUpDate;
    private String productsName="";
    private String productCode="";
    private String Name = "";
    private String phoneNo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        setTitle("");

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        deliveryDate=findViewById(R.id.delivery_product);
        pickUpDate=findViewById(R.id.pickUp_product);
        getDeliveryDate=getIntent().getStringExtra("delivery");

        Name = getIntent().getStringExtra("name");
        phoneNo = getIntent().getStringExtra("no");
        deliveryDate.setText(getDeliveryDate);
        pickUpDate.setText(getPickUpDate);

        ordertotalprice=findViewById(R.id.ordertotalprice_id);
        shipmentname=findViewById(R.id.shipment_name);
        shipmentphone=findViewById(R.id.shipment_phone);
        shipmentaddress=findViewById(R.id.shipment_address);
        shipmentcity=findViewById(R.id.shipment_city);
        shipmentname.setText(Name);
        shipmentphone.setText(phoneNo);
        adapter=new CartItemAdapter(getApplicationContext(),cartProductList);
        df= FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(user.getUid()).child("medicines");
        df.addValueEventListener(postlistning);




    }

    ValueEventListener postlistning=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            cartProductList.clear();

            if (dataSnapshot.exists())
            {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    CartProduct cp=postSnapshot.getValue(CartProduct.class);
                    cartProductList.add(cp);
                    Integer gettotalprice =  Integer.parseInt(cp.getPrice());
                    String getProductCode=String.valueOf(cp.getPcode());
                    String getproductName=cp.getName();
                    productCode=productCode+","+getProductCode;
                    productsName=productsName+","+getproductName;
                    cost=cost+gettotalprice;
                }
                adapter.notifyDataSetChanged();
            }

            adapter.notifyDataSetChanged();
            ordertotalprice.setText("Total Cost: PKR " + cost + " Only");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

        }
    };


    public void confirmshipment(View view) {

        if (forname())
        {
            return;
        }
        if (forPhone())
        {
            return;
        }
        if (forAdress())
        {
            return;
        }
        if (forCityName())
        {
            return;
        }
        Intent i=new Intent(getApplicationContext(), PreviewPaymentActivity.class);
        i.putExtra("namee", shipmentname.getText().toString().trim());
        i.putExtra("totalamountt",cost);
        i.putExtra("productsName",productsName);
        i.putExtra("productCode",productCode);
        i.putExtra("phonee",shipmentphone.getText().toString().trim());
        i.putExtra("addresss", shipmentaddress.getText().toString().trim());
        i.putExtra("cityy", shipmentcity.getText().toString().trim());
        i.putExtra("deliveryDate",deliveryDate.getText().toString().trim());
        startActivity(i);

    }

    public boolean forname()
    {
        if (shipmentname.getText().toString().trim().isEmpty())
        {
            shipmentname.setError("Enter Your Name");
            shipmentname.requestFocus();
            return true;
        }
        else
            return false;
    }
    public boolean forPhone()
    {
        if (shipmentphone.getText().toString().trim().isEmpty())
        {
            shipmentphone.setError("Enter Your Phone Number");
            shipmentphone.requestFocus();
            return true;
        }
        else
            return false;
    }
    public boolean forAdress()
    {
        if (shipmentaddress.getText().toString().trim().isEmpty())
        {
            shipmentaddress.setError("Enter Your Address");
            shipmentaddress.requestFocus();
            return true;
        }
        else
            return false;
    }
    public boolean forCityName()
    {
        if (shipmentcity.getText().toString().trim().isEmpty())
        {
            shipmentcity.setError("Enter Your City");
            shipmentcity.requestFocus();
            return true;
        }
        else
            return false;
    }


}
