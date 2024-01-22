package com.example.finalproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject.R;

public class AboutFragment extends Fragment {

    Button btnLinkedIn, btnGithub;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        btnLinkedIn = view.findViewById(R.id.btnLinkedIn);
        btnGithub = view.findViewById(R.id.btnGithub);

        btnLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedIn();
            }
        });

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithub();
            }
        });

        return view;
    }

    public void openLinkedIn() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/esra-konya-94739a236/"));
        startActivity(intent);
    }

    public void openGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/esrakonya"));
        startActivity(intent);
    }
}