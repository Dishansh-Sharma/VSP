//package com.example.vsp;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.health.connect.datatypes.ExerciseRoute;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.vsp.databinding.FragmentHomeBinding;
//import com.google.android.material.imageview.ShapeableImageView;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class HomeFragment extends Fragment {
//    private FragmentHomeBinding binding;
//
//    private static final String TAG = "Home_TAG";
//    private static final int MAX_DISTANCE_TO_LOAD_ADS_KM = 500;
//
//    private Context mContext;
//
//    private ArrayList<ModelAd> adArrayList;
//    private AdapterAd adapterAd;
//
//    public SharedPreferences locationsp;
//    double currentLatitude =0.0;
//    double currentLongitude =0.0;
//    private String currentAddress ="";
//
//
//
//    public  void onAttach(Context context) {
//
//        mContext = context;
//        super.onAttach(context);
//
//    }
//
//
//    public HomeFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        binding = FragmentHomeBinding.inflate(LayoutInflater.from(mContext), container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        locationsp = mContext.getSharedPreferences("LOCATION_SP", Context.MODE_PRIVATE);
//        currentLatitude = locationsp.getFloat("CURRENT_LATITUDE",0.0f);
//        currentLongitude = locationsp.getFloat("CURRENT_LONGITUDE",0.0f);
//        currentAddress = locationsp.getString("CURRENT_ADDRESS","");
//
//
//        if(currentLatitude !=0.0 && currentLongitude !=0.0){
//            binding.locationTv.setText(currentAddress);
//        }
//
//        loadCategories();
//        loadAds("All");
//
//        binding.searchEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                Log.d(TAG, "onTextChanged: Query: "+s);
//                try {
//                    String query = s.toString();
//                    adapterAd.getFilter().filter(query);
//                }catch (Exception e){
//                    Log.e(TAG, "onTextChanged: ",e);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        binding.locationCV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, LocationPickerActivity.class);
//                LocationPickerActivity.launch(intent);
//            }
//        });
//
//
//    }
//
//
//private ActivityResultLauncher<Intent> LocationPickerActivity = registerForActivityResult(
//        new ActivityResultContracts.StartActivityForResult(),
//        new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//
//                if(result.getResultCode() == Activity.RESULT_OK){
//                    Log.d(TAG, "onActivityResult: RESULT_OK");
//                    Intent data = result.getData();
//
//                    if(data != null){
//                        Log.d(TAG, "onActivityResult: Location Picked");
//                        currentLatitude = data.getDoubleExtra("latitude",0.0);
//                        currentLongitude = data.getDoubleExtra("longitude",0.0);
//                        currentAddress = data.getStringExtra("address");
//
//
//                        locationsp.edit()
//                                .putFloat("CURRENT_LATITUDE",Float.parseFloat(""+currentLatitude) )
//                                .putFloat("CURRENT_LONGITUDE",Float.parseFloat(""+currentLongitude) )
//                                .putString("CURRENT_ADDRESS",currentAddress)
//                                .apply();
////isko baad me un comment karna hai tab location picker implemrnt hojaya
////                        binding.locationCV.setText(currentAddress);
//                        loadAds("All");
//
//                    }
//
//                }
//                else{
//                    Log.d(TAG, "onActivityResult: Cancelled!...");
//                    Utils.toast(mContext, "Cancelled!...");
//                }
//
//            }
//        }
//
//
//);
//    private void loadAds(String category) {
//        Log.d(TAG, "loadAds: Category:"+category);
//        adArrayList = new ArrayList<>();
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                adArrayList.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    ModelAd modelAd = ds.getValue(ModelAd.class);
//                    if (category.equals("All") || modelAd.getCategory().equals(category)) {
//                        adArrayList.add(modelAd);
//                    }
//                }
//                adapterAd = new AdapterAd(mContext, adArrayList);
//                binding.adsRv.setAdapter(adapterAd);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(TAG, "onCancelled: ", error.toException());
//            }
//        });
//    }
//
//    private void loadCategories() {
//        ArrayList<ModelCategory> categoryArrayList = new ArrayList<>();
//
//        ModelCategory modelCategoryAll = new ModelCategory("All", R.drawable.ic_category_all);
//        categoryArrayList.add(modelCategoryAll);
//
//        for (int i =0;i<Utils.categories.length;i++){
//            ModelCategory modelCategory = new ModelCategory(Utils.categories[i], Utils.categoryIcons[i]);
//            categoryArrayList.add(modelCategory);
//        }
//        AdapterCategory adapterCategory = new AdapterCategory(mContext, categoryArrayList, new RvListenerCategory() {
//            @Override
//            public void onCategoryClick(ModelCategory modelCategory) {
//
//                loadAds(modelCategory.getCategory());
//
//            }
//        });
//        binding.categoriesRv.setAdapter(adapterCategory);
//
//    }
//
////
//    private void loadAds(String category) {
//        Log.d(TAG, "loadAds: Category:"+category);
//        adArrayList = new ArrayList<>();
//        DatabaseReference ref =FirebaseDatabase.getInstance().getReference("Ads");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                adArrayList.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    ModelAd modelAd = ds.getValue(ModelAd.class);
//                    double distance = calculateDistanceKm(modelAd.getLatitude(),modelAd.getLongitude());
//
//                    Log.d(TAG, "onDataChange: distance: "+distance);
//
//                    if (category.equals("All")) {
//                        if(distance <= MAX_DISTANCE_TO_LOAD_ADS_KM){
//                            adArrayList.add(modelAd);
//                        }
//
//                    }
//                    else{
//                        if(modelAd.getCategory().equals(category)){
//                            if(distance <= MAX_DISTANCE_TO_LOAD_ADS_KM){
//                                adArrayList.add(modelAd);
//                            }
//                        }
//                    }
//                    adapterAd = new AdapterAd(mContext, adArrayList);
//                    binding.adsRv.setAdapter(adapterAd);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//
//            }
//        });
//
//
//    }
//
//
//
//        private double calculateDistanceKm(double adLatitude, double adLongitude) {
//        Log.d(TAG, "calculateDistanceKm: currentLatitude "+currentLatitude);
//        Log.d(TAG, "calculateDistanceKm: currentLongitude "+currentLongitude);
//        Log.d(TAG, "calculateDistanceKm: adLatitude "+adLatitude);
//        Log.d(TAG, "calculateDistanceKm: adLongitude "+adLongitude);
//
//            Location startPoint = new Location(LocationManager.NETWORK_PROVIDER);
//            startPoint.setLatitude(currentLatitude);
//            startPoint.setLongitude(currentLongitude);
//            Location endPoint = new Location(LocationManager.NETWORK_PROVIDER);
//            endPoint.setLatitude(adLatitude);
//            endPoint.setLongitude(adLongitude);
//
//            double distanceInMeters = startPoint.distanceTo(endPoint);
//            double distanceInKm = distanceInMeters / 1000;
//            return distanceInKm;
//        }
//}
//






































































