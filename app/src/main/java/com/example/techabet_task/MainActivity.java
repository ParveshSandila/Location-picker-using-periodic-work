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

        checkPermission();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplication(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getListData(){
        list.clear();
        list=databaseHelper.getAllData();
        locationListAdapter=new LocationListAdapter(getApplication(),list);
        recyclerView.setAdapter(locationListAdapter);
        Toast.makeText(getApplication(),"Total List Size: "+list.size(),Toast.LENGTH_SHORT).show();
    }

    // Function to check and request permission
    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 101);
        }else{
            turnGPSOn();
            getListData();
            PeriodicWorkRequest locationRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 30, TimeUnit.MINUTES).build();
            WorkManager.getInstance().enqueue(locationRequest);
        }
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
