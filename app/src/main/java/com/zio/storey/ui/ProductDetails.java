package com.zio.storey.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.slider.Slider;
import com.zio.storey.R;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.ModelProduct;
import com.zio.storey.data.ProductsDAO;

public class ProductDetails extends AppCompatActivity {

    EditText name, net, quantity;
    Slider gst;
    DataBase db;
    ProductsDAO productsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        name = findViewById(R.id.inp1);
        net = findViewById(R.id.inp3);
        gst = findViewById(R.id.inp4);
        quantity = findViewById(R.id.gross);

    }

    public void add_product(View view) {

        db = Room.databaseBuilder(this, DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productsDAO = db.productsDAO();

        ModelProduct model = new ModelProduct(name.getText().toString(),
                Integer.parseInt(net.getText().toString()),
                (int) gst.getValue(),
                Integer.parseInt(quantity.getText().toString()));

        productsDAO.addProduct(model);
        db.close();
        Toast.makeText(this, "added successfully", Toast.LENGTH_SHORT).show();

    }
}