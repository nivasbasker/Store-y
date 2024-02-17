package com.zio.storey.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ModelProduct {

    @NonNull
    @ColumnInfo(name = "b_id")
    private String barcodeID;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String product_name;
    @NonNull
    @ColumnInfo(name = "price")
    private int net;

    @NonNull
    @ColumnInfo(name = "quantity")
    private int quantity;

    @Ignore
    public ModelProduct() {
    }

    public ModelProduct(@NonNull String barcodeID, @NonNull String product_name, int net, int quantity) {
        this.barcodeID = barcodeID;
        this.product_name = product_name;
        this.net = net;
        this.quantity = quantity;
    }

    @NonNull
    public String getBarcodeID() {
        return barcodeID;
    }

    public void setBarcodeID(@NonNull String barcodeID) {
        this.barcodeID = barcodeID;
    }

    @NonNull
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(@NonNull String product_name) {
        this.product_name = product_name;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
