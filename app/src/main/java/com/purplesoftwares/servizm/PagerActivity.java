package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PagerActivity extends AppCompatActivity {

    private ViewPager sliderPager;
    private LinearLayout dotsLayout;
    private ImageButton arrowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);


        sliderPager = findViewById(R.id.sliderViewPager);
        dotsLayout =  findViewById(R.id.dotsLayout);
        arrowButton = findViewById(R.id.arrowButton);

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToPhoneAtivity();
            }
        });


        SlideActivity sliderAdapter = new SlideActivity(this) ;

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
            dots[i].setTextColor(getResources().getColor(R.color.colorDot));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[position].setTextColor(getResources().getColor(R.color.colorDotSelected));
    }



    ViewPager.OnPageChangeListener  listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {


        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
            if (i == 2)
                arrowButton.setVisibility(View.VISIBLE);
            else
                arrowButton.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    private void sendToPhoneAtivity()
    {
        Intent mainIntent = new Intent(PagerActivity.this, FirstLoginPage.class);
        startActivity(mainIntent);
        finish();
    }
}
