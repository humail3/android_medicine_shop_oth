package com.fyp.medicineshop.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.medicineshop.ProductDetailActicity;
import com.fyp.medicineshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.JavaClass.Favorite_Product;

import java.util.List;

public class FavoriteItemAdapter extends RecyclerView.Adapter<FavoriteItemAdapter.MyViewHolder> {
    private DatabaseReference df;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Context mcontext;

    private List<Favorite_Product> favorite_productList;

    public FavoriteItemAdapter(Context mcontext, List<Favorite_Product> favorite_productList) {
        this.mcontext = mcontext;
        this.favorite_productList = favorite_productList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        View v= LayoutInflater.from(mcontext).inflate(R.layout.user_favorite_item,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        final Favorite_Product fp=favorite_productList.get(position);

        myViewHolder.favItemName.setText(fp.getName());
        myViewHolder.favRent.setText(fp.getPrice());
        myViewHolder.favItemDescriptiop.setText(fp.getDescription());

        Glide.with(mcontext).load(fp.image).into(myViewHolder.imageView);

        myViewHolder.btnFavPlaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mcontext, ProductDetailActicity.class);
                i.putExtra("pid",fp.getPid());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);
            }
        });
        myViewHolder.btnFavcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                df= FirebaseDatabase.getInstance().getReference().child("favorite_List").child("User View").child(user.getUid()).child("medicines").child(fp.getPid());
                df.removeValue();
                favorite_productList.clear();
                Toast.makeText(mcontext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                notifyItemChanged(myViewHolder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return favorite_productList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView favItemName,favRent,favItemDescriptiop;
        private ImageView imageView;
        private Button btnFavcancel,btnFavPlaced;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            favItemName=itemView.findViewById(R.id.productName_fav);
            favRent=itemView.findViewById(R.id.productRent_fav);
            favItemDescriptiop=itemView.findViewById(R.id.productDiscription_fav);
            btnFavcancel=itemView.findViewById(R.id.cancerOrderBtn);
            btnFavPlaced=itemView.findViewById(R.id.placeOrderbtn);
            imageView=itemView.findViewById(R.id.productImage_fav);




        }
    }

}
