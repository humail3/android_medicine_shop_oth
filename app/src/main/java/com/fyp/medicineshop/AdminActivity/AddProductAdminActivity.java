package com.fyp.medicineshop.AdminActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.fyp.medicineshop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductAdminActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference productImagesRef;
    private FirebaseAuth mAuth;
    private DatabaseReference ProductsRef;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice,inputProductCode;
    private Uri imageUri;
    private String imagename, ImageUrl, saveCurrentDate, saveCurrentTime;
    private String CategoryName, productRandomKey, downloadImageUrl;
    private String Name, Description, Price, Code;
    private Button add_new_productBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_admin);
        setTitle("");
        add_new_productBtn = findViewById(R.id.add_new_product);
        CategoryName = getIntent().getExtras().get("category").toString();
        Toast.makeText(getApplicationContext(), CategoryName, Toast.LENGTH_SHORT).show();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("medicines");


//        productImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        mAuth = FirebaseAuth.getInstance();


        InputProductImage = findViewById(R.id.select_product_image);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductName = findViewById(R.id.product_name);
        InputProductPrice = findViewById(R.id.product_price);
        inputProductCode = findViewById(R.id.product_code);
        InputProductName.setText(CategoryName);

           }

    public void selectpic(View view) {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "select an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            InputProductImage.setImageURI(imageUri);
            imagename = getFileName(imageUri);

        }
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void disableAllTextViewAndenableLoading() {
        InputProductName.setEnabled(false);
        InputProductDescription.setEnabled(false);
        InputProductImage.setEnabled(false);
        InputProductPrice.setEnabled(false);
        inputProductCode.setEnabled(false);
        add_new_productBtn.setEnabled(false);
    }

    private void EnableAllTextViewAndDisableLoading() {
        InputProductName.setEnabled(true);
        InputProductDescription.setEnabled(true);
        InputProductImage.setEnabled(true);
        InputProductPrice.setEnabled(true);
        inputProductCode.setEnabled(true);
        add_new_productBtn.setEnabled(true);
    }

    public void savedata(View view) {
        disableAllTextViewAndenableLoading();
        Name = InputProductName.getText().toString().trim();
        Description = InputProductDescription.getText().toString().trim();
        Price = InputProductPrice.getText().toString().trim();
        Code = inputProductCode.getText().toString().trim();



        if (imageUri == null) {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
            EnableAllTextViewAndDisableLoading();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
            EnableAllTextViewAndDisableLoading();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
            EnableAllTextViewAndDisableLoading();
        } else if (TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
            EnableAllTextViewAndDisableLoading();

        } else if (TextUtils.isEmpty(Code)) {
            Toast.makeText(this, "Please write product Code", Toast.LENGTH_SHORT).show();
            EnableAllTextViewAndDisableLoading();
        } else {
            StoreProductInformation();
        }
    }


    private void StoreProductInformation() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + " " + saveCurrentTime;

        final StorageReference filepath = productImagesRef.child(imageUri.getLastPathSegment() + productRandomKey);

        final UploadTask uploadTask = filepath.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Product Image Upload Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downloadImageUrl = task.getResult().toString();
                        Toast.makeText(getApplicationContext(), "Image Url saved successfully", Toast.LENGTH_SHORT).show();
                        SaveProductInfoToDatabase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Occured!!!" + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
       // productMap.put("date", saveCurrentDate);
       // productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("name", Name);
        productMap.put("pcode", Code);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), AdminCategoryActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

