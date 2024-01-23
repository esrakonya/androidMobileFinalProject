package com.example.finalproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentAddphotoBinding;
import com.example.finalproject.models.AddPhotoViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class AddPhotoFragment extends Fragment {

    private FragmentAddphotoBinding binding;

    Button btnSec, btnPaylas;
    ImageView selectedImageView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    RadioGroup radioGroup;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("photos");
        storageReference = FirebaseStorage.getInstance().getReference("photos");
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddPhotoViewModel addPhotoViewModel =
                new ViewModelProvider(this).get(AddPhotoViewModel.class);

        binding = FragmentAddphotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSec = root.findViewById(R.id.btnSec);
        btnPaylas = root.findViewById(R.id.btnPaylas);
        selectedImageView = root.findViewById(R.id.imageView);
        radioGroup = root.findViewById(R.id.radioGroup);

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        return root;
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            try {
                ImageView selectedImageView = getView().findViewById(R.id.imageView);
                selectedImageView.setImageURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadPhoto() {
        if(imageUri != null) {
            final StorageReference photoRef = storageReference.child(System.currentTimeMillis() + ".jpg");
            photoRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String photoId = databaseReference.push().getKey();
                    databaseReference.child(photoId).setValue(uri.toString());

                    StringBuilder selectedLabels = new StringBuilder();
                    for(int i = 0; i < radioGroup.getChildCount(); i++) {
                        CheckBox checkBox = (CheckBox) radioGroup.getChildAt(i);
                        if(checkBox.isChecked()) {
                            selectedLabels.append(checkBox.getText()).append(", ");
                        }
                    }

                    if(selectedLabels.length() > 0) {
                        databaseReference.child(photoId).child("labels").setValue(selectedLabels.toString().substring(0, selectedLabels.length() - 2));
                    }

                    Toast.makeText(getContext(), "Fotoğraf başarıyla paylaşıldı", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Fotoğraf paylaşılırken bir hata oluştu", Toast.LENGTH_SHORT).show();
            });
        }else{
            Toast.makeText(getContext(), "Lütfen bir fotoğraf seçin", Toast.LENGTH_SHORT).show();
        }
    }
}