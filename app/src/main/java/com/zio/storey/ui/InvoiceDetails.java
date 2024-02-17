package com.zio.storey.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.zio.storey.util.AdapterProducts;
import com.zio.storey.R;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.ModelInvoice;
import com.zio.storey.data.ModelOrder;
import com.zio.storey.data.ModelProduct;
import com.zio.storey.data.StoreDao;
import com.zio.storey.databinding.ActivityInvoiceDetailsBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceDetails extends AppCompatActivity {


    int bill;
    String date;

    TextView day, bill_num, net;
    EditText customer;


    SharedPreferences preferences;
    PopupWindow popupWindow = null;
    ActivityInvoiceDetailsBinding binding;


    List<ModelProduct> allProductsList;
    List<ModelOrder> orderList;
    RecyclerView orderView;
    RecyclerView searchResultRView;
    AdapterProducts searchResultAdapter;


    private int net_value = 0;

    private String INV_ID;


    DataBase db;
    private StoreDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        day = binding.day;
        bill_num = binding.bill;
        net = binding.net;
        customer = binding.inp2;
        orderView = binding.prodlist;

        orderList = new ArrayList<>();
        orderView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bill_num.setText("INVOICE #");

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        day.setText(date);
        preferences = getSharedPreferences("invoices", Context.MODE_PRIVATE);
        bill = preferences.getInt("total", 0);
        INV_ID = "INV" + bill;

        db = Room.databaseBuilder(this, DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        dao = db.storeDao();

        createPop();

        binding.addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
            }
        });

    }

    AdapterProducts.OnItemClickListener itemClickListener = new AdapterProducts.OnItemClickListener() {
        @Override
        public void onItemClick(ModelProduct product) {
            selectedProduct(product);
        }
    };

    private void selectedProduct(ModelProduct product) {
        if (product.getQuantity() < 1) {
            Toast.makeText(InvoiceDetails.this, "No stock !", Toast.LENGTH_SHORT).show();
            return;
        }
        popupWindow.dismiss();
        ModelOrder order = new ModelOrder(INV_ID, product.getProduct_name(), product.getNet(), 1, product.getQuantity());
        orderList.add(order);
        add_value(order);

        orderView.setAdapter(new AdapterProducts(orderList, InvoiceDetails.this, new AdapterProducts.QuantityChangeListener() {
            @Override
            public void onQuantityIncreased(ModelOrder order) {
                add_value(order);
            }
        }));
    }

    private void createPop() {

        allProductsList = dao.getAllProducts();

        popupWindow = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.popup_search, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setAnimationStyle(androidx.appcompat.R.style.Animation_AppCompat_Dialog);

        EditText search_place = popupWindow.getContentView().findViewById(R.id.search);

        searchResultRView = popupWindow.getContentView().findViewById(R.id.search_result);
        searchResultRView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        searchResultAdapter = new AdapterProducts(this, allProductsList);
        searchResultRView.setAdapter(searchResultAdapter);
        searchResultAdapter.setOnItemClickListener(itemClickListener);

        search_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search_for(editable.toString());
            }
        });

        popupWindow.getContentView().findViewById(R.id.img_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });

    }

    private void scan() {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_EAN_13).build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);
        scanner.startScan().addOnSuccessListener(barcode -> {
            if (barcode.getValueType() == Barcode.TYPE_PRODUCT)
                findID(barcode.getRawValue());
            else Snackbar.make(binding.getRoot(), "Unable to detect", Snackbar.LENGTH_LONG).show();
        }).addOnCanceledListener(() -> {
            // Task canceled
            Log.d("TAG", "Cancelled by user");
            Snackbar.make(binding.getRoot(), "Unable to detect", Snackbar.LENGTH_LONG).show();
        }).addOnFailureListener(e -> {
            // Task failed with an exception
            Log.d("TAG", e.getMessage());
            Snackbar.make(binding.getRoot(), "Unable to detect", Snackbar.LENGTH_LONG).show();
        });

    }

    private void findID(String id) {
        ModelProduct resultProduct = dao.FindProductByBID(id);
        if (resultProduct != null) {
            selectedProduct(resultProduct);

        } else Snackbar.make(binding.getRoot(), "Product not found", Snackbar.LENGTH_LONG).show();
    }


    private void search_for(String userInput) {
        List<ModelProduct> resultProds = new ArrayList<>();
        for (ModelProduct product : allProductsList)
            if (product.getProduct_name().contains(userInput))
                resultProds.add(product);

        searchResultAdapter.setData(resultProds);
    }

    private void add_value(ModelOrder order) {

        dao.decreaseProduct(order.getProduct_name());
        net_value += order.getNet();
        net.setText(String.valueOf(net_value));
    }

    public void save_inv(View view) {

        ModelInvoice invoice = new ModelInvoice(
                INV_ID,
                Integer.parseInt(net.getText().toString()),
                date);

        dao.addOrder(orderList.toArray(new ModelOrder[0]));

        dao.addInvoice(invoice);

        Toast.makeText(this, "saved successfully", Toast.LENGTH_LONG).show();
        preferences.edit().putInt("total", ++bill).apply();

    }

    public void print(View view) {

        Toast.makeText(this, "Feature coming soon...", Toast.LENGTH_LONG).show();
        String s = INV_ID + ".pdf";
        File f = new File(getFilesDir() + s);
        try {
            createPdf(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createPdf(File dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add content to the PDF
        document.add(new Paragraph("Hello, this is a simple PDF generated using iText!"));

        // Close the document
        document.close();
    }
}