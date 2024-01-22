package com.example.finalproject.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentAddlabelBinding;
import com.example.finalproject.models.AddLabelViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AddLabelFragment extends Fragment {

    private FragmentAddlabelBinding binding;
    LinearLayout linearLayout;
    EditText etLabel;
    Button btnAdd;
    FirebaseFirestore auth;
    CollectionReference collectionReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddLabelViewModel addLabelViewModel =
                new ViewModelProvider(this).get(AddLabelViewModel.class);

        binding = FragmentAddlabelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        linearLayout = root.findViewById(R.id.linearLayout);
        etLabel = root.findViewById(R.id.etLabel);
        btnAdd = root.findViewById(R.id.btnAdd);
        auth = FirebaseFirestore.getInstance();
        collectionReference = auth.collection("AddLabelViewModel");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        AddLabelViewModel labelViewModel = documentSnapshot.toObject(AddLabelViewModel.class);
                        CheckBox checkBox = new CheckBox(getActivity());
                        checkBox.setText(labelViewModel.getLabel());
                        linearLayout.addView(checkBox);
                    }
                }else{
                    Log.d("TAG", "Hata Oluştu", task.getException());
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextLabel = etLabel.getText().toString();
                collectionReference.whereEqualTo("label", editTextLabel).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult().size() > 0) {
                            Toast.makeText(getActivity(), "Bu label var", Toast.LENGTH_SHORT).show();
                        }else{
                            collectionReference.add(new AddLabelViewModel(editTextLabel)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(), "Label eklendi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Label eklenemedi", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });


        return root;
    }

}