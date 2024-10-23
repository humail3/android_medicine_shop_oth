package com.fyp.medicineshop.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.medicineshop.JavaClass.UserProfile;
import com.fyp.medicineshop.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context mcontext;
    private List<UserProfile> userAdapterList;


    public UserAdapter(Context mcontext, List<UserProfile> userAdapterList) {
        this.mcontext = mcontext;
        this.userAdapterList = userAdapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(mcontext).inflate(R.layout.nav_header_home,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        UserProfile up=userAdapterList.get(position);


        myViewHolder.userName.setText(up.getUserName());
        myViewHolder.userEmail.setText(up.userEmail);

        Glide.with(mcontext).load(up.getImageUrl()).into(myViewHolder.userprofile);


    }

    @Override
    public int getItemCount() {
        return userAdapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName,userEmail;
        private CircleImageView userprofile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userEmail=itemView.findViewById(R.id.getUserEmail);
            userName=itemView.findViewById(R.id.getUserName);
            userprofile=itemView.findViewById(R.id.getUserProfile);
        }
    }
}
