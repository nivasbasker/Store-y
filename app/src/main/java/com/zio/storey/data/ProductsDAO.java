package com.zio.storey.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductsDAO {

    String TableName = "products";

    @Query("SELECT * FROM " + TableName)
    List<ModelProduct> getAll();

    @Query("SELECT * FROM " + TableName + " WHERE pname LIKE :Name " +
            "LIMIT 1")
    ModelProduct findByName(String Name);

    @Insert
    void addProduct(ModelProduct... prods);

    @Update
    void updateProduct(ModelProduct... products);

    @Delete
    void deleteProduct(ModelProduct user);

    @Query("DELETE FROM " + TableName)
    void deleteAll();
}
