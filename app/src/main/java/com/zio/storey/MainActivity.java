package com.zio.storey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.zio.storey.databinding.ActivityMainBinding;
import com.zio.storey.ui.InventoryFragment;
import com.zio.storey.R;
import com.zio.storey.ui.InvoiceFragment;
import com.zio.storey.ui.StatsFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navigationView = binding.navView;

        Fragment inventoryFragment = new InventoryFragment();
        Fragment invoiceFragment = new InvoiceFragment();
        Fragment statsFragment = new StatsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, statsFragment).commit();

        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                final int id = item.getItemId();
                if (id == R.id.nav_inv)
                    fragment = invoiceFragment;
                else if (id == R.id.nav_store)
                    fragment = inventoryFragment;
                else fragment = statsFragment;

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();
                return true;
            }
        });


    }
}