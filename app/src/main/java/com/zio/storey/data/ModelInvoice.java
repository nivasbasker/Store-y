package com.zio.storey.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "invoices")
public class ModelInvoice {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String inv_id;
    @ColumnInfo(name = "net")
    private int net;

    @ColumnInfo(name = "date")
    private String date;


    public ModelInvoice(@NonNull String inv_id, int net, String date) {
        this.inv_id = inv_id;
        this.net = net;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    public String getInv_id() {
        return inv_id;
    }

    public void setInv_id(@NonNull String inv_id) {
        this.inv_id = inv_id;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }


}
