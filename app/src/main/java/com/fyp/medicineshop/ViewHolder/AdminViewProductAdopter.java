package com.fyp.medicineshop.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.JavaClass.Product;
import com.fyp.medicineshop.R;

import java.util.List;

public class AdminViewProductAdopter extends RecyclerView.Adapter<AdminViewProductAdopter.MyViewHolder> {
    private DatabaseReference df;
    public Context mcontext;
    public List<Product> productList;

    public AdminViewProductAdopter(Context mcontext, List<Product> productList) {
        this.mcontext = mcontext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.product_item_view,viewGroup,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        final Product p=productList.get(position);
        myViewHolder.txtProductName.setText(p.name);
        myViewHolder.txtProductPrice.setText("Price = "+p.price+"Pkr");

        myViewHolder.txtProductDescription.setText(p.description);
//        Toast.makeText(mcontext, getItemCount()+" Items", Toast.LENGTH_SHORT).show();
        Glide.with(mcontext).load(p.image).into(myViewHolder.imageView);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopUpmenu(v);

            }

            private void showPopUpmenu(View view) {

                PopupMenu popupMenu = new PopupMenu(mcontext,view);
                popupMenu.getMenuInflater().inflate(R.menu.cart_item_menu,
                        popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.deleteCartItem:
                                df=FirebaseDatabase.getInstance().getReference().child("medicines").child(p.getPid());
                                df.removeValue();
                                productList.clear();
                                Toast.makeText(mcontext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                return true;

                            default:
                        }

                        return false;
                    }

                });
                popupMenu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return  productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductDescription, txtProductPrice;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_imageShow);
            txtProductName = (TextView) itemView.findViewById(R.id.product_nameShow);
           txtProductDescription = (TextView) itemView.findViewById(R.id.product_descriptionShow);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_priceShow);

        }
    }
}
