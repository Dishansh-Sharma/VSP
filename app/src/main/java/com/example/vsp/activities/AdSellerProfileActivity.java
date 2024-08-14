package com.example.vsp.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.adapters.AdapterAd;
import com.example.vsp.databinding.ActivityAdSellerProfileBinding;
import com.example.vsp.models.ModelAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdSellerProfileActivity extends AppCompatActivity {


    private ActivityAdSellerProfileBinding binding;

    private static final String TAG = "AD_SELLER_PROFILE_TAG";

    private String sellerUid ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdSellerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_ad_seller_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        sellerUid = getIntent().getStringExtra("sellerUid");
        Log.d(TAG, "onCreate: sellerUid: "+sellerUid);


        loadSellerDetails();
        loadAds();


        binding.toolbarBackBtn.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void loadAds() {

        ArrayList<ModelAd> adArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");

        ref.orderByChild("uid").equalTo(sellerUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        adArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            try {
                                ModelAd modelAd = ds.getValue(ModelAd.class);

                                adArrayList.add(modelAd);
                            }catch (Exception e){
                                Log.e(TAG, "onDataChange: ", e);
                            }
                        }

                        AdapterAd adapterAd = new AdapterAd(AdSellerProfileActivity.this, adArrayList);
                        binding.adsRv.setAdapter(adapterAd);


                        String adsCount = ""+adArrayList.size();
                        binding.publishedAdsCountTv.setText(String.valueOf(adsCount));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void loadSellerDetails() {

        Log.d(TAG, "loadSellerDetails: ");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(sellerUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name = "" + snapshot.child("name").getValue();
                        String profileImageUrl = "" + snapshot.child("profileImageUrl").getValue();
                        long timestamp = (Long) snapshot.child("timestamp").getValue();


                        String formattedDate = Utils.formatTimestampDate(timestamp) ;

                        binding.sellerNameTv.setText(name);
                        binding.sellerMemberSinceTv.setText(formattedDate);


                        try{
                            Glide.with(AdSellerProfileActivity.this)
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.ic_person_white)
                                    .into(binding.sellerProfileiv);
                        }
                        catch (Exception e){
                            Log.e(TAG, "onDataChange: ", e);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}