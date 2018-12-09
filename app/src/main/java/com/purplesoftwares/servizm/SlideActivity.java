package com.purplesoftwares.servizm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideActivity extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideActivity(Context context)
    {
        this.context = context;
    }

    public int[] slide_images = {
            R.mipmap.icon_pack_1,
            R.mipmap.icon_pack_2,
            R.mipmap.van_icons_merged,
    };

    public String[] slide_desc = {
            "seyahat programını yap,\n" +"aracını biz bulalım",
            "ister günübirlik gezi,\n" +
                    "ister bir yakının düğünü...\n\n" +
                    "keyifli bir yolculuk zamanı",
            "uygulamaya gir,\n" +
                    "aradığın servisi bul"
    };


    @Override
    public int getCount() {
        return slide_desc.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slide, container, false);

        ImageView imageView = view.findViewById(R.id.iPicture);
        TextView textView = view.findViewById(R.id.txtInfo);


        imageView.setImageResource(slide_images[position]);

        textView.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

}
