package com.example.finalproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentAddphotoBinding;
import com.example.finalproject.models.AddPhotoViewModel;

public class AddPhotoFragment extends Fragment {

    private FragmentAddphotoBinding binding;

    Button btnSec, btnPaylas;

    private int images = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddPhotoViewModel addPhotoViewModel =
                new ViewModelProvider(this).get(AddPhotoViewModel.class);

        binding = FragmentAddphotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSec = root.findViewById(R.id.btnSec);
        btnPaylas = root.findViewById(R.id.btnPaylas);

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
        });

        return root;
    }



}