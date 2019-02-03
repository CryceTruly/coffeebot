package com.must.cofeeapp.cpro;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;



public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    private int [] slideImages={
      R.drawable.two,R.drawable.three,R.drawable.five
    };
    private String [] headings={
            "WELCOME","LEARN","PREVENT"
    };

    private String [] descriptions={
            "Welcome to the Coffee assistant App,Expert help in your hands","Learn about different coffee diseases and gain insights on how to control them","Get measures on how to guard against certain coffee disease from your garden."
    };

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v=inflater.inflate(R.layout.slide_layout,container,false);


        TextView heading=v.findViewById(R.id.top);
        TextView bottom=v.findViewById(R.id.bottom);
        CircleImageView imageView=v.findViewById(R.id.pic);

        Glide.with(context).load(slideImages[position]).into(imageView);
        heading.setText(headings[position]);
        bottom.setText(descriptions[position]);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
