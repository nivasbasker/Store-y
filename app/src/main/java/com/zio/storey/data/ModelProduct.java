package com.zio.storey.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Products")
public class ModelProduct {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pname")
    private String product_name;
    @ColumnInfo(name = "net")
    private int net;
    @ColumnInfo(name = "gst")
    private int gst;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @Ignore
    public ModelProduct() {
    }

    public ModelProduct(@NonNull String product_name, int net, int gst, int quantity) {
        this.product_name = product_name;
        this.net = net;
        this.gst = gst;
        this.quantity = quantity;
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

    public int getGst() {
        return gst;
    }

    public void setGst(int gst) {
        this.gst = gst;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
