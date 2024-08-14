//package com.example.vsp.activities;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Adapter;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.bumptech.glide.Glide;
//import com.example.vsp.R;
//import com.example.vsp.Utils;
//import com.example.vsp.adapters.AdapterImageSlider;
//import com.example.vsp.databinding.ActivityAdDetailsBinding;
//import com.example.vsp.databinding.ActivityProfileEditBinding;
//import com.example.vsp.models.ModelAd;
//import com.example.vsp.models.ModelImageSlider;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.dialog.MaterialAlertDialogBuilder;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class AdDetailsActivity extends AppCompatActivity {
//
//    private ActivityAdDetailsBinding binding;
//    private  static final  String TAG ="AD_DETAILS_TAG";
//    private ProgressDialog progressDialog;
//    private FirebaseAuth firebaseAuth;
//    private String adId="";
//
//    private double adLatitude =0;
//    private double adLongitude =0;
//
//    private String sellerUid=null;
//    private String sellerPhone="";
//
//    private boolean favorite=false;
//
//    private ArrayList<ModelImageSlider> imageSliderArrayList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        EdgeToEdge.enable(this);
////        setContentView(R.layout.activity_ad_details);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//
//
//
//        binding =ActivityAdDetailsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.toolbarBackBtn.setVisibility(View.GONE);
//        binding.toolbarDeleteBtn.setVisibility(View.GONE);
//        binding.callBtn.setVisibility(View.GONE);
//        binding.chatBtn.setVisibility(View.GONE);
//        binding.smsBtn.setVisibility(View.GONE);
//
//        adId =getIntent().getStringExtra("adId");
//
//        Log.d(TAG,"onCreated: adId: "+adId);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//
//        if(firebaseAuth.getCurrentUser() !=null){
//            checkIsFavorite();
//        }
//
//        loadAdDetails();
//        loadAdImages();
//
//        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//        binding.toolbarDeleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MaterialAlertDialogBuilder materialAlertBuilder = new MaterialAlertDialogBuilder(AdDetailsActivity.this);
//
//                materialAlertBuilder.setTitle("Delete Ad")
//                        .setMessage("Are you sure want to delete this Ad?")
//                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int which){
//                                deleteAd();
//
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        });
//
//        binding.toolbarEditBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.toast(AdDetailsActivity.this, "Edit Clicked...");
//            }
//        });
//
//
//        binding.toolbarDeleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(favorite){
//                    Utils.removeFavourite(AdDetailsActivity.this, adId);
//                }else {
//                    Utils.addToFavourite(AdDetailsActivity.this, adId);
//                }
//            }
//        });
//
//
//        binding.sellerProfileCv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        binding.callBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        binding.callBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Utils.callIntent(AdDetailsActivity.this, sellerPhone);
//            }
//        });
//
//
//        binding.smsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.smsIntent(AdDetailsActivity.this, sellerPhone);
//            }
//        });
//
//        binding.mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.mapIntent(AdDetailsActivity.this, adLatitude, adLongitude);
//            }
//        });
//
//    }
//
//
//    private void showMarkAsSoldDialog(){
//        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this);
//        alertBuilder.setTitle("Mark as Sold")
//                .setMessage("Are you sure you want to mark this ad as sold?")
//                .setPositiveButton("Sold", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        Log.d(TAG, "onClick: Sold Clicked...");
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("status", ""+ Utils.AD_STATUS_SOLD);
//
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
//                        ref.child(adId)
//                                .updateChildren(hashMap)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        // Handle success
//                                        Log.d(TAG, "onSuccess: Mark as Sold");
//                                        Utils.toast(AdDetailsActivity.this, "Mark as Sold");
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        // Handle failure
//                                        Log.e(TAG, "onFailure: " , e);
//                                        Utils.toast(AdDetailsActivity.this, "Failed to Mark as Sold due to "+e.getMessage());
//
//                                    }
//                                });
//
//
//
//                    }
//                })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d(TAG, "onClick: CANCEL Clicked...");
//                        dialog.dismiss();
//
//
//                    }
//                })
//                .show();
//
//    }
//
//    private void loadAdDetails(){
//        Log.d(TAG, "loadAdDetails: ");
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
//        ref.child(adId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        try {
//                            ModelAd modelAd = snapshot.getValue(ModelAd.class);
//
//                            sellerUid = modelAd.getUid();
//                            String title = modelAd.getTitle();
//                            String description = modelAd.getDescription();
//                            String address = modelAd.getAddress();
//                            String price = modelAd.getPrice();
//                            String condition = modelAd.getCondition();
//                            long timestamp = modelAd.getTimestamp();
//                            String formattedDate = Utils.formatTimestampDate(timestamp);
//
//                            adLatitude = modelAd.getLatitude();
//                            adLongitude = modelAd.getLongitude();
//
//                            if(sellerUid.equals(firebaseAuth.getUid())){
//
//                                binding.toolbarEditBtn.setVisibility(View.VISIBLE);
//                                binding.toolbarDeleteBtn.setVisibility(View.VISIBLE);
//                                binding.callBtn.setVisibility(View.VISIBLE);
//                                binding.chatBtn.setVisibility(View.VISIBLE);
//                                binding.smsBtn.setVisibility(View.VISIBLE);
//
//                            }else {
//                                binding.toolbarEditBtn.setVisibility(View.VISIBLE);
//                                binding.toolbarDeleteBtn.setVisibility(View.VISIBLE);
//                                binding.callBtn.setVisibility(View.VISIBLE);
//                                binding.chatBtn.setVisibility(View.VISIBLE);
//                                binding.smsBtn.setVisibility(View.VISIBLE);
//                            }
//
//
//                            binding.titleTv.setText(title);
//                            binding.descriptionTv.setText(description);
//                            binding.addressTv.setText(address);
//                            binding.priceTv.setText(price);
//                            binding.dateTv.setText(formattedDate);
//                            binding.conditionTv.setText(condition);
//
//
//                            loadSellerDetails();
//                        }
//                        catch (Exception e){
//                            Log.e(TAG, "onDataChange: ", e);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//    }
//    private void loadSellerDetails(){
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(sellerUid)
//                .addValueEventListener(new ValueEventListener() {
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        String name = ""+snapshot.child("name").getValue();
//                        String phoneNumber = ""+snapshot.child("phoneNumber").getValue();
//                        String profileImageUrl = ""+snapshot.child("profileImageUrl").getValue();
//                        long timestamp = (Long)snapshot.child("timestamp").getValue();
//                        String phoneCode = ""+snapshot.child("phoneCode").getValue();
//
//                        String formattedDate = Utils.formatTimestampDate(timestamp);
//                        sellerPhone = phoneCode+""+phoneNumber;
//
//                        binding.sellerNameTv.setText(name);
//                        binding.memberSinceTv.setText(formattedDate);
//
//                        try {
//                            Glide.with(AdDetailsActivity.this)
//                                    .load(profileImageUrl)
//                                    .placeholder(R.drawable.ic_person_white)
//                                    .into(binding.sellerProfileIv);
//                        }
//                        catch (Exception e){
//                            Log.e(TAG, "onDataChange: ", e);
//                        }
//
//
//                    }
//
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//    }
//
//    private void checkIsFavorite(){
//        Log.d(TAG,"checkIsFavorite: ");
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.child(firebaseAuth.getUid()).child("Favorites").child(adId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        favorite = snapshot.exists();
//                        Log.d(TAG, "onDataChange: " + favorite);
//                        if (favorite) {
//                            binding.toolbarFavBtn.setImageResource(R.drawable.ic_fav_yes);
//                        } else {
//                            binding.toolbarFavBtn.setImageResource(R.drawable.ic_fav_no);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }
//
//    private void loadAdImages(){
//
//        Log.d(TAG,"loadAdImages: ");
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
//        ref.child(adId).child("Images")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        imageSliderArrayList.clear();
//                        for(DataSnapshot ds: snapshot.getChildren()){
//                            ModelImageSlider modelImageSlider = ds.getValue(ModelImageSlider.class);
//                            imageSliderArrayList.add(modelImageSlider);
//                        }
//
//                        AdapterImageSlider adapterImageSlider = new AdapterImageSlider(AdDetailsActivity.this, imageSliderArrayList);
//                        binding.imageSliderVp.setAdapter(adapterImageSlider);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//    }
//
//    private void deleteAd(){
//        Log.d(TAG,"deleteAd: ");
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
//        ref.child(adId)
//                .removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: Ad Deleted...");
//                        Utils.toast(AdDetailsActivity.this, "Ad Deleted...");
//                        finishAffinity();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: ", e);
//                        Utils.toast(AdDetailsActivity.this, "Failed to Delete due to "+e.getMessage());
//                    }
//                });
//    }
//
//
//
//}



























































































package com.example.vsp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.adapters.AdapterImageSlider;
import com.example.vsp.databinding.ActivityAdDetailsBinding;
import com.example.vsp.models.ModelAd;
import com.example.vsp.models.ModelImageSlider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdDetailsActivity extends AppCompatActivity {

    private ActivityAdDetailsBinding binding;
    private static final String TAG = "AD_DETAILS_TAG";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String adId = "";

    private double adLatitude = 0;
    private double adLongitude = 0;

    private String sellerUid = null;
    private String sellerPhone = "";

    private boolean favorite = false;

    private ArrayList<ModelImageSlider> imageSliderArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarBackBtn.setVisibility(View.GONE);
        binding.toolbarDeleteBtn.setVisibility(View.GONE);
        binding.callBtn.setVisibility(View.GONE);
        binding.chatBtn.setVisibility(View.GONE);
//        binding.smsBtn.setVisibility(View.GONE);

        adId = getIntent().getStringExtra("adId");

        Log.d(TAG, "onCreated: adId: " + adId);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            checkIsFavorite();
        }

        loadAdDetails();
        loadAdImages();

        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        binding.toolbarDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertBuilder = new MaterialAlertDialogBuilder(AdDetailsActivity.this);

                materialAlertBuilder.setTitle("Delete Ad")
                        .setMessage("Are you sure want to delete this Ad?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAd();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        binding.toolbarEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(AdDetailsActivity.this, "Edit Clicked...");
                editOptions();
            }
        });

        binding.toolbarFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite) {
                    Utils.removeFavourite(AdDetailsActivity.this, adId);
                } else {
                    Utils.addToFavourite(AdDetailsActivity.this, adId);
                }
            }
        });

        binding.sellerProfileCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle seller profile click

                Intent intent = new Intent(AdDetailsActivity.this, AdSellerProfileActivity.class);
                intent.putExtra("sellerUid", sellerUid);
                startActivity(intent);
            }
        });

        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.callIntent(AdDetailsActivity.this, sellerPhone);
            }
        });

