package com.fyp.medicineshop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.ViewHolder.ProductAdopter;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference dr;
    private ValueEventListener postListener;
    private ProductAdopter adopter;
    private EditText searchet;
    private List<Product> productList=new ArrayList<>();
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        setTitle("");
        post();

        auth= FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference().child("medicines");
    searchet=findViewById(R.id.autosearch);


        rv=findViewById(R.id.search_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adopter=new ProductAdopter(this,productList);
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

    public void searchProduct(View view) {
       // searcher();
        if (!forsearch())
        {
            return;
        }
search();
search11();
    }

   /* public void searcher(){
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.select_dialog_item,getResources().getStringArray(R.array.search_items));
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String)parent.getItemAtPosition(position);
                Query query=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("price").startAt(selection);
                query.addValueEventListener(postListener);
            }
        });
    }*/
private void search()
{
    String search1=searchet.getText().toString().trim();
    Query query=FirebaseDatabase.getInstance().getReference().child("medicines").orderByChild("price").startAt(search1);
    query.addValueEventListener(postListener);
}
    private void search11()
    {
        String search1=searchet.getText().toString().trim();
        Query query=FirebaseDatabase.getInstance().getReference().child("medicines").orderByChild("name").startAt(search1);
        query.addValueEventListener(postListener);
    }
    public boolean forsearch()
    {


         if(searchet.getText().toString().trim().isEmpty()){
        searchet.setError("Email should not be Empty");
        searchet.requestFocus();
        return false;
    }
        else {
        return true;

    }

}
}
