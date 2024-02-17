package com.zio.storey.ui;

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
import android.widget.Toast;

import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.zio.storey.databinding.ActivityMainBinding;
import com.zio.storey.ui.InventoryFragment;
import com.zio.storey.R;
import com.zio.storey.ui.InvoiceFragment;
import com.zio.storey.ui.StatsFragment;

import java.util.Objects;

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

        look_for_scanner();
    }

    public void scan(View view) {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_EAN_13).build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);
        scanner.startScan().addOnSuccessListener(barcode -> {
            Log.d("TAG", barcode.getRawValue());
        }).addOnCanceledListener(() -> {
            // Task canceled
            Log.d("TAG", "Cancelled by user");
        }).addOnFailureListener(e -> {
            // Task failed with an exception
            Log.d("TAG", e.getMessage());
        });

    }

    private void look_for_scanner() {
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(this);
        OptionalModuleApi optionalModuleApi = GmsBarcodeScanning.getClient(this);
        ModuleInstallRequest moduleInstallRequest =
                ModuleInstallRequest.newBuilder()
                        .addApi(optionalModuleApi)
                        .build();
        moduleInstallClient.installModules(moduleInstallRequest)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAlreadyInstalled()) {
                                // Modules are already installed when the request is sent.
                                Log.d("TAG", "installed");
                            } else Log.d("TAG", "installing");
                        })
                .addOnFailureListener(
                        e -> {
                            Log.d("TAG", e.getMessage());
                            // Handle failure...
                        });
        /*moduleInstallClient.areModulesAvailable(optionalModuleApi)
                .addOnSuccessListener(
                        response -> {

                            if (!response.areModulesAvailable()) {
                                // Modules are not present on the device...
                                Log.d("TAG", "Trying to get GSM package");
                                moduleInstallClient.deferredInstall(optionalModuleApi).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG", "installing");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", Objects.requireNonNull(e.getMessage()));

                                    }
                                });
                            }
                        });*/
    }


}