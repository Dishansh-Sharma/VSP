package com.example.vsp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.vsp.fragments.AccountFragment;
import com.example.vsp.fragments.ChatsFragment;
import com.example.vsp.fragments.HomeFragment;
import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.databinding.ActivityMainBinding;
import com.example.vsp.fragments.myadsFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startLoginOptions();
        }

        showHomeFragment();
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        binding.bottomNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.menu_home){
                    showHomeFragment();
                    return true;
                }
                if(itemId == R.id.menu_chats){

                    if(firebaseAuth.getCurrentUser() == null){
                        Utils.toast(MainActivity.this,"Login Required...");
                        startLoginOptions();
                        return false;
                    }
                    else{
                        showChatsFragment();
                        return true;
                    }

                }
                if(itemId == R.id.menu_my_ads){
                    if(firebaseAuth.getCurrentUser() == null){
                        Utils.toast(MainActivity.this,"Login Required...");
                        startLoginOptions();
                        return false;
                    }
                    else{
                        showMyAdsFragment();
                        return true;
                    }

                }
                if(itemId == R.id.menu_account){
                    if(firebaseAuth.getCurrentUser() == null){
                        Utils.toast(MainActivity.this,"Login Required...");
                        startLoginOptions();
                        return false;
                    }
                    else{
                        showAccountFragment();
                        return true;
                    }

                }
                else {
                    return false;
                }
            }
        });



        binding.sellFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AdCreateActivity.class);
                intent.putExtra("isEditMode", false);

                startActivity(intent);
            }
        });
    }

    private void showHomeFragment() {
        binding.toolbarTittleTv.setText("Home");
        HomeFragment fragment =new HomeFragment();
        FragmentTransaction fragmentTransaction  =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentFl.getId(), fragment,"HomeFragment");
        fragmentTransaction.commit();
    }
    private void showChatsFragment() {
        binding.toolbarTittleTv.setText("Chats");

        ChatsFragment fragment =new ChatsFragment();
        FragmentTransaction fragmentTransaction  =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentFl.getId(), fragment,"ChatsFragment");
        fragmentTransaction.commit();


    }
    private void showMyAdsFragment() {
        binding.toolbarTittleTv.setText("My Ads");

        myadsFragment fragment =new myadsFragment();
        FragmentTransaction fragmentTransaction  =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentFl.getId(), fragment,"mydsFragment");
        fragmentTransaction.commit();
    }
    private void showAccountFragment() {
        binding.toolbarTittleTv.setText("Account");

        AccountFragment fragment =new AccountFragment();
        FragmentTransaction fragmentTransaction  =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentFl.getId(), fragment,"AccountFragment");
        fragmentTransaction.commit();
    }

    private  void startLoginOptions(){
        startActivity(new Intent(this, LoginOptionsActivity.class));

    }
}