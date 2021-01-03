package com.fer.diagrama.elfer.roomdatabaseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables
        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //initialize database
        database = RoomDB.getInstance(this);
        //store database value in data list
        dataList = database.mainDao().getAll();

        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //initialize adapter
        adapter = new MainAdapter(MainActivity.this, dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string from edit text
                String sText = editText.getText().toString().trim();
                //check condition
                if (!sText.equals("")) {
                    //when text is not empty initialize main data
                    MainData data = new MainData();
                    //set text on main dat
                    data.setText(sText);
                    //insert text in database
                    database.mainDao().insert(data);
                    //clear edit text
                    editText.setText("");
                    //notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all data from database
                database.mainDao().reset(dataList);
                //notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}