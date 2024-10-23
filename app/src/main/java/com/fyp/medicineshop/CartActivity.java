package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.fyp.medicineshop.JavaClass.CartProduct;
import com.fyp.medicineshop.ViewHolder.CartItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {

    private DatabaseReference df;
    private FirebaseAuth auth;
    private Button btnNext;
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private String state = "normal";

    private final List<CartProduct> cartProductList = new ArrayList<>();
    ValueEventListener postlistning = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            cartProductList.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CartProduct cp = postSnapshot.getValue(CartProduct.class);
                    cartProductList.add(cp);
                }
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

        }
    };
    private final int cost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("Cart");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        checkOrderState();


        btnNext = findViewById(R.id.btnNext_cart_list);
        recyclerView = findViewById(R.id.rv_cart_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartItemAdapter(getApplicationContext(), cartProductList);
        recyclerView.setAdapter(adapter);
        df = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(user.getUid()).child("medicines");
        df.addValueEventListener(postlistning);

    }

    private void checkOrderState() {
        DatabaseReference deRef;
        deRef = FirebaseDatabase.getInstance().getReference().child("confirm order").child(user.getUid());
        deRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("totalamount").exists()) {
                        String shippingState = dataSnapshot.child("state").getValue().toString();
                        if (shippingState.equals("shipped")) {
                            state = "order shipped";
                        } else if (shippingState.equals("not shipped")) {
                            state = "order placed";

                        }

                    }

                }else{
                        //Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void next(View view) {
        if (cartProductList.isEmpty()) {
            Toast.makeText(this, "Empty Cart", Toast.LENGTH_SHORT).show();
        } else if (state.equals("order placed") || state.equals("order shipped")) {

            Toast.makeText(getApplicationContext(), "You can Rent more Products, once you recieve your first final order ", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getApplicationContext(),ProductDeliverDateActivity.class);
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.putExtra("no",getIntent().getStringExtra("no"));
            startActivity(intent);
//            startActivity(new Intent(getApplicationContext(), com.shahbaz.rentadressapplicationsemesterproject.ProductDeliverDateActivity.class));

        }
    }


}

