package com.example.daniel.uberapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<Marker> markers = new ArrayList<>();
        mMap = googleMap;

        intent = getIntent();

        LatLng driverLocation = new LatLng(intent.getDoubleExtra("driverLat", 0), intent.getDoubleExtra("driverLong", 0));
        LatLng requestLocation = new LatLng(intent.getDoubleExtra("requestLat", 0), intent.getDoubleExtra("requestLong", 0));
        markers.add(mMap.addMarker(new MarkerOptions().position(driverLocation).title("Your location!")));
        markers.add(mMap.addMarker(new MarkerOptions().position(requestLocation).title("Request location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (height * 0.20); // offset from edges of the map 20% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        googleMap.animateCamera(cu);
    }

    public void acceptRequest(View view) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.whereEqualTo("username", intent.getStringExtra("username"));
        query.whereDoesNotExist("driverUsername");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    if(objects.size() > 0) {
                        for(ParseObject object : objects) {
                            object.put("driverUsername", ParseUser.getCurrentUser().getUsername());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        String driverLat = Double.toString(intent.getDoubleExtra("driverLat", 0));
                                        String driverLong = Double.toString(intent.getDoubleExtra("driverLong", 0));
                                        String requestLat = Double.toString(intent.getDoubleExtra("requestLat", 0));
                                        String requestLong = Double.toString(intent.getDoubleExtra("requestLong", 0));

                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                Uri.parse("http://maps.google.com/maps?saddr="+driverLat+","+driverLong+"&daddr="+requestLat+","+requestLong));
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(DriverActivity.this, "Couldn't find request, mate :/", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DriverActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
