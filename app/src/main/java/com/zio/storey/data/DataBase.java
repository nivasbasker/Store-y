package com.zio.storey.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ModelProduct.class, ModelInvoice.class, ModelOrder.class}, version = 1)
public abstract class DataBase extends RoomDatabase {


    public abstract StoreDao storeDao();

    public abstract ProductsDAO productsDAO();

    public abstract InvoicesDAO invoicesDAO();

    public abstract OrderDAO orderDAO();
}
