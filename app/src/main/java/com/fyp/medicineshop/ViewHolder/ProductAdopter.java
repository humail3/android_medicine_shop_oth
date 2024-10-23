package com.fyp.medicineshop.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.ProductDetailActicity;
import com.fyp.medicineshop.SearchProductActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdopter extends RecyclerView.Adapter<ProductAdopter.MyViewHolder> {
    public Context mcontext;
    public List<Product> productList;
    public List<Product> productListFull;

    private DatabaseReference df;


    public ProductAdopter(Context mcontext, List<Product> productList) {
        this.mcontext = mcontext;
        this.productList = productList;
        productListFull = new ArrayList<>(productList);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(mcontext).inflate(R.layout.product_item_view,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        final Product p =productList.get(position);
        myViewHolder.txtProductName.setText(p.name);
        myViewHolder.txtProductDescription.setText(p.description);
        myViewHolder.txtProductPrice.setText("Rs."+p.price);

        Glide.with(mcontext).load(p.image).into(myViewHolder.imageView);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mcontext, ProductDetailActicity.class);
                df = FirebaseDatabase.getInstance().getReference().child("medicines").child(p.getPid());

                i.putExtra("pid",p.getPid());
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtProductName, txtProductPrice,txtProductDescription;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_imageShow);
            txtProductName = (TextView) itemView.findViewById(R.id.product_nameShow);
            txtProductDescription = (TextView) itemView.findViewById(R.id.product_descriptionShow);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_priceShow);



        }

    }
    public void filterList(ArrayList<Product> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }


}

