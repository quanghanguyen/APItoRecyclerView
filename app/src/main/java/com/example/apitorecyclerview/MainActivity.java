package com.example.apitorecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnCallAPI;
    private RecyclerView rcvData;
    private ArrayList<Data> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCallAPI = (Button) findViewById(R.id.callAPI);
        rcvData = (RecyclerView) findViewById(R.id.rcvData);
//      RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
//      rcvData.setAdapter(recyclerAdapter);
        arrayList = new ArrayList<>();


        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
                        rcvData.setAdapter(recyclerAdapter);
                        rcvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.getMessage());

                    }
                });


            }
        });

    }
}