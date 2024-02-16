package com.zio.storey.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zio.storey.R;
import com.zio.storey.Utils;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.StoreDao;
import com.zio.storey.databinding.FragmentStatsBinding;

import java.util.List;

public class StatsFragment extends Fragment {


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentStatsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(getLayoutInflater());

        DataBase db = Room.databaseBuilder(getContext(), DataBase.class, "store").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        StoreDao storeDao = db.storeDao();

        //binding.result1.setText(storeDao.getProductInDemand().string_val);
        Utils.StringAndInt res = storeDao.getProductInDemand();
        binding.result1.setText(res.string_val + " sold " + res.int_val + " units");
        res = storeDao.getProfitableProduct();
        binding.result2.setText(res.string_val + " sold for total ₹" + res.int_val);
        String result = "Sold goods for total ₹" + storeDao.getTotalSale() + " this month";
        binding.result3.setText(result);

        List<String> l = storeDao.getLowStocks();
        result = "No Low Stocks";
        if (!l.isEmpty())
            result = "Products like '" + l.get(0) + "' and " + (l.size() - 1) + " others have very low stock";

        binding.result4.setText(result);


        return binding.getRoot();
    }
}