package com.example.vsp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.databinding.ActivityLoginOptionsBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginOptionsActivity extends AppCompatActivity {
    private @NonNull ActivityLoginOptionsBinding binding;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private  static final  String TAG ="LOGIN_OPTIONS_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login_options);

        binding = ActivityLoginOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

// Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        binding.loginEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOptionsActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });


        binding.loginPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOptionsActivity.this, LoginPhoneActivity.class);
                startActivity(intent);
            }
        });


        binding.loginGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginOptionsActivity.this, LoginEmailActivity.class);
//                startActivity(intent);

                beginGoogleLogin();
            }
        });



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }


    public void beginGoogleLogin(){
        Log.d(TAG,"beginGoogleLogin: ");
        Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInnARL.launch(googleSignInIntent);
    }

    private ActivityResultLauncher<Intent>googleSignInnARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG,"onActivityResult: ");

                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task  = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d(TAG,"onActivityResult: Account ID:"+account.getId());
                            firebaseAuthWithGoogleAccount(account.getIdToken());

                        }
                        catch (ApiException e){
                            Log.e(TAG,"onActivityResult: ",e);
                        }
                    }
                    else{
                        Log.d(TAG,"onActivityResult: Cancelled");
                        Utils.toast(LoginOptionsActivity.this,"Cancelled...");
                    }

                }


            }

    );


//    private void firebaseAuthWithGoogleAccount(String idToken){
//
//        Log.d(TAG,"firebaseAuthWithGoogleAccount: idToken: "+idToken);
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
//
//        firebaseAuth.signInWithCredential(credential)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        if(authResult.getAdditionalUserInfo().isNewUser()){
//                            Log.d(TAG,"onSuccess: New User, Account created...");
//                            updateUserInfoOb();
//                        }
//                        else{
//                            Log.d(TAG,"onSuccess: Existing User, Logged In...");
//                            startActivity(new Intent(LoginOptionsActivity.this ,MainActivity.class));
//                            finishAffinity();
//                        }
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG,"onFailure: ",e);
//                    }
//                });
//
//    }





    private void firebaseAuthWithGoogleAccount(String idToken) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: idToken: " + idToken);

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Check if the email domain is valid
                        String userEmail = firebaseAuth.getCurrentUser().getEmail();
                        if (userEmail != null && userEmail.endsWith("@vitbhopal.ac.in")) {
                            if (authResult.getAdditionalUserInfo().isNewUser()) {
                                Log.d(TAG, "onSuccess: New User, Account created...");
                                updateUserInfoOb();
                            } else {
                                Log.d(TAG, "onSuccess: Existing User, Logged In...");
                                startActivity(new Intent(LoginOptionsActivity.this, MainActivity.class));
                                finishAffinity();
                            }
                        } else {
                            Log.e(TAG, "onSuccess: Invalid domain. Only @vitbhopal.ac.in is allowed.");
                            Utils.toast(LoginOptionsActivity.this, "Invalid email domain. Only @vitbhopal.ac.in is allowed.");
                            firebaseAuth.signOut(); // Sign out the user
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    private  void updateUserInfoOb(){
        Log.d(TAG,"updateUserInfoOb...");
        progressDialog.setMessage("Saving User Info");
        progressDialog.show();




        long timestamp = Utils.getTimestamp();
        String registerUserEmail =firebaseAuth.getCurrentUser().getEmail();
        String registerUserUid = firebaseAuth.getUid();
        String name = firebaseAuth.getCurrentUser().getDisplayName();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name",""+name);
        hashMap.put("phoneCode","");
        hashMap.put("phoneNumber","");
        hashMap.put("profileImageUrl","");
        hashMap.put("dob","");
        hashMap.put("userType","Google");
        hashMap.put("TypingTo","");
        hashMap.put("Timestamp",timestamp);
        hashMap.put("onlineStatus",true);
        hashMap.put("email",registerUserEmail);
        hashMap.put("uid",registerUserUid);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(registerUserUid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"onSUccess: User info saved...");
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginOptionsActivity.this,MainActivity.class));
                        finishAffinity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e);
                        progressDialog.dismiss();
                        Utils.toast(LoginOptionsActivity.this,"Failed to save user info dur to "+e.getMessage());
                    }
                });


    }
}