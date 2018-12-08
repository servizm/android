package com.purplesoftwares.servizm;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ViewPager sliderPager;
    private LinearLayout dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sliderPager = findViewById(R.id.sliderViewPager);
        dotsLayout =  findViewById(R.id.dotsLayout);

        SwipeActivity sliderAdapter = new SwipeActivity(this) ;

        sliderPager.setAdapter(sliderAdapter);

        addDots(0);

        sliderPager.addOnPageChangeListener(listener);
    }

    public  void addDots(int position) {

        TextView[] dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i=0;i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
    }
    ViewPager.OnPageChangeListener  listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {


        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
