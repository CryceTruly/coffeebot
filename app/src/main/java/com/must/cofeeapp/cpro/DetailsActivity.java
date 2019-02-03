package com.must.cofeeapp.cpro;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager tabsPager;
    protected PagerApter pagerAdapter;
    private TabLayout mtablayout;
    private Context mContext = DetailsActivity.this;
    private CoordinatorLayout main_rel_layout;
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout appBarLayout;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        main_rel_layout = findViewById(R.id.main_rel_layout);
        tabsPager = findViewById(R.id.main_tabPager);
        toolbar=findViewById(R.id.toolbar);
        pagerAdapter = new PagerApter(getSupportFragmentManager());
        mtablayout = findViewById(R.id.main_tabs);
        tabsPager.setAdapter(pagerAdapter);
        mtablayout.setupWithViewPager(tabsPager);

        name=getIntent().getStringExtra("name");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        super.onBackPressed();
    }
}
