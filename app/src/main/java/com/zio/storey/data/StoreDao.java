package com.zio.storey.data;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zio.storey.Utils;

import java.util.List;

@Dao
public interface StoreDao {
    String OrderTable = "orders", InvoiceTable = "invoices", ProductsTable = "products";

    //For stats ..........

    @Query("SELECT pname AS string_val, SUM(quantity) AS int_val " +
            "FROM " + OrderTable +
            " GROUP BY pname " +
            "ORDER BY int_val DESC " +
            "LIMIT 1;")
    Utils.StringAndInt getProductInDemand();

    @Query("SELECT pname AS string_val, SUM(quantity*net) AS int_val " +
            "FROM " + OrderTable +
            " GROUP BY pname " +
            "ORDER BY int_val DESC " +
            "LIMIT 1;")
    Utils.StringAndInt getProfitableProduct();

    @Query("SELECT SUM(net) FROM " + InvoiceTable)
    String getTotalSale();

    @Query("SELECT pname FROM " + ProductsTable + " WHERE quantity <10 ORDER BY quantity")
    List<String> getLowStocks();

    //for orders........
    @Insert
    void addOrder(ModelOrder... users);


    //for products............
    @Query("SELECT * FROM " + ProductsTable)
    List<ModelProduct> getAllProducts();

    @Query("SELECT * FROM " + ProductsTable + " WHERE pname LIKE :Name " +
            "LIMIT 1")
    ModelProduct FindProduct(String Name);

    @Insert
    void addProduct(ModelProduct... prods);

    @Update
    void updateProduct(ModelProduct... products);

    //for invoices...........
    @Query("SELECT * FROM " + InvoiceTable)
    List<ModelInvoice> getAllInvoices();

    @Insert
    void addInvoice(ModelInvoice... users);
}
