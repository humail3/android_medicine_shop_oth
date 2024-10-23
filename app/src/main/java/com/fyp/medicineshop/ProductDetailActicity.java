package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.LogIn.LogInActivity;
import com.fyp.medicineshop.ViewHolder.ProductAdopter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProductDetailActicity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button addtoCartBtn;
    private ImageView productImage;
    private TextView productName,productDescription,productPrice;
    private String productID,imageUrl,saveCurrentTime,saveCurrentDate,state="normal";
    private String productCode="";

    private List<Product> list= new ArrayList<>();
    private ProductAdopter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_acticity);
        setTitle("");

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        productPrice=findViewById(R.id.product_Price_detail);
        addtoCartBtn=findViewById(R.id.add_product_to_cart);
        productName=findViewById(R.id.product_name_detail);
        productDescription=findViewById(R.id.product_description_detail);
        productImage=findViewById(R.id.product_image_detail);

        productID=getIntent().getExtras().get("pid").toString();
        getProductDetail(productID);



//        checkOrderState();
        adapter=new ProductAdopter(getApplicationContext(),list);

        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user==null)
                {
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                }
                else
                    {
                        if (state.equals("Order Shipped") || state.equals("Order Placed"))
                        {
                            Toast.makeText(getApplicationContext(), "You can rent more products, once your order shipped or confirmed", Toast.LENGTH_LONG).show();
                        }else
                            {
                                selectedCalendarDate();
                            }

                    }


            }
        });

    }
    public void img_favorite(View view) {
        addToFavorite();

    }
    public void selectedCalendarDate()
    {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");

        saveCurrentTime=currentTime.format(calendar.getTime());

        DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

        HashMap<String,Object> cartMap=new HashMap<>();

        cartMap.put("pid", productID);
        cartMap.put("image",imageUrl);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("name", productName.getText().toString());
       cartMap.put("pcode", productCode);
        cartMap.put("userID",user.getUid());

        cartListRef.child("User View").child(user.getUid()).child("medicines").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ProductDetailActicity.this, "added to cart", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductDetailActicity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductDetail(String productID) {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("medicines");
        databaseReference.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();


                if (dataSnapshot.exists())
                {

                    Product p=dataSnapshot.getValue(Product.class);

                   productCode=p.getPcode();

                    productName.setText(p.getName());
                    productDescription.setText(p.getDescription());
                    productPrice.setText(p.getPrice());

                    imageUrl=p.getImage();

                    Glide.with(ProductDetailActicity.this).load(p.getImage()).into(productImage);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addToFavorite()
    {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");

        saveCurrentTime=currentTime.format(calendar.getTime());

        DatabaseReference favoriteListRef=FirebaseDatabase.getInstance().getReference().child("favorite_List");

        HashMap<String,Object> favoriteMap=new HashMap<>();

        favoriteMap.put("pid", productID);
        favoriteMap.put("image",imageUrl);
        favoriteMap.put("date", saveCurrentDate);
        favoriteMap.put("time", saveCurrentTime);
        favoriteMap.put("name", productName.getText().toString().trim());
        favoriteMap.put("description", productDescription.getText().toString().trim());

        favoriteListRef.child("User View").child(user.getUid()).child("medicines").child(productID)
                .updateChildren(favoriteMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Successfully added to favorite list",Snackbar.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductDetailActicity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkOrderState()
    {
        DatabaseReference deRef;
        deRef=FirebaseDatabase.getInstance().getReference().child("confirm order").child(user.getUid());
        deRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("totalamount").exists())
                    {
                        String shippingState=dataSnapshot.child("state").getValue().toString();



                        if (shippingState.equals("shipped"))
                        {
                           state="Order Shipped";
                        }
                        else if (shippingState.equals("not shipped"))
                        {
                            state="Order Placed";
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
