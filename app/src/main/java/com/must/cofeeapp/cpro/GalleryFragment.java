package com.must.cofeeapp.cpro;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;


public class GalleryFragment extends Fragment {

    private StoriesProgressView storiesProgressView;
    private int PROGRESS_COUNT;
    private static final String TAG = "GalleryFragment";
    List<String> photos = new ArrayList<>();
    ImageView imageView;
    int counter = 0;

    Query query;
    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        Log.d(TAG, "onCreateView: ");
        imageView = view.findViewById(R.id.image);
        storiesProgressView = view.findViewById(R.id.stories);
            query = FirebaseDatabase.getInstance().getReference().child("photos")
                    .child(getActivity().getIntent().getStringExtra("key"));
        query.keepSynced(true);

            photos.add(getActivity().getIntent().getStringExtra("imageone"));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange1: " + dataSnapshot);
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                        photos.add(single.child("downloadUri").getValue(String.class));

                    PROGRESS_COUNT=photos.size();
                    Log.d(TAG, "onDataChange: progress counting to " + PROGRESS_COUNT);
                }

                Log.d(TAG, "onDataChange: photos count "+photos);
                try {
                  Glide.with(getContext()).load(photos.get(0)).into(imageView);
                 try {
                     imageView.setOnClickListener(v -> storiesProgressView.skip());
                 }catch (IndexOutOfBoundsException eee){

                 }
                  storiesProgressView.setStoriesCount(PROGRESS_COUNT); // <- set stories
                  storiesProgressView.setStoryDuration(7200L);
              }catch (Exception e){

              }

                try {
    storiesProgressView.startStories();
}catch (IndexOutOfBoundsException e){

}
                storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                    @Override
                    public void onNext() {

                        try {
                            Glide.with(getContext()).load(Uri.parse(photos.get(++counter))).into(imageView);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onPrev() {

                        try {
                            Glide.with(getContext()).load(Uri.parse(photos.get(--counter))).into(imageView);
                        } catch (Exception ee) {
                            
                        }
                    }

                    @Override
                    public void onComplete() {
                        storiesProgressView.reverse();
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        storiesProgressView.destroy();
        super.onDestroyView();
    }





}