package com.example.vsp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.activities.LocationPickerActivity;
import com.example.vsp.adapters.AdapterAd;
import com.example.vsp.adapters.AdapterCategory;
import com.example.vsp.databinding.FragmentHomeBinding;
import com.example.vsp.models.ModelAd;
import com.example.vsp.models.ModelCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private static final String TAG = "Home_TAG";
    private static final int MAX_DISTANCE_TO_LOAD_ADS_KM = 500;

    private Context mContext;

    private ArrayList<ModelAd> adArrayList;
    private AdapterAd adapterAd;

    public SharedPreferences locationsp;
    float currentLatitude = 0.0f; // Changed to float
    float currentLongitude = 0.0f; // Changed to float
    private String currentAddress = "";

    private ActivityResultLauncher<Intent> locationPickerActivityLauncher;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(mContext), container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationsp = mContext.getSharedPreferences("LOCATION_SP", Context.MODE_PRIVATE);
        currentLatitude = locationsp.getFloat("CURRENT_LATITUDE", 0.0f);
        currentLongitude = locationsp.getFloat("CURRENT_LONGITUDE", 0.0f);
        currentAddress = locationsp.getString("CURRENT_ADDRESS", "");

        if (currentLatitude != 0.0 && currentLongitude != 0.0) {
            binding.locationTv.setText(currentAddress);
        }

        loadCategories();
        loadAds("All");

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: Query: " + s);
                try {
                    String query = s.toString();
                    adapterAd.getFilter().filter(query);
                } catch (Exception e) {
                    Log.e(TAG, "onTextChanged: ", e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.locationCV.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LocationPickerActivity.class);
            locationPickerActivityLauncher.launch(intent);
        });

        locationPickerActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Log.d(TAG, "onActivityResult: RESULT_OK");
                            Intent data = result.getData();

                            if (data != null) {
                                Log.d(TAG, "onActivityResult: Location Picked");
                                currentLatitude = data.getFloatExtra("latitude", 0.0f); // Changed to float
                                currentLongitude = data.getFloatExtra("longitude", 0.0f); // Changed to float
                                currentAddress = data.getStringExtra("address");

                                locationsp.edit()
                                        .putFloat("CURRENT_LATITUDE", currentLatitude)
                                        .putFloat("CURRENT_LONGITUDE", currentLongitude)
                                        .putString("CURRENT_ADDRESS", currentAddress)
                                        .apply();
                                binding.locationTv.setText(currentAddress); // Updated to set the text
                                loadAds("All");
                            }
                        } else {
                            Log.d(TAG, "onActivityResult: Cancelled!...");
                            Utils.toast(mContext, "Cancelled!...");
                        }
                    }
                });
    }

    private void loadAds(String category) {
        Log.d(TAG, "loadAds: Category:" + category);
        adArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelAd modelAd = ds.getValue(ModelAd.class);
                    double distance = calculateDistanceKm(modelAd.getLatitude(), modelAd.getLongitude());

                    Log.d(TAG, "onDataChange: distance: " + distance);

                    if (category.equals("All")) {
                        if (distance <= MAX_DISTANCE_TO_LOAD_ADS_KM) {
                            adArrayList.add(modelAd);
                        }
                    } else {
                        if (modelAd.getCategory().equals(category)) {
                            if (distance <= MAX_DISTANCE_TO_LOAD_ADS_KM) {
                                adArrayList.add(modelAd);
                            }
                        }
                    }
                }
                adapterAd = new AdapterAd(mContext, adArrayList);
                binding.adsRv.setAdapter(adapterAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
    }






    private void loadCategories() {
        ArrayList<ModelCategory> categoryArrayList = new ArrayList<>();

        ModelCategory modelCategoryAll = new ModelCategory("All", R.drawable.ic_category_all);
        categoryArrayList.add(modelCategoryAll);

        for (int i = 0; i < Utils.categories.length; i++) {
            ModelCategory modelCategory = new ModelCategory(Utils.categories[i], Utils.categoryIcons[i]);
            categoryArrayList.add(modelCategory);
        }
        AdapterCategory adapterCategory = new AdapterCategory(mContext, categoryArrayList, modelCategory -> loadAds(modelCategory.getCategory()));
        binding.categoriesRv.setAdapter(adapterCategory);
    }

    private double calculateDistanceKm(double adLatitude, double adLongitude) {
        Log.d(TAG, "calculateDistanceKm: currentLatitude " + currentLatitude);
        Log.d(TAG, "calculateDistanceKm: currentLongitude " + currentLongitude);
        Log.d(TAG, "calculateDistanceKm: adLatitude " + adLatitude);
        Log.d(TAG, "calculateDistanceKm: adLongitude " + adLongitude);

        Location startPoint = new Location(LocationManager.NETWORK_PROVIDER);
        startPoint.setLatitude(currentLatitude);
        startPoint.setLongitude(currentLongitude);
        Location endPoint = new Location(LocationManager.NETWORK_PROVIDER);
        endPoint.setLatitude(adLatitude);
        endPoint.setLongitude(adLongitude);

        double distanceInMeters = startPoint.distanceTo(endPoint);
        double distanceInKm = distanceInMeters / 1000;
        return distanceInKm;
    }
}
