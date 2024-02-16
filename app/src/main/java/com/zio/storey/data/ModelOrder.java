package com.zio.storey.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class ModelOrder {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "invid")
    private String inv_id;

    @NonNull
    @ColumnInfo(name = "pname")
    private String product_name;
    @ColumnInfo(name = "net")
    private int net;
    @ColumnInfo(name = "quantity")
    private int quantity;

    @Ignore
    private int maxi;

    @Ignore
    public ModelOrder() {
    }

    public ModelOrder(@NonNull long id, @NonNull String inv_id, @NonNull String product_name, int net, int quantity) {
        this.inv_id = inv_id;
        this.id = id;
        this.product_name = product_name;
        this.net = net;
        this.quantity = quantity;
    }

    @Ignore
    public ModelOrder(@NonNull String inv_id, @NonNull String product_name, int net, int quantity, int maxi) {
        this.inv_id = inv_id;
        this.product_name = product_name;
        this.net = net;
        this.quantity = quantity;
        this.maxi = maxi;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @NonNull
    public String getInv_id() {
        return inv_id;
    }

    public void setInv_id(@NonNull String inv_id) {
        this.inv_id = inv_id;
    }

    @NonNull
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(@NonNull String product_name) {
        this.product_name = product_name;
    }

    public int getMaxi() {
        return maxi;
    }

    public void setMaxi(int maxi) {
        this.maxi = maxi;
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
