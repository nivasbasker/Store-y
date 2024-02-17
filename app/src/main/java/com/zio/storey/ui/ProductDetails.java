package com.zio.storey.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.zio.storey.R;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.ModelProduct;
import com.zio.storey.data.StoreDao;
import com.zio.storey.databinding.ActivityProductDetailsBinding;

public class ProductDetails extends AppCompatActivity {


    ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    public void scan(View view) {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_EAN_13).build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);
        scanner.startScan().addOnSuccessListener(barcode -> {
            if (barcode.getValueType() == Barcode.TYPE_PRODUCT)
                writeID(barcode.getRawValue());
            else writeID("");
        }).addOnCanceledListener(() -> {
            // Task canceled
            Log.d("TAG", "Cancelled by user");
            writeID("");
        }).addOnFailureListener(e -> {
            // Task failed with an exception
            Log.d("TAG", e.getMessage());
            writeID("");
        });

    }

    private void writeID(String rawValue) {
        if (rawValue.isEmpty())
            Snackbar.make(binding.getRoot(), "Unable to detect", Snackbar.LENGTH_LONG).show();
        else {
            binding.inp1.getEditText().setText(rawValue);
        }
    }

    public void add_product(View view) {

        String inp1 = binding.inp1.getEditText().getText().toString();
        String inp2 = binding.inp2.getEditText().getText().toString();
        String inp3 = binding.inp5.getEditText().getText().toString();
        String inp4 = binding.inp4.getEditText().getText().toString();
        if (!inp1.isEmpty() && !inp2.isEmpty() && !inp3.isEmpty() && !inp4.isEmpty()) {
            DataBase db = Room.databaseBuilder(this, DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            StoreDao dao = db.storeDao();

            ModelProduct model = new ModelProduct(inp1, inp2, Integer.parseInt(inp3), Integer.parseInt(inp4));

            dao.addProduct(model);
            db.close();
            Toast.makeText(this, "added successfully", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();


    }
}