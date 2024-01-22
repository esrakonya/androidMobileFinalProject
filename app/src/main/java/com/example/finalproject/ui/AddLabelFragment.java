package com.example.finalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.FragmentAddlabelBinding;
import com.example.finalproject.models.AddLabelViewModel;

public class AddLabelFragment extends Fragment {

    private FragmentAddlabelBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddLabelViewModel addLabelViewModel =
                new ViewModelProvider(this).get(AddLabelViewModel.class);

        binding = FragmentAddlabelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        addLabelViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}