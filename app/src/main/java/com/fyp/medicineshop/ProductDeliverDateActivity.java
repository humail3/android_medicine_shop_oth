package com.fyp.medicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProductDeliverDateActivity extends AppCompatActivity {
    TextView textViewDate;
    String selectedDate;
    Date today;
    String rentDate,future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deliver_date);
        setTitle("");

        textViewDate = findViewById(R.id.text_view_date);

        today = new Date();
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 3);


        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                calSelected.add(Calendar.DATE, 3);
                calSelected.add(Calendar.MONTH, 0);
                calSelected.add(Calendar.YEAR, 0);
                SimpleDateFormat sdf=new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());
                future = sdf.format(calSelected.getTime());

                rentDate = sdf.format(date);

                selectedDate = ("" + calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.YEAR));


                textViewDate.setText("Delivery Date: " + rentDate );
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void getDate(View v) {

        if (textViewDate.getText().toString().isEmpty())
        {
             Toast.makeText(getApplicationContext(),"Please Select Your Rental Date", Toast.LENGTH_SHORT).show();
        }
        else
            {
                Intent i=new Intent(getApplicationContext(), ConfirmOrderActivity.class);
                i.putExtra("name",getIntent().getStringExtra("name"));
                i.putExtra("no",getIntent().getStringExtra("no"));
                i.putExtra("delivery",rentDate);
                i.putExtra("pickup",future);
                startActivity(i);

            }


    }
}
