package com.zio.storey.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.zio.storey.AdapterProducts;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.ProductsDAO;
import com.zio.storey.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {


    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    DataBase db;
    ProductsDAO productsDAO;
    private FragmentInventoryBinding binding;

    private RecyclerView recyclerView;

    ImageButton add, edit;

    private AdapterProducts adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        db = Room.databaseBuilder(getContext(), DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productsDAO = db.productsDAO();


        recyclerView = binding.productList;
        add = binding.addProd;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProductDetails.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();


    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new AdapterProducts(getContext(), productsDAO.getAll());
        recyclerView.setAdapter(adapter);
    }
}