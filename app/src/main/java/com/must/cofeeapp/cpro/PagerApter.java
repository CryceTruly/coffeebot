package com.must.cofeeapp.cpro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class PagerApter extends FragmentPagerAdapter {

    public PagerApter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GalleryFragment();
                case 1:
                    return new SignsFragment();

                case 2:
                    return new MeasuresFragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SAMPLES";
                case 1:
                    return "SIGNS";
                case 2:
                    return "MEASURES";

                default:
                    return super.getPageTitle(position);
            }


        }
    }

