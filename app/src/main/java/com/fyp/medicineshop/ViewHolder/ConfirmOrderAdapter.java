package com.fyp.medicineshop.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.medicineshop.JavaClass.ConfirmOrders;
import com.fyp.medicineshop.R;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.MyconfirmViewHolder> {
    private Context mcontext;
    private List<ConfirmOrders> confirmOrdersList;
//    private OnImageClickListener xyz;
    public ConfirmOrderAdapter(Context mcontext, List<ConfirmOrders> confirmOrdersList) {
        this.mcontext = mcontext;
        this.confirmOrdersList = confirmOrdersList;
    }

    @NonNull
    @Override
    public MyconfirmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(mcontext).inflate(R.layout.orders_layout,viewGroup,false);

        return new MyconfirmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyconfirmViewHolder myconfirmViewHolder, final int position) {

        final ConfirmOrders co=confirmOrdersList.get(position);
        myconfirmViewHolder.userName.setText("Name: "+co.getName());
        myconfirmViewHolder.userPhone.setText("Phone: "+co.getPhone());
        myconfirmViewHolder.userproductName.setText("products"+co.getProductName());
        myconfirmViewHolder.userproductCode.setText("productCode"+co.getProductCode());
        myconfirmViewHolder.userAddress.setText("Address: "+co.getAddress()+" ,"+co.getCity());
        myconfirmViewHolder.userTotalprice.setText("Amount: " + co.getTotalamount());
        myconfirmViewHolder.userDateTime.setText("Order at :"+co.getDate()+","+co.getTime());
        myconfirmViewHolder.userDeliverydate.setText(co.getDeliveryDate());


    }

    @Override
    public int getItemCount() {
        return confirmOrdersList.size();
    }

    public class MyconfirmViewHolder extends RecyclerView.ViewHolder {
        private TextView userName,userPhone,userproductName,userproductCode,userTotalprice,userAddress,userDateTime,userDeliverydate;
        public MyconfirmViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.userName_orderList);
            userPhone=itemView.findViewById(R.id.phonenumber_orderList);
            userproductName=itemView.findViewById(R.id.ppppp);
            userproductCode=itemView.findViewById(R.id.productCode_orderList);
            userTotalprice=itemView.findViewById(R.id.totalPrice_orderList);
            userAddress=itemView.findViewById(R.id.address_orderList);
            userDateTime=itemView.findViewById(R.id.dateTime_orderList);
            userDeliverydate=itemView.findViewById(R.id.deliveryPickUpDate_orderList);



                }
}
}
