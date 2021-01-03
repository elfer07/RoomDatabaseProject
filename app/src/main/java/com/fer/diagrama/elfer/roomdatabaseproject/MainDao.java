package com.fer.diagrama.elfer.roomdatabaseproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);
    
    //delete
    @Delete
    void delete(MainData mainData);

    //delete all query
    @Delete
    void reset(List<MainData> mainData);

    //update query
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //gget all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}
