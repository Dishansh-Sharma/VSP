package com.example.vsp;

import android.content.Context;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;





public class Utils {


    public static  final String AD_STATUS_AVAILABE ="AVAILABLE";
    public static  final String AD_STATUS_SOLD ="SOLD";




    public  static final String[] categories ={
            "Mobiles",
            "Computer / Laptop",
            "Electronics & Home Appliances",
            "Vehicles",
            "Furniture",
            "Books",
            "Sports",
            "Shoes",
            "Food",
            "Fashion & Beauty",
            "Others"

    };

    public static final int[] categoryIcons = {
            R.drawable.ic_category_mobiles,
            R.drawable.ic_category_computer,
            R.drawable.ic_category_electronics,
            R.drawable.ic_category_vehicle,
            R.drawable.ic_category_furniture,
            R.drawable.ic_category_book,
            R.drawable.ic_category_sport,
            R.drawable.icshoes,
            R.drawable.ic_category_apple,
            R.drawable.ic_category_fashion_beauty,
            R.drawable.ic_category_other
    };

    public  static final String[] conditions ={"New","Used","Refurbished"};

    public static void toast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static  long getTimestamp(){
        return System.currentTimeMillis();
    }


    public static String formatTimestampDate(Long timestamp){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String date = dateFormat.format(calendar.getTime());
        return date;

    }


    public static  void  addToFavourite(Context context,String adId){
        FirebaseAuth firebaseAuth  =  FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            Utils.toast(context,"You are not logged in!...");
        }
        else{
            long timestamp = Utils.getTimestamp();

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("adId",adId);
            hashMap.put("timestamp",timestamp);


            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getCurrentUser().getUid()).child("Favorites").child(adId)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Utils.toast(context,"Added to Favorite!...");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Utils.toast(context,"Failed to add to favorite due to "+e.getMessage());
                        }
                    });
        }
    }

    public static void removeFavourite(Context context,String adId){
        FirebaseAuth firebaseAuth  =  FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            Utils.toast(context,"You are not logged in!...");
        }
        else{



            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(adId)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Utils.toast(context,"Removed from favorite!...");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Utils.toast(context,"Failed to remove to favorite due to "+e.getMessage());
                        }
                    });
        }

    }

    public static void  callIntent(Context context ,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+ Uri.encode(phone)));
        context.startActivity(intent);
    }


//    public static void smsIntent(Context context, String phone) {
//        Intent intent = new Intent(Intent.ACTION_DIAL , Uri.parse("sms:"+ Uri.encode(phone)));
//        context.startActivity(intent);
//    }
    public static void  mapIntent(Context context ,double latitude,double longitude){

        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude);
        Intent mapIntent =new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if(mapIntent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(mapIntent);
        }
        else{
            Utils.toast(context,"Google Maps is not installed on your device!");
        }
    }




}
