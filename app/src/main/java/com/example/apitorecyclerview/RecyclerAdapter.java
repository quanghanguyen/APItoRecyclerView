package com.example.apitorecyclerview;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Data> arrayList;

    public RecyclerAdapter(ArrayList<Data> arrayList){
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.list_items, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        Data data = arrayList.get(position);
        holder.title.setText(data.getTitle());
        holder.message.setText(data.getMessage());

        holder.rlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);

                EditText edtName = dialog.findViewById(R.id.edtName);
                EditText edtEmail = dialog.findViewById(R.id.edtEmail);
                Button btnAdd = dialog.findViewById(R.id.btnAdd);
                TextView tvTitle = dialog.findViewById(R.id.tvAddUser);

                btnAdd.setText("Update");
                tvTitle.setText("Update Infomation");

                edtName.setText(arrayList.get(position).getTitle());
                edtName.setText(arrayList.get(position).getMessage());

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = "", email = "";

                        if (!edtName.getText().toString().equals("")) {
                            name = edtName.getText().toString();
                        } else {
                            Toast.makeText(context, "Please Enter Name!", Toast.LENGTH_SHORT).show();
                        }

                        if (!edtEmail.getText().toString().equals("")) {
                            email = edtEmail.getText().toString();
                        } else {
                            Toast.makeText(context, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                        }

                        arrayList.set(position, new Data(name, email));
                        notifyItemChanged(position);

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView message;
        RelativeLayout rlInfo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            message = (TextView) itemView.findViewById(R.id.tvMessage);
            rlInfo = (RelativeLayout)  itemView.findViewById(R.id.rlInfo);


        }
    }
}
