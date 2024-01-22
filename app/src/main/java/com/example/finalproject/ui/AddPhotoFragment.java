package com.example.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.FragmentAddphotoBinding;
import com.example.finalproject.models.AddPhotoViewModel;

public class AddPhotoFragment extends Fragment {

    private FragmentAddphotoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddPhotoViewModel addPhotoViewModel =
                new ViewModelProvider(this).get(AddPhotoViewModel.class);

        binding = FragmentAddphotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        addPhotoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}