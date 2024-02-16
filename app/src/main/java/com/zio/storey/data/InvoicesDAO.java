package com.zio.storey.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InvoicesDAO {

    String TableName = "invoices";

    @Query("SELECT * FROM " + TableName)
    List<ModelInvoice> getAll();

    @Query("SELECT * FROM " + TableName + " WHERE id LIKE :inv " +
            "LIMIT 1")
    ModelInvoice findById(String inv);

    @Insert
    void addInvoice(ModelInvoice... users);

    @Delete
    void deleteInvoice(ModelInvoice user);

    @Query("DELETE FROM " + TableName)
    void deleteAll();
}
