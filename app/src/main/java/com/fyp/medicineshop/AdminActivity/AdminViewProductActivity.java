package com.fyp.medicineshop.AdminActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.R;
import com.fyp.medicineshop.ViewHolder.AdminViewProductAdopter;

import java.util.ArrayList;
import java.util.List;

public class AdminViewProductActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dr;
    private ValueEventListener postListener;
    private AdminViewProductAdopter adopter;
    private List<Product> productList=new ArrayList<>();
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_product);

        post();

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        dr = FirebaseDatabase.getInstance().getReference().child("medicines");

        rv=findViewById(R.id.adminrecycleView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        adopter=new AdminViewProductAdopter(this,productList);
        rv.setAdapter(adopter);

        dr.addValueEventListener(postListener);
    }


    public void post(){
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Product p = postSnapshot.getValue(Product.class);
                        productList.add(p);
                    }
                    adopter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        };


    }

}
