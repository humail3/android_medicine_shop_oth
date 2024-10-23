package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.LogIn.LogInActivity;
import com.fyp.medicineshop.ViewHolder.ProductAdopter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.TransactionOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final List<Product> productList = new ArrayList<>();
    TextView getUserNamee, getUserEmaill;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private ValueEventListener postListener;
    private FirebaseUser user;
    private DatabaseReference df;
    private RecyclerView rv;
    private CircleImageView circleImageView;
    private ProductAdopter adopter;
    private String name, email, phone, password;
    private ViewFlipper vflipper;
    private EditText searchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.probarrr);
        progressBar.setVisibility(View.VISIBLE);
       post();
        int[] images = {R.drawable.med1, R.drawable.medd2, R.drawable.medd3};
        vflipper = findViewById(R.id.vflipper);
        for (int i = 0; i < images.length; i++) {
            flipperImages(images[i]);
        }
        searchBox = findViewById(R.id.searchBoxMain);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference().child("medicines");

        rv = findViewById(R.id.recycleView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,1));
        adopter = new ProductAdopter(this, productList);
        rv.setAdapter(adopter);

       df.addValueEventListener(postListener);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user == null) {
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("no", phone);
                    startActivity(intent);
                }

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View v = navigationView.getHeaderView(0);

        getUserNamee = v.findViewById(R.id.getUserName);
        getUserEmaill = v.findViewById(R.id.getUserEmail);
        circleImageView = v.findViewById(R.id.getUserProfile);


        if (user == null) {
            getUserNamee.setText("Guest");
            getUserEmaill.setText("guest@gmail.com");
            circleImageView.setImageResource(R.drawable.profile);

        } else {
            getAllData();
        }

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (Product item : productList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adopter.filterList(filteredList);
    }

    public void getAllData() {
        DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("user").child(auth.getCurrentUser().getUid());
        dff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Retrieving Data...", Toast.LENGTH_SHORT).show();
//                                String image=dataSnapshot.child("imageUrl").getValue().toString();
                    name = dataSnapshot.child("UName").getValue().toString();
                    email = dataSnapshot.child("Uemail").getValue().toString();
                    phone = dataSnapshot.child("UPhoneNo").getValue().toString();
                    password = dataSnapshot.child("Upassword").getValue().toString();

//                    Toast.makeText(getApplicationContext(), "Data is being retrieved...", Toast.LENGTH_SHORT).show();
//                                Glide.with(HomeActivity.this).load(image).into(circleImageView);
                    progressBar.setVisibility(View.GONE);
                    getUserNamee.setText(name);
                    getUserEmaill.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error Occured!!!", Toast.LENGTH_SHORT).show();
            }
        });
        circleImageView.setImageResource(R.drawable.profile);
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        vflipper.addView(imageView);
        vflipper.setFlipInterval(4000);
        vflipper.setAutoStart(true);
        vflipper.setInAnimation(this, android.R.anim.slide_out_right);
        vflipper.setOutAnimation(this, android.R.anim.slide_in_left);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.nav_cart) {
            if (user == null) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            }
            else {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("no", phone);
                startActivity(intent);
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        }
        else if (id == R.id.nav_order) {
            if (user == null) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), com.fyp.medicineshop.UserOrderActivity.class));
            }
        }
        else if (id == R.id.nav_favorite) {
            if (user == null) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), FavoriteItemActivity.class));
            }
        }
        else if (id == R.id.nav_profile) {
            if(user == null) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        } else if (id == R.id.nav_loginOrcreactAccount) {
            if (user == null) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "You are already logged in", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_passwordChange) {
            if (user == null) {
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), com.fyp.medicineshop.ForgetPassActivity.class));
            }
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            //LoginManager.getInstance().logOut();
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void post() {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Product p = postSnapshot.getValue(Product.class);
                        productList.add(p);
                    }

                    adopter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    //progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
