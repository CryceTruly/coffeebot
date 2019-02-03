package com.must.cofeeapp.cpro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    ProgressBar progresss;
    DatabaseReference mDatabase;
    private Context mContext = SearchActivity.this;
    FirebaseRecyclerAdapter adapter;
    //widgets
    private EditText mSearchParam;
    private RecyclerView mListView;
    ImageView seachimage;
    TextView textView;
    Query firebaseSearchQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchParam = (EditText) findViewById(R.id.search);
        mListView =  findViewById(R.id.listView);
        Log.d(TAG, "onCreate: started.");
        ImageView close=findViewById(R.id.closewin);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),HomeActivity.class));
                finish();
            }
        });
        progresss = findViewById(R.id.progresss);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("diseases");
        mDatabase.keepSynced(true);
        mContext = this;
        seachimage =findViewById(R.id.seachimage);
        mListView = findViewById(R.id.listView);
        mListView.setHasFixedSize(true);
        textView =findViewById(R.id.textView);
        mListView.setLayoutManager(new LinearLayoutManager(mContext));


        mSearchParam.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                //execute our method for searching
                firebaseUserSearch(Handy.capitalize(mSearchParam.getText().toString().trim()));
            }
            return false;
        });

        seachimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = Handy.getTrimmedName(mSearchParam.getText().toString().trim());
                if (!TextUtils.isEmpty(searchText)) {
                    progresss.setVisibility(View.VISIBLE);
                    firebaseUserSearch(Handy.capitalize(searchText));

                } else {
                    Toast.makeText(mContext, "Type a disease", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void firebaseUserSearch(final String searchText) {
        Log.d(TAG, "firebaseUserSearch: searching for "+searchText);

        textView.setVisibility(View.GONE);
        firebaseSearchQuery = mDatabase.orderByChild("name").startAt(Handy.getTrimmedName(searchText))
                .endAt(Handy.getTrimmedName(searchText + "\uf8ff"));
      firebaseSearchQuery.keepSynced(true);
        firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideSoftKeyboard();
                if (!dataSnapshot.exists()) {
                    Log.d(TAG, "onDataChange: No match for search");
                    Toast.makeText(mContext, "No matching disease", Toast.LENGTH_SHORT).show();
                    progresss.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);

                }else {

                    Log.d(TAG, "onDataChange: found matches"+dataSnapshot);

        FirebaseRecyclerOptions<DiseaseSearch> options = new FirebaseRecyclerOptions.Builder<DiseaseSearch>().setQuery(
                firebaseSearchQuery, DiseaseSearch.class
        ).build();

        adapter = new FirebaseRecyclerAdapter<DiseaseSearch, TrulysDiseaseVH>(options) {
            @Override
            public TrulysDiseaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_disease_search, parent, false);

                return new TrulysDiseaseVH(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrulysDiseaseVH viewHolder, int position, @NonNull DiseaseSearch model) {
                final String key = getRef(position).getKey();
                Log.d(TAG, "onBindViewHolder: bound the holder");


               viewHolder.setName(model.getName());
               viewHolder.setPhoto(getApplicationContext(),model.getPhoto());
                progresss.setVisibility(View.GONE);

                viewHolder.mView.setOnClickListener(view -> {
                    Intent i = new Intent(mContext, DetailsActivity.class);
                    i.putExtra("key", key);
                    i.putExtra("imageone", model.getPhoto());
                    i.putExtra("name", model.getName());
                    startActivity(i);
                    finish();
                });

            }











        };
                    setaAdapter(adapter,mListView);


                    mListView.setAdapter(adapter);
                    adapter.startListening();
                    showshoftKeyBoard();
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showshoftKeyBoard() {
    }

    private void setaAdapter(FirebaseRecyclerAdapter adapter, RecyclerView mListView) {
        Log.d(TAG, "setaAdapter: setting adapter");
        mListView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {
        try{
            adapter.stopListening();
        }catch (NullPointerException e){

        }
        super.onStop();
    }

    public static class TrulysDiseaseVH extends RecyclerView.ViewHolder {
        View mView;
        TextView us_name;
       ImageView us_image;
        public TrulysDiseaseVH(View itemView) {
            super(itemView);
            mView = itemView;
            us_name = mView.findViewById(R.id.name);
            us_image = mView.findViewById(R.id.image);

        }
public void setPhoto(Context context,String photo){

    ImageView us_image = mView.findViewById(R.id.image);

   try {
       Glide.with(context).load(photo).into(us_image);
   }catch (Exception e){
       Log.d(TAG, "setPhoto: error");
   }
}

public void setName(String name){
    TextView us_name = mView.findViewById(R.id.name);
    us_name.setText(Handy.getTrimmedName(name));



        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),HomeActivity.class));
        finish();
        super.onBackPressed();
    }
}