package com.example.daniel.uberapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Double> arrayLatitudes = new ArrayList<>();
    ArrayList<Double> arrayLongitudes = new ArrayList<>();
    ArrayList<String> username = new ArrayList<>();
    ListView listView;
    Location lastKnownLocation;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        setTitle("Pedidos próximos");

        arrayList.clear();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        arrayList.add("Pegando pedidos próximos...");

        listView = findViewById(R.id.requestList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Build.VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(RequestsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RequestsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(arrayLatitudes.size() > i && arrayLongitudes.size() > i && username.size() > i && lastKnownLocation != null) {
                        Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
                        intent.putExtra("requestLat", arrayLatitudes.get(i));
                        intent.putExtra("requestLong", arrayLongitudes.get(i));
                        intent.putExtra("driverLat", lastKnownLocation.getLatitude());
                        intent.putExtra("driverLong", lastKnownLocation.getLongitude());
                        intent.putExtra("username", username.get(i));
                        startActivity(intent);
                    }
                }
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
                ParseUser.getCurrentUser().saveInBackground();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            } else {
                //if we have permission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                measureDistance(lastKnownLocation);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //we don't have permission, so we ask for it
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //if we have permission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                measureDistance(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                measureDistance(lastKnownLocation);
            }
        }
    }

    public void measureDistance(Location location) {
        if(location != null) {
            final ParseGeoPoint geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

            arrayLatitudes.add(location.getLatitude());

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            query.whereNear("location", geoPoint);
            query.setLimit(5);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null) {
                        arrayList.clear();
                        arrayLatitudes.clear();
                        arrayLongitudes.clear();

                        if(objects.size() > 0) {
                            for(ParseObject object : objects) {
                                Double distanceInKm = geoPoint.distanceInKilometersTo(object.getParseGeoPoint("location"));
                                distanceInKm = (double) Math.round(distanceInKm * 10) / 10;
                                arrayList.add(distanceInKm.toString() + " km");
                                arrayLatitudes.add(object.getParseGeoPoint("location").getLatitude());
                                arrayLongitudes.add(object.getParseGeoPoint("location").getLongitude());
                                username.add(object.getString("username"));
                            }
                        } else {
                            arrayList.add("Sem pedidos ativos próximos");
                        }

                        listView.setAdapter(arrayAdapter);
                    } else {
                        Toast.makeText(RequestsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Where u at boy?", Toast.LENGTH_SHORT).show();
        }
    }
}
