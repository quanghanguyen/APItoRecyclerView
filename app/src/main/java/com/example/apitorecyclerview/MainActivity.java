package com.example.apitorecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView rcvData;
    private ArrayList<Data> arrayList;
    private FloatingActionButton btnOpenAdd;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenAdd = (FloatingActionButton) findViewById(R.id.btnOpenAdd);
        rcvData = (RecyclerView) findViewById(R.id.rcvData);
        arrayList = new ArrayList<>();

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getAllData();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e(TAG, "onResponse: code : " + response.code());
                ArrayList<Model.data> data = response.body().getData();

                for (Model.data data1 : data)
                {
                    Log.e(TAG, "onResponse: emails : " + data1.getEmail() );
                    arrayList.add(new Data(data1.getFirst_name(), data1.getEmail()));
                }

                rcvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                //here
                recyclerAdapter = new RecyclerAdapter(MainActivity.this, arrayList);
                rcvData.setAdapter(recyclerAdapter);



            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });

        btnOpenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update_lay);

                EditText edtName = dialog.findViewById(R.id.edtName);
                EditText edtEmail = dialog.findViewById(R.id.edtEmail);
                Button btnAdd = dialog.findViewById(R.id.btnAdd);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = "", email = "";

                        if (!edtName.getText().toString().equals("")) {
                            name = edtName.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Name!", Toast.LENGTH_SHORT).show();
                        }

                        if (!edtEmail.getText().toString().equals("")) {
                            email = edtEmail.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                        }

                        arrayList.add(new Data(name, email));

                        recyclerAdapter.notifyItemInserted(arrayList.size()-1);

                        rcvData.scrollToPosition(arrayList.size()-1);

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}