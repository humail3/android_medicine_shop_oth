package com.fyp.medicineshop;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.fyp.medicineshop.JavaClass.Favorite_Product;
import com.fyp.medicineshop.ViewHolder.FavoriteItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference df;
    private RecyclerView rv;
    private FavoriteItemAdapter adopter;
    private List<Favorite_Product> favorite_productList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);
        setTitle("My Favorite");

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();


        rv=findViewById(R.id.rv_favItem);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adopter=new FavoriteItemAdapter(this,favorite_productList);
        rv.setAdapter(adopter);
        df= FirebaseDatabase.getInstance().getReference().child("favorite_List").child("User View").child(user.getUid()).child("medicines");
        df.addValueEventListener(postListener);
    }

    ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favorite_productList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Favorite_Product p = postSnapshot.getValue(Favorite_Product.class);
                        favorite_productList.add(p);
                    }
                    adopter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getApplicationContext(), "favorite List is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    }

