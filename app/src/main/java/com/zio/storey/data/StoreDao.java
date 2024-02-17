package com.zio.storey.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zio.storey.util.Utils;

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

    @Query("SELECT name, COUNT(name) AS int_val FROM " + ProductsTable + " WHERE quantity <10 ORDER BY quantity LIMIT 1")
    Utils.StringAndInt getLowStocks();


    //for orders........
    @Insert
    void addOrder(ModelOrder... users);

    @Query("SELECT COUNT(*) FROM " + OrderTable)
    int getTotalOrders();


    //for products............
    @Query("SELECT * FROM " + ProductsTable)
    List<ModelProduct> getAllProducts();

    @Query("SELECT * FROM " + ProductsTable + " WHERE name = :Name ")
    ModelProduct FindProductByName(String Name);

    @Query("SELECT * FROM " + ProductsTable + " WHERE b_id = :bid ")
    ModelProduct FindProductByBID(String bid);

    @Insert
    void addProduct(ModelProduct... prods);

    @Update
    void updateProduct(ModelProduct... products);

    @Query("UPDATE " + ProductsTable + " SET quantity = quantity-1 WHERE name == :product_name")
    void decreaseProduct(String product_name);


    //for invoices...........
    @Query("SELECT * FROM " + InvoiceTable)
    List<ModelInvoice> getAllInvoices();

    @Insert
    void addInvoice(ModelInvoice... users);
}
