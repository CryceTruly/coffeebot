package com.must.cofeeapp.cpro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;



public class MeasuresFragment extends Fragment {


    public MeasuresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_measures, container, false);
        final TextView textView=view.findViewById(R.id.measures);

        FirebaseFirestore.getInstance().collection("diseases").document(getActivity().getIntent().getStringExtra("key")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
           textView.setText(documentSnapshot.get("measures").toString());
            }
        });


        return view;
    }

}
