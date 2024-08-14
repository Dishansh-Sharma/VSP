package com.example.vsp.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vsp.R;
import com.example.vsp.Utils;
import com.example.vsp.databinding.RowImagesPickedBinding;
import com.example.vsp.models.ModelImagePicked;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterImagePicked extends RecyclerView.Adapter<AdapterImagePicked.HolderImagePicked> {

    private RowImagesPickedBinding binding;
    private  static  final String TAG ="IMAGES_TAG";
    private ArrayList<ModelImagePicked> imagePickedArrayList;
    private Context context;

    private String adId;

    public AdapterImagePicked(ArrayList<ModelImagePicked> imagePickedArrayList, Context context, String adId) {
        this.imagePickedArrayList = imagePickedArrayList;
        this.context = context;
        this.adId = adId;
    }

    @NonNull
    @Override
    public HolderImagePicked onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = RowImagesPickedBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderImagePicked(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderImagePicked holder, int position) {

        ModelImagePicked model = imagePickedArrayList.get(position);


        if(model.getFromInternet() ){
            String imageUrl = model.getImageurl();



            Log.d(TAG,"onBindViewHolder: imageUrl: "+imageUrl);

            try {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_image_gray)
                        .into(holder.imageiv);
            }catch (Exception e){
                Log.e(TAG,"onBindViewHolder: ",e);
            }
        }
        else{
            Uri imageUri = model.getImageuri();

            Log.d(TAG,"onBindViewHolder: imageUri: "+imageUri);

            try {
                Glide.with(context)
                        .load(imageUri)
                        .placeholder(R.drawable.ic_image_gray)
                        .into(holder.imageiv);
            }catch (Exception e){
                Log.e(TAG,"onBindViewHolder: ",e);
            }
        }


        holder.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(model.getFromInternet()){
                    deleteImageFirebase(model,holder,position);
                }
                else{
                    imagePickedArrayList.remove(model);
                    notifyItemRemoved(position);
                }

            }
        });

    }

    private void deleteImageFirebase(ModelImagePicked model, HolderImagePicked holder, int position) {



        String imageId = model.getId();

        Log.d(TAG,"deleteImageFirebase: adId: "+adId);

        Log.d(TAG,"deleteImageFirebase: imageId: "+imageId);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ads");
        ref.child(adId).child("Images").child(imageId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"onSuccess: Deleted");
                        Utils.toast(context,"Image Deleted!");
                        try{
                            imagePickedArrayList.remove(model);
                            notifyItemRemoved(position);
                        }catch (Exception e){
                            Log.e(TAG,"onSuccess: ",e);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"Failed to delete image due to: "+e.getMessage());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return imagePickedArrayList.size();
    }

    class HolderImagePicked extends RecyclerView.ViewHolder{
        ImageView imageiv;
        ImageView closeBtn;
        public HolderImagePicked(View itemView){
            super(itemView);
            imageiv = binding.imageiv;
            closeBtn = binding.closeBtn;
        }
    }
}
