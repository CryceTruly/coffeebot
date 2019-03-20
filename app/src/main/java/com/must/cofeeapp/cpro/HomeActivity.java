package com.must.cofeeapp.cpro;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int NUM_COLUMNS = 2;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.rout);


        recyclerView = findViewById(R.id.list);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));

        recyclerView.setHasFixedSize(true);
        com.google.firebase.firestore.Query query = FirebaseFirestore.getInstance()
                .collection("diseases");

        //todo afix for location on phones probaby build a serve with no failure issues.whereEqualTo("locality", Preferences.getLocality(getContext()));


        FirestoreRecyclerOptions<Disease> options = new FirestoreRecyclerOptions.Builder<Disease>()
                .setQuery(query, Disease.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Disease, DiseaseHolder>(options) {
            @Override
            public DiseaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_disease, parent, false);

                return new DiseaseHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull final DiseaseHolder holder, final int position, final Disease model) {
                final String key = getSnapshots().getSnapshot(position).getId();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Glide.with(getBaseContext()).load(model.photo).into(holder.image);


                holder.name.setText(model.getName());
                holder.signs.setText(model.getSigns());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getBaseContext(), DetailsActivity.class);
                        i.putExtra("key", key);
                        i.putExtra("imageone", model.getPhoto());
                        i.putExtra("name", model.getName());
                        startActivity(i);

                    }
                });

                progressBar.setVisibility(View.GONE);


            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        recyclerView.setAdapter(adapter);

        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, SearchActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public static class DiseaseHolder extends RecyclerView.ViewHolder {
        View v;
        ImageView image;
        TextView name, signs;

        public DiseaseHolder(View itemView) {
            super(itemView);

            v = itemView;
            name = v.findViewById(R.id.name);
            signs = v.findViewById(R.id.signs);
            image = v.findViewById(R.id.image);

        }
    }
}
