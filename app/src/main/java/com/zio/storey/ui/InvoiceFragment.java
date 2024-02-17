package com.zio.storey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.zio.storey.util.AdapterInvoice;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.ModelInvoice;
import com.zio.storey.data.StoreDao;
import com.zio.storey.databinding.FragmentInvoicesBinding;

import java.util.List;

public class InvoiceFragment extends Fragment {

    private FragmentInvoicesBinding binding;

    private RecyclerView invoicesView;

    List<ModelInvoice> Invoices_list;

    ImageButton add;

    DataBase db;
    StoreDao dao;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInvoicesBinding.inflate(inflater, container, false);

        invoicesView = binding.invList;
        add = binding.addInv;

        db = Room.databaseBuilder(getContext(), DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        dao = db.storeDao();

        invoicesView.setLayoutManager(new LinearLayoutManager(getContext()));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InvoiceDetails.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        invoicesView.setAdapter(new AdapterInvoice(getContext(), dao.getAllInvoices()));
    }
}