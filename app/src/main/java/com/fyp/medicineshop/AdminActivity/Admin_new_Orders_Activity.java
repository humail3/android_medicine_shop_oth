package com.fyp.medicineshop.AdminActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fyp.medicineshop.JavaClass.ConfirmOrders;
import com.fyp.medicineshop.R;

public class Admin_new_Orders_Activity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new__orders_);
        setTitle("");

        orderList = findViewById(R.id.rV_new_order);
        orderRef = FirebaseDatabase.getInstance().getReference().child("confirm order");
        orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ConfirmOrders> options =
                new FirebaseRecyclerOptions.Builder<ConfirmOrders>()
                        .setQuery(orderRef, ConfirmOrders.class)
                        .build();
        FirebaseRecyclerAdapter<ConfirmOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<ConfirmOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final ConfirmOrders model) {

                        holder.userName.setText("Name: " + model.getName());
                        holder.userPhone.setText("Phone: " + model.getPhone());
                        holder.userproductName.setText("products" + model.getProductName());
                        holder.userproductCode.setText("productCode" + model.getProductCode());
                        holder.userAddress.setText("Address: " + model.getAddress() + " ," + model.getCity());
                        holder.userTotalprice.setText("Amount: " + model.getTotalamount());
                        holder.userDateTime.setText("Order at :" + model.getDate() + "," + model.getTime());
                        holder.userDeliverydate.setText(model.getDeliveryDate());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence[] options = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_new_Orders_Activity.this);
                                builder.setTitle("Have you shipped this order products?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i == 0) {
                                            String uID = getRef(position).getKey();
                                            removeOrder(uID);
                                        } else {
                                            finish();
                                        }
                                    }


                                });
                                builder.show();
                            }
                        });
                    }


                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }

    private void removeOrder(String uID) {
        orderRef.child(uID).removeValue();
    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userPhone;
        private final TextView userproductName;
        private final TextView userproductCode;
        private final TextView userTotalprice;
        private final TextView userAddress;
        private final TextView userDateTime;
        private final TextView userDeliverydate;


        public AdminOrdersViewHolder(@NonNull View itemView) {

            super(itemView);

            userName = itemView.findViewById(R.id.userName_orderList);
            userPhone = itemView.findViewById(R.id.phonenumber_orderList);
            userproductName = itemView.findViewById(R.id.ppppp);
            userproductCode = itemView.findViewById(R.id.productCode_orderList);
            userTotalprice = itemView.findViewById(R.id.totalPrice_orderList);
            userAddress = itemView.findViewById(R.id.address_orderList);
            userDateTime = itemView.findViewById(R.id.dateTime_orderList);
            userDeliverydate = itemView.findViewById(R.id.deliveryPickUpDate_orderList);

        }
    }


}
