package com.example.healthcareapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.healthcareapp.databinding.ActivityLocationBinding;
import com.example.healthcareapp.model.Hospital;
import com.example.healthcareapp.model.Pharmacy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLocationBinding binding;
    double lat = 0, lng = 0;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button hospital, medicine;
    ImageView imgBackLocation;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(LocationActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hospital = findViewById(R.id.bt_search_hospital1);
        medicine = findViewById(R.id.bt_search_medicine_shop1);
        imgBackLocation = findViewById(R.id.imgbackLocation);
        imgBackLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                getCurrentLocation();
                mMap.setBuildingsEnabled(true);
                List<Hospital> listHospital = new ArrayList<>();
                firestore = FirebaseFirestore.getInstance();
                CollectionReference reference = firestore.collection("Hospital");
                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                Hospital h = new Hospital();
                                String name = doc.get("name").toString();
                                double lat1 = Double.parseDouble(doc.get("lat").toString());
                                double lg1 = Double.parseDouble(doc.get("lng").toString());
                                String phone = doc.get("phone").toString();
                                double distance = Math.sqrt((lat1 - lat) * (lat1 - lat) + (lg1 - lng) * (lg1 - lng));
                                h.setName(name);
                                h.setLng(lg1);
                                h.setLat(lat1);
                                h.setPhone(phone);
                                h.setDistance(distance * 1000000000);
                                listHospital.add(h);
                            }
                            Collections.sort(listHospital);
                        }
//                        Collections.sort(listHospital);
                        for (Hospital ho : listHospital.subList(0, 5)) {
                            LatLng HosLocation = new LatLng(ho.getLat(), ho.getLng());
                            mMap.addMarker(new MarkerOptions()
//                                    .icon(bitmapDescriptorFromVector(LocationActivity.this, R.drawable.ic_baseline_local_hospital_24))
                                    .position(new LatLng(ho.getLat(), ho.getLng()))
                                    .title(ho.getName()));

                        }
                        controlCam(new LatLng(listHospital.get(0).getLat(), listHospital.get(0).getLng()));

                    }

                });


            }
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                getCurrentLocation();
                List<Pharmacy> listPharma = new ArrayList<>();
                firestore = FirebaseFirestore.getInstance();
                CollectionReference reference = firestore.collection("Pharmacy");
                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : snapshot) {
                                Pharmacy ph = new Pharmacy();
                                String name = doc.get("name").toString();
                                double lat1 = Double.parseDouble(doc.get("lat").toString());
                                double lg1 = Double.parseDouble(doc.get("lng").toString());
                                double distance = Math.sqrt((lat1 - lat) * (lat1 - lat) + (lg1 - lng) * (lg1 - lng));
                                ph.setName(name);
                                ph.setLng(lg1);
                                ph.setLat(lat1);
                                ph.setDistance(distance * 1000000000);
                                Log.d("TAG", "onComplete Distance: " + distance);
                                listPharma.add(ph);
                            }
                            Collections.sort(listPharma);
                        }
                        for (Pharmacy ho : listPharma.subList(0, 5)) {
                            LatLng HosLocation = new LatLng(ho.getLat(), ho.getLng());
                            Log.d("TAG", "onMapReady Ho: " + ho.getLat() + ho.getLng());
                            mMap.addMarker(new MarkerOptions()
//                                    .icon(bitmapDescriptorFromVector(LocationActivity.this, R.drawable.ic_baseline_medical_services_24))
                                    .position(new LatLng(ho.getLat(), ho.getLng()))
                                    .title(ho.getName() + "\n"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HosLocation, 12));
                        }
                        controlCam(new LatLng(listPharma.get(0).getLat(), listPharma.get(0).getLng()));

                    }

                });


            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        getCurrentLocation();
        // Add a marker in Sydney and move the camera

    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        Log.d("TAG", "onComplete: lat = " + lat + " lng = " + lng);

                        LatLng currentLocation = new LatLng(lat, lng);
                        Log.d("TAG", "onMapReady: " + lat + lng);
                        mMap.addMarker(new MarkerOptions()
                                .icon(bitmapDescriptorFromVector(LocationActivity.this, R.drawable.ic_baseline_location_on_24))
                                .position(currentLocation)
                                .title("Here"));
                        Log.d("TAG", "onMapReady: " + currentLocation.longitude);
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                lat = location1.getLatitude();
                                lng = location1.getLongitude();
                                Log.d("TAG", "onLocationResult: lat = " + lat + " lng = " + lng);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest
                                , locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void controlCam(LatLng latlg) {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latlg)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

}