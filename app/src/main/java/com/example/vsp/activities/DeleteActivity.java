package com.example.vsp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vsp.Utils;
import com.example.vsp.databinding.ActivityDeleteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteActivity extends AppCompatActivity {

    private ActivityDeleteBinding binding;
    private  static final  String TAG ="DELETE_ACCOUNT_TAG";


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_delete);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        binding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

// Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser != null) {
                    deleteAccount();
                } else {
                    Utils.toast(DeleteActivity.this, "No user is currently signed in.");
                }
            }
        });
    }

//    private  void deleteAccount(){
//        Log.d(TAG,"deleteAccount: ");
//
//        String myuid = firebaseAuth.getUid();
//
//        progressDialog.setMessage("Deleting User Ads");
//        progressDialog.show();
//
//        firebaseUser.delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG,"onSuccess: Account Deleted");
//                        DatabaseReference refUserAds = FirebaseDatabase.getInstance().getReference("Ads");
//                        refUserAds.orderByChild("uid").equalTo(myuid)
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        for(DataSnapshot ds : snapshot.getChildren()){
//                                            ds.getRef().removeValue();
//                                        }
//
//
//
//
//
//                                        progressDialog.setMessage("Deleting User Data...");
//
//                                        DatabaseReference refUsers =FirebaseDatabase.getInstance().getReference("Users");
//                                        refUsers.child(myuid)
//                                                .removeValue()
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void unused) {
//                                                        Log.d(TAG,"onSuccess: User data deleted...");
//                                                        startMainActivity();
//
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//
//                                                        Log.e(TAG,"onFailure: ",e);
//                                                        progressDialog.dismiss();
//                                                        Utils.toast(DeleteActivity.this,"Failed to delete account due to " +e.getMessage());
//                                                        startMainActivity();
//
//                                                    }
//                                                });
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//                                        Log.e(TAG, "onCancelled: ", error.toException());
//                                        progressDialog.dismiss();
//                                        Utils.toast(DeleteActivity.this, "Failed to delete user ads due to " + error.getMessage());
//
//                                    }
//                                });
//
//
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG,"onFailure: ",e);
//                        progressDialog.dismiss();
//                        Utils.toast(DeleteActivity.this,"Failed to delete account due to " +e.getMessage());
//                    }
//                });
//
//
//    }






    private void deleteAccount() {
        Log.d(TAG, "deleteAccount: ");

        String myUid = firebaseAuth.getUid();

        progressDialog.setMessage("Deleting User Ads...");
        progressDialog.show();

        firebaseUser.delete()
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "onSuccess: Account Deleted");
                    DatabaseReference refUserAds = FirebaseDatabase.getInstance().getReference("Ads");
                    refUserAds.orderByChild("uid").equalTo(myUid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        ds.getRef().removeValue();
                                    }

                                    progressDialog.setMessage("Deleting User Data...");

                                    DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("Users");
                                    refUsers.child(myUid)
                                            .removeValue()
                                            .addOnSuccessListener(unused1 -> {
                                                Log.d(TAG, "onSuccess: User data deleted...");
                                                startMainActivity();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e(TAG, "onFailure: ", e);
                                                progressDialog.dismiss();
                                                Utils.toast(DeleteActivity.this, "Failed to delete user data due to " + e.getMessage());
                                                startMainActivity();
                                            });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e(TAG, "onCancelled: ", error.toException());
                                    progressDialog.dismiss();
                                    Utils.toast(DeleteActivity.this, "Failed to delete user ads due to " + error.getMessage());
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    progressDialog.dismiss();
                    Utils.toast(DeleteActivity.this, "Failed to delete account due to " + e.getMessage());
                });
    }





    public void onBackPressed(){
        startActivity(new Intent(DeleteActivity.this, MainActivity.class));
        finishAffinity();

    }


    private void startMainActivity(){
        Log.d(TAG,"startMainActivity: ");
        startActivity(new Intent(DeleteActivity.this,MainActivity.class));
        finishAffinity();
    }



}




















