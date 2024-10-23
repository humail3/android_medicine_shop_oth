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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.JavaClass.CartProduct;
import com.fyp.medicineshop.R;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> {

    private DatabaseReference df;
    private FirebaseAuth auth;
    private FirebaseUser user;
    ArrayList<CartProduct> list;

    private Context mcontext;

    private List<CartProduct> cartProductList;

    public CartItemAdapter(Context mcontext, List<CartProduct> cartProductList) {
        this.mcontext = mcontext;
        this.cartProductList = cartProductList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();




        View v= LayoutInflater.from(mcontext).inflate(R.layout.cart_items,viewGroup,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {



        final CartProduct cp=cartProductList.get(position);
        myViewHolder.productName_cartList.setText(cp.getName());
        myViewHolder.productPrice_cartList.setText("Price="+cp.getPrice()+" Pkr");
        Glide.with(mcontext).load(cp.image).into(myViewHolder.imageView);
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

                                df= FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(user.getUid()).child("medicines").child(cp.getPid());
                                df.removeValue();
                                cartProductList.clear();
                                Toast.makeText(mcontext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                notifyItemChanged(myViewHolder.getAdapterPosition());


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
        return cartProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView productName_cartList,productPrice_cartList;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        productName_cartList=itemView.findViewById(R.id.productName_cartList);
        productPrice_cartList=itemView.findViewById(R.id.productPrice_cartList);
        imageView=itemView.findViewById(R.id.productImage_cartList);
        }
    }

}
