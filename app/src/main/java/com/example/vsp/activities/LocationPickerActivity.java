package com.example.vsp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.databinding.ActivityLocationPickerBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class LocationPickerActivity extends AppCompatActivity implements OnMapReadyCallback {



    private ActivityLocationPickerBinding binding;
    private  static final  String TAG ="LOCATION_PICKER_TAG";

    private static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap = null;
    private PlacesClient mPlacesClient = null;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation = null;
    private Double selectedLatitude = null;
    private Double selectedLongitude = null;
    private String selectedAddress = "";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);

        binding = ActivityLocationPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding.doneLl.setVisibility(View.GONE);


        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);


        Places.initialize(this, getString(R.string.google_map_api_key));


        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        Place.Field[] placesList = new Place.Field[]{Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG};


        autocompleteSupportFragment.setPlaceFields(Arrays.asList(placesList));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: status: "+status);

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {

                String id = place.getId();
                String title = place.getName();
                LatLng latLng = place.getLatLng();
                selectedLatitude = latLng.latitude;
                selectedLongitude = latLng.longitude;
                selectedAddress = place.getAddress();


                Log.d(TAG, "onPlaceSelected: ID: " + id);
                Log.d(TAG, "onPlaceSelected: Title: " + title);
                Log.d(TAG, "onPlaceSelected: Latitude: " + selectedLatitude);
                Log.d(TAG, "onPlaceSelected: Longitude: " + selectedLongitude);
                Log.d(TAG, "onPlaceSelected: Address: " + selectedAddress);


                addMarker(latLng, title,selectedAddress);


            }
        });


        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.toolbarGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGPSEnabled()) {
                    requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    Utils.toast(LocationPickerActivity.this, "Location is not on! Turn it on to show your current location...");
                }
            }
        });



        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("latitude", selectedLatitude);
                intent.putExtra("longitude", selectedLongitude);
                intent.putExtra("address", selectedAddress);
                setResult(RESULT_OK, intent);
                finish();
            }
        });





    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");
        mMap = googleMap;


        requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                selectedLatitude = latLng.latitude;
                selectedLongitude = latLng.longitude;

                Log.d(TAG, "onMapClick: Latitude: " + selectedLatitude);
                Log.d(TAG, "onMapClick: Longitude: " + selectedLongitude);


                addressFromLng(latLng);
            }
        });


    }

    @SuppressLint("MissingPermission")
    public ActivityResultLauncher<String> requestLocationPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {

                @Override
                public void onActivityResult(Boolean isGranted) {
                    Log.d(TAG, "onActivityResult: ");
                    if (isGranted) {
                        mMap.setMyLocationEnabled(true);
                        pickCurrentPlace();
                    } else {
                        Utils.toast(LocationPickerActivity.this, "Permission Denied!...");
                    }

                }
            }
    );

    private  void addressFromLng(LatLng latLng){
        Log.d(TAG, "addressFromLng: ");
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList =geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            Address address =addressList.get(0);

            String addressLine = address.getAddressLine(0);
            String countryName = address.getCountryName();
            String adminArea = address.getAdminArea();
            String subAdminArea = address.getSubLocality();
            String locality = address.getLocality();
            String subLocality = address.getSubLocality();
            String postalCode = address.getPostalCode();


            selectedAddress = ""+addressLine;
            addMarker(latLng,""+subLocality,""+addressLine);

        }
        catch (Exception e) {
            Log.e(TAG, "addressFromLng: ", e);
        }
    }


    private void pickCurrentPlace() {
        Log.d(TAG, "pickCurrentPlace: ");
        if(mMap == null) {
            return;
        }
        detectAndShowDeviceLocationMAP();
    }


    @SuppressLint("MissingPermission")
    private void  detectAndShowDeviceLocationMAP() {

        try{
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        mLastKnownLocation = location;
                        selectedLatitude = location.getLatitude();
                        selectedLongitude = location.getLongitude();
                        Log.d(TAG, "onSuccess: Latitude: " + selectedLatitude);
                        Log.d(TAG, "onSuccess: Longitude: " + selectedLongitude);


                        LatLng latLng = new LatLng(selectedLatitude, selectedLongitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));

                        addressFromLng(latLng);
                    }
                    else {
                        Log.d(TAG, "onSuccess: Location is null...");
                    }



                }
            })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " , e);

            }
        });


        }
        catch (Exception e) {
            Log.e(TAG, "detectAndShowDeviceLocationMAP: " , e);
        }

    }


    private  boolean isGPSEnabled() {
        LocationManager lm =(LocationManager) getSystemService(LOCATION_SERVICE);

        boolean getEnabled = false;
        boolean networkEnabled = false;
        try{
            getEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e) {
            Log.e(TAG, "isGPSEnabled: " , e);
        }
        try{
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception e) {
            Log.e(TAG, "isGPSEnabled: " , e);

        }

        return !(!getEnabled && !networkEnabled);
    }
    private void addMarker(LatLng latLng, String title, String address) {
        Log.d(TAG,"addMarker: latitude: " + latLng.latitude);
        Log.d(TAG,"addMarker: longitude: " + latLng.longitude);
        Log.d(TAG,"addMarker: title: " + title);
        Log.d(TAG,"addMarker: address: " + address);

        mMap.clear();


        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(""+title);
            markerOptions.snippet(""+address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

            binding.doneLl.setVisibility(View.VISIBLE);
            binding.selectedPlaceTv.setText(address);
        }
        catch (Exception e) {
            Log.e(TAG, "addMarker: " , e);

        }

    }

}