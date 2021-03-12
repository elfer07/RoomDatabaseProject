package com.fer.diagrama.elfer.roomdatabaseproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Fernando Moreno on 12/3/2021.
 */

//define table name
@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //create text column
    @ColumnInfo(name = "text")
    private String text;

    //generate getter and setter


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
