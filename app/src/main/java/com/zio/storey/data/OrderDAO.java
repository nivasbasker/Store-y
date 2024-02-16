package com.zio.storey.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDAO {

    String TableName = "orders";

    @Query("SELECT * FROM " + TableName)
    List<ModelOrder> getAll();

    @Query("SELECT * FROM " + TableName + " WHERE id LIKE :id " +
            "LIMIT 1")
    ModelOrder findById(String id);

    @Insert
    void addOrder(ModelOrder... users);

    @Delete
    void deleteOrder(ModelOrder user);

    @Query("DELETE FROM " + TableName)
    void deleteAll();
}
