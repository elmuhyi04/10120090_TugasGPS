package com.example.a10120090_gps;
// NIM   : 10120090
// Nama  : Muhammad Rizky Muhyi
// Kelas : IF-3


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
public class SlideViewPagerAdapter extends PagerAdapter {

    Context ctx;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_screen,container,false);


        ImageView logo=view.findViewById(R.id.logo);
        ImageView idn1=view.findViewById(R.id.idn1);
        ImageView idn2=view.findViewById(R.id.idn2);
        ImageView idn3=view.findViewById(R.id.idn3);

        TextView txt_judul=view.findViewById(R.id.txt_judul);
        TextView txt_desc=view.findViewById(R.id.txt_desc);
        TextView txt_deskripsi=view.findViewById(R.id.txt_deskripsi);

        ImageView next=view.findViewById(R.id.img_next);
        ImageView back=view.findViewById(R.id.img_back);


        Button btnGetStarted=view.findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position-1);
            }
        });

        switch (position)
        {
            case 0:
                logo.setImageResource(R.drawable.logolokasi);
                idn1.setImageResource(R.drawable.selected);
                idn2.setImageResource(R.drawable.unselected);
                idn3.setImageResource(R.drawable.unselected);

                txt_judul.setText("♫ Akses Lokasi Pengguna ♫");
                txt_desc.setText("~ Your Location ~");
                txt_deskripsi.setText("Aplikasi ini dapat memberikan informasi keberadaan lokasi pengguna di Google Maps pada saat digunakan");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
            case 1:
                logo.setImageResource(R.drawable.logotempatmakan);
                idn1.setImageResource(R.drawable.unselected);
                idn2.setImageResource(R.drawable.selected);
                idn3.setImageResource(R.drawable.unselected);

                txt_judul.setText("♫ Tempat Makan Favorit ♫");
                txt_desc.setText("~ Rekomendasi 5 Tempat Makan Favorit By El Muhyi ~");
                txt_deskripsi.setText("Pengguna dapat melihat rekomendasi tempat makan yang murah, lokasi strategis, serta pelayanan yang cepat");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:
                logo.setImageResource(R.drawable.logoroket);
                idn1.setImageResource(R.drawable.unselected);
                idn2.setImageResource(R.drawable.unselected);
                idn3.setImageResource(R.drawable.selected);

                txt_judul.setText("♫ Inisialisasi Now ♫");
                txt_desc.setText("~ Enjoy Guys ~");
                txt_deskripsi.setText("");
                next.setVisibility(View.GONE);
                break;
        }
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
