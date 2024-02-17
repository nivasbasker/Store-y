package com.zio.storey.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zio.storey.util.Utils;
import com.zio.storey.data.DataBase;
import com.zio.storey.data.StoreDao;
import com.zio.storey.databinding.FragmentStatsBinding;

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

        if (storeDao.getTotalOrders() > 0) {
            Utils.StringAndInt res = storeDao.getProductInDemand();
            binding.result1.setText(res.string_val + " sold " + res.int_val + " units");

            res = storeDao.getProfitableProduct();
            binding.result2.setText(res.string_val + " sold for total ₹" + res.int_val);

            String result = "Sold goods for total ₹" + storeDao.getTotalSale() + " this month";
            binding.result3.setText(result);

            res = storeDao.getLowStocks();
            result = "No Low Stocks";
            if (res != null && res.int_val != 0 && !res.string_val.isEmpty())
                result = "Products like '" + res.string_val + "' and " + res.int_val + " others have very low stock";

            binding.result4.setText(result);
        }

        return binding.getRoot();
    }
}