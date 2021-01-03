package com.fer.diagrama.elfer.roomdatabaseproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//add database entities
@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //create deatabase instance
    private static RoomDB database;
    //define database name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
        //check condition
        if (database == null){
            //when database is null
            //initialize database
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDao mainDao();
}
