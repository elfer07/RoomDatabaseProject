package com.fer.diagrama.elfer.roomdatabaseproject;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Created by Fernando Moreno on 12/3/2021.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //create constructor
    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //initialize main data
        MainData data = dataList.get(position);
        //initialize database
        database = RoomDB.getInstance(context);
        //set text on text viw
        holder.textView.setText(data.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //get id
                int sID = d.getID();
                //get text
                String sText = d.getText();
                //create dialog
                Dialog dialog = new Dialog(context);
                //set context view
                dialog.setContentView(R.layout.dialog_update);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set Layout
                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();

                //initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                //set text on edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dismiss dialog
                        dialog.dismiss();
                        //get update text from edit text
                        String uText = editText.getText().toString().trim();
                        //update text in database
                        database.mainDao().update(sID,uText);
                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //delete text from database
                database.mainDao().delete(d);
                //notifywhen data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
                Snackbar
                        .make(v, "ELiminado!", Snackbar.LENGTH_LONG)
                        .setAction("DESHACER", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.mainDao().insert(d);
                                notifyItemRangeChanged(position, dataList.size());
                                context.recreate();
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize variable
        TextView textView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