//        binding.smsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.smsIntent(AdDetailsActivity.this, sellerPhone);
//            }
//        });

        binding.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mapIntent(AdDetailsActivity.this, adLatitude, adLongitude);
            }
        });

        binding.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdDetailsActivity.this, ChatActivity.class);
                intent.putExtra("receiptUid", sellerUid);
                startActivity(intent);
            }
        });
    }

    private void editOptions() {
        Log.d(TAG, "editOptions: ");


        PopupMenu popupMenu = new PopupMenu(this, binding.toolbarEditBtn);
        popupMenu.getMenu().add(0, 0, 0, "Edit");
        popupMenu.getMenu().add(0, 1, 0, "Mark as Sold");
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == 0) {
                    Intent intent = new Intent(AdDetailsActivity.this, AdCreateActivity.class);
                    intent.putExtra("isEditMode", true);
                    intent.putExtra("adId", adId);
                    startActivity(intent);
                }
                else if (itemId == 1) {
                    showMarkAsSoldDialog();

                }
                return false;

            }
        });
    }

    private void showMarkAsSoldDialog() {
        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(this);
        alertBuilder.setTitle("Mark as Sold")
                .setMessage("Are you sure you want to mark this ad as sold?")
                .setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Sold Clicked...");
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "" + Utils.AD_STATUS_SOLD);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
                        ref.child(adId)
                                .updateChildren(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: Mark as Sold");
                                        Utils.toast(AdDetailsActivity.this, "Mark as Sold");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "onFailure: ", e);
                                        Utils.toast(AdDetailsActivity.this, "Failed to Mark as Sold due to " + e.getMessage());
                                    }
                                });
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: CANCEL Clicked...");
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void loadAdDetails() {
        Log.d(TAG, "loadAdDetails: ");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        ref.child(adId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            ModelAd modelAd = snapshot.getValue(ModelAd.class);

                            sellerUid = modelAd.getUid();
                            String title = modelAd.getTitle();
                            String description = modelAd.getDescription();
                            String address = modelAd.getAddress();
                            String price = modelAd.getPrice();
                            String condition = modelAd.getCondition();
                            String category = modelAd.getCategory();
                            Long timestamp = modelAd.getTimestamp();
                            String formattedDate =Utils.formatTimestampDate(timestamp) ;

//                            adLatitude = modelAd.getLatitude();
//                            adLongitude = modelAd.getLongitude();

                            if (sellerUid.equals(firebaseAuth.getUid())) {
                                binding.toolbarEditBtn.setVisibility(View.VISIBLE);
                                binding.toolbarDeleteBtn.setVisibility(View.VISIBLE);
                                binding.callBtn.setVisibility(View.VISIBLE);
                                binding.chatBtn.setVisibility(View.VISIBLE);
//                                binding.smsBtn.setVisibility(View.VISIBLE);

                                binding.sellerProfileLabelTv.setVisibility(View.GONE);
                                binding.sellerProfileCv.setVisibility(View.GONE);
                            } else {
                                binding.toolbarEditBtn.setVisibility(View.GONE);
                                binding.toolbarDeleteBtn.setVisibility(View.GONE);
                                binding.callBtn.setVisibility(View.VISIBLE);
                                binding.chatBtn.setVisibility(View.VISIBLE);
//                                binding.smsBtn.setVisibility(View.VISIBLE);

                                binding.sellerProfileLabelTv.setVisibility(View.GONE);
                                binding.sellerProfileCv.setVisibility(View.GONE);
                            }

                            binding.titleTv.setText(title);
                            binding.descriptionTv.setText(description);
                            binding.addressTv.setText(address);
                            binding.priceTv.setText(price);
                            binding.dateTv.setText(formattedDate);
                            binding.conditionTv.setText(condition);
                            binding.categoryTv.setText(category);

                            loadSellerDetails();
                        } catch (Exception e) {
                            Log.e(TAG, "onDataChange: ", e);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
    }

    private void loadSellerDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(sellerUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = "" + snapshot.child("name").getValue();
                        String phoneNumber = "" + snapshot.child("phoneNumber").getValue();
                        String profileImageUrl = "" + snapshot.child("profileImageUrl").getValue();
                        long timestamp = snapshot.child("timestamp").getValue(Long.class);
                        String phoneCode = "" + snapshot.child("phoneCode").getValue();

                        String formattedDate = Utils.formatTimestampDate(timestamp) ;
                        sellerPhone = phoneCode + " " + phoneNumber;

                        binding.sellerNameTv.setText(name);
                        binding.memberSinceTv.setText(formattedDate);

                        try {
                            Glide.with(AdDetailsActivity.this)
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.ic_person_white)
                                    .into(binding.sellerProfileIv);
                        } catch (Exception e) {
                            Log.e(TAG, "onDataChange: ", e);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
    }

    private void checkIsFavorite() {
        Log.d(TAG, "checkIsFavorite: ");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Favorites").child(adId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        favorite = snapshot.exists();
                        Log.d(TAG, "onDataChange: " + favorite);
                        if (favorite) {
                            binding.toolbarFavBtn.setImageResource(R.drawable.ic_fav_yes);
                        } else {
                            binding.toolbarFavBtn.setImageResource(R.drawable.ic_fav_no);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
    }

    private void loadAdImages() {
        Log.d(TAG, "loadAdImages: ");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        ref.child(adId).child("Images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (imageSliderArrayList == null) {
                            imageSliderArrayList = new ArrayList<>();
                        }
                        imageSliderArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ModelImageSlider modelImageSlider = ds.getValue(ModelImageSlider.class);
                            imageSliderArrayList.add(modelImageSlider);
                        }

                        AdapterImageSlider adapterImageSlider = new AdapterImageSlider(AdDetailsActivity.this, imageSliderArrayList);
                        binding.imageSliderVp.setAdapter(adapterImageSlider);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
    }

    private void deleteAd() {
        Log.d(TAG, "deleteAd: ");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        ref.child(adId)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Ad Deleted...");
                        Utils.toast(AdDetailsActivity.this, "Ad Deleted...");
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                        Utils.toast(AdDetailsActivity.this, "Failed to Delete due to " + e.getMessage());
                    }
                });
    }
}
