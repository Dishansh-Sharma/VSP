//package com.example.vsp;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.vsp.databinding.ActivityLoginEmailBinding;
//import com.example.vsp.databinding.ActivityRegisrterEmailBinding;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//
//public class RegisrterEmailActivity extends AppCompatActivity {
//
//    private ActivityRegisrterEmailBinding binding;
//    private  static final  String TAG ="Register_TAG";
//    private ProgressDialog progressDialog;
//    private FirebaseAuth firebaseAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        EdgeToEdge.enable(this);
//
//        binding =ActivityRegisrterEmailBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Initialize FirebaseAuth instance
//        firebaseAuth = FirebaseAuth.getInstance();
//
//// Initialize ProgressDialog
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Please wait...");
//        progressDialog.setCanceledOnTouchOutside(false);
////        setContentView(R.layout.activity_regisrter_email);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//
//
//
//        binding.toolbarBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//        binding.haveAccountTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateData();
//            }
//        });
//
//    }
//    private  String email , password , cPassword;
////    private  void validateData(){
////        email =binding.emailEt.getText().toString().trim();
////        password = binding.passwordEt.getText().toString();
////        cPassword = binding.passwordEt.getText().toString();
////
////        Log.d(TAG,"validateData: email: "+email);
////        Log.d(TAG,"validateData: password: "+password);
////        Log.d(TAG,"validateData: cPassword: "+cPassword);
////
////
////
////        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
////            binding.emailEt.setError("Invalid Email Pattern...");
////            binding.emailEt.requestFocus();
////        }
////        else if(password.isEmpty()){
////            binding.passwordEt.setError("Enter Password");
////            binding.passwordEt.requestFocus();
////        } else if (!password.equals(cPassword)) {
////            binding.cPasswordEt.setError("Password doesn't match");
////            binding.cPasswordEt.requestFocus();
////        }
////        else{
////            registerUser();
////        }
////    }
//
//
//
//
////    private  void registerUser(){
////        progressDialog.setMessage("Creating Account...");
////        progressDialog.show();
////
////        firebaseAuth.createUserWithEmailAndPassword(email , password)
////                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
////                    @Override
////                    public void onSuccess(AuthResult authResult) {
////                        Log.d(TAG,"onSuccess: Register Sucsess");
////                        updateUserInfo();
//////                        progressDialog.dismiss();
//////                        startActivity(new Intent(LoginEmailActivity.this, MainActivity.class));
//////                        finishAffinity();
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////
////                        Log.e(TAG,"onFailure: ",e);
////                        Utils.toast(RegisrterEmailActivity.this ,"Faliure due to "+e.getMessage());
////                        progressDialog.dismiss();
////                    }
////                });
////    }
//
////    private void updateUserInfo(){
////        progressDialog.setMessage("Saving User Info");
////
////        long timestamp = Utils.getTimestamp();
////        String registerUserEmail =firebaseAuth.getCurrentUser().getEmail();
////        String registerUserUid = firebaseAuth.getUid();
////
////        HashMap<String,Object> hashMap = new HashMap<>();
////        hashMap.put("name","");
////        hashMap.put("phoneCode","");
////        hashMap.put("phoneNumber","");
////        hashMap.put("profileImageUrl","");
////        hashMap.put("dob","");
////        hashMap.put("userType","Email");
////        hashMap.put("TypingTo","");
////        hashMap.put("Timestamp",timestamp);
////        hashMap.put("onlineStatus",true);
////        hashMap.put("email",registerUserEmail);
////        hashMap.put("uid",registerUserUid);
////
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
////        reference.child(registerUserUid)
////                .setValue(hashMap)
////                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                    @Override
////                    public void onSuccess(Void unused) {
////                        Log.d(TAG,"onSuccess: Info Saved...");
////
////                        progressDialog.dismiss();
////                        startActivity(new Intent(RegisrterEmailActivity.this, MainActivity.class));
////                        finishAffinity();
////
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.e(TAG,"onFailure: ",e);
////                        progressDialog.dismiss();
////                        Utils.toast(RegisrterEmailActivity.this ,"Failed to save info due to  "+e.getMessage());
////                    }
////                });
////
////
////    }
//
//
//
//    private void validateData() {
//        email = binding.emailEt.getText().toString().trim();
//        password = binding.passwordEt.getText().toString();
//        cPassword = binding.cPasswordEt.getText().toString();
//
//        Log.d(TAG, "validateData: email: " + email);
//        Log.d(TAG, "validateData: password: " + password);
//        Log.d(TAG, "validateData: cPassword: " + cPassword);
//
//        // Check if email is valid and has the correct domain
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.endsWith("@vitbhopal.ac.in")) {
//            binding.emailEt.setError("Invalid Email. Only @vitbhopal.ac.in domain is allowed.");
//            binding.emailEt.requestFocus();
//        } else if (password.isEmpty()) {
//            binding.passwordEt.setError("Enter Password");
//            binding.passwordEt.requestFocus();
//        } else if (!password.equals(cPassword)) {
//            binding.cPasswordEt.setError("Password doesn't match");
//            binding.cPasswordEt.requestFocus();
//        } else {
//            // Check if email already exists
//            checkEmailExists(email);
//        }
//    }
//
//    private void checkEmailExists(String email) {
//        firebaseAuth.fetchSignInMethodsForEmail(email)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
//                        if (isNewUser) {
//                            registerUser();
//                        } else {
//                            binding.emailEt.setError("Email is already registered.");
//                            binding.emailEt.requestFocus();
//                        }
//                    } else {
//                        Log.e(TAG, "checkEmailExists: Error checking email existence", task.getException());
//                        Utils.toast(RegisrterEmailActivity.this, "Failed to check email existence.");
//                    }
//                });
//    }
//
//    private void registerUser() {
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnSuccessListener(authResult -> {
//                    Log.d(TAG, "onSuccess: Register Success");
//                    updateUserInfo();
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "onFailure: Registration failed", e);
//                    Utils.toast(RegisrterEmailActivity.this, "Failure due to " + e.getMessage());
//                    progressDialog.dismiss();
//                });
//    }
//
//    private void updateUserInfo() {
//        progressDialog.setMessage("Saving User Info");
//        progressDialog.show();
//
//        long timestamp = Utils.getTimestamp();
//        String registerUserEmail = firebaseAuth.getCurrentUser().getEmail();
//        String registerUserUid = firebaseAuth.getUid();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "");
//        hashMap.put("phoneCode", "");
//        hashMap.put("phoneNumber", "");
//        hashMap.put("profileImageUrl", "");
//        hashMap.put("dob", "");
//        hashMap.put("userType", "Email");
//        hashMap.put("TypingTo", "");
//        hashMap.put("Timestamp", timestamp);
//        hashMap.put("onlineStatus", true);
//        hashMap.put("email", registerUserEmail);
//        hashMap.put("uid", registerUserUid);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.child(registerUserUid)
//                .setValue(hashMap)
//                .addOnSuccessListener(unused -> {
//                    Log.d(TAG, "onSuccess: User info saved...");
//                    progressDialog.dismiss();
//                    startActivity(new Intent(RegisrterEmailActivity.this, MainActivity.class));
//                    finishAffinity();
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "onFailure: Failed to save user info", e);
//                    progressDialog.dismiss();
//                    Utils.toast(RegisrterEmailActivity.this, "Failed to save info due to " + e.getMessage());
//                });
//    }
//
//
//
//
//
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

package com.example.vsp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vsp.Utils;
import com.example.vsp.databinding.ActivityRegisrterEmailBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisrterEmailActivity extends AppCompatActivity {

    private ActivityRegisrterEmailBinding binding;
    private static final String TAG = "Register_TAG";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisrterEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.toolbarBackBtn.setOnClickListener(v -> onBackPressed());

        binding.haveAccountTv.setOnClickListener(v -> onBackPressed());

        binding.registerBtn.setOnClickListener(v -> validateData());
    }

    private String email, password, cPassword;

    private void validateData() {
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString();
        cPassword = binding.cPasswordEt.getText().toString();

        Log.d(TAG, "validateData: email: " + email);
        Log.d(TAG, "validateData: password: " + password);
        Log.d(TAG, "validateData: cPassword: " + cPassword);

        // Check if email is valid and has the correct domain
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.endsWith("@vitbhopal.ac.in")) {
            binding.emailEt.setError("Invalid Email. Only @vitbhopal.ac.in domain is allowed.");
            binding.emailEt.requestFocus();
        } else if (password.isEmpty()) {
            binding.passwordEt.setError("Enter Password");
            binding.passwordEt.requestFocus();
        } else if (!password.equals(cPassword)) {
            binding.cPasswordEt.setError("Password doesn't match");
            binding.cPasswordEt.requestFocus();
        } else {
            // Check if email already exists
            checkEmailExists(email);
        }
    }

    private void checkEmailExists(String email) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if (isNewUser) {
                            registerUser();
                        } else {
                            binding.emailEt.setError("Email is already registered.");
                            binding.emailEt.requestFocus();
                        }
                    } else {
                        Log.e(TAG, "checkEmailExists: Error checking email existence", task.getException());
                        Utils.toast(RegisrterEmailActivity.this, "Failed to check email existence.");
                    }
                });
    }

    private void registerUser() {
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "onSuccess: Register Success");
                    sendVerificationEmail();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: Registration failed", e);
                    Utils.toast(RegisrterEmailActivity.this, "Failure due to " + e.getMessage());
                    progressDialog.dismiss();
                });
    }

    private void sendVerificationEmail() {
        firebaseAuth.getCurrentUser().sendEmailVerification()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "onSuccess: Verification Email Sent.");
                    progressDialog.dismiss();
                    Utils.toast(RegisrterEmailActivity.this, "Verification email sent. Please verify and log in again.");
                    // Prompt the user to log in after verification
                    startActivity(new Intent(RegisrterEmailActivity.this, LoginEmailActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: Failed to send verification email", e);
                    Utils.toast(RegisrterEmailActivity.this, "Failed to send verification email.");
                    progressDialog.dismiss();
                });
    }
}
