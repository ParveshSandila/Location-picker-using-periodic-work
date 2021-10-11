package com.example.techabet_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.techabet_task.adapters.LocationListAdapter;
import com.example.techabet_task.models.LocationDataModel;
import com.example.techabet_task.utils.DatabaseHelper;
import com.example.techabet_task.utils.MyWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LocationListAdapter locationListAdapter;
    List<LocationDataModel> list=new ArrayList<>();
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        databaseHelper=new DatabaseHelper(getApplication());

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplication(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getListData();

        PeriodicWorkRequest locationRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 30, TimeUnit.MINUTES).build();
        WorkManager.getInstance().enqueue(locationRequest);
    }

    private void getListData(){
        list.clear();
        list=databaseHelper.getAllData();
        locationListAdapter=new LocationListAdapter(getApplication(),list);
        recyclerView.setAdapter(locationListAdapter);
        Toast.makeText(getApplication(),"Total List Size: "+list.size(),Toast.LENGTH_SHORT).show();
    }
}