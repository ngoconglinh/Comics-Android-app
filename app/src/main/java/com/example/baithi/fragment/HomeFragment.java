package com.example.baithi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baithi.CategoryComicsActivity;
import com.example.baithi.ComicsActivity;
import com.example.baithi.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {
    private Button btn_new_comics, btn_trending_comics, btn_detective_comics, btn_fairy_tables, btn_funny_comics;
    SliderView sliderView;
    int[] images = {R.drawable.doraemon,
            R.drawable.conan,
            R.drawable.rocket,
            R.drawable.pokemon,
            R.drawable.songoku,
            R.drawable.bakugan};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderView = view.findViewById(R.id.imgae_slider);

        btn_new_comics = view.findViewById(R.id.btn_new_comics);
        btn_new_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryComicsActivity.class);
                intent.putExtra("NameFolder", "New");
                startActivity(intent);
            }
        });

        btn_trending_comics = view.findViewById(R.id.btn_trending_comics);
        btn_trending_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryComicsActivity.class);
                intent.putExtra("NameFolder", "New");
                startActivity(intent);
            }
        });

        btn_detective_comics = view.findViewById(R.id.btn_detective_comics);
        btn_detective_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryComicsActivity.class);
                intent.putExtra("NameFolder", "Detective");
                startActivity(intent);
            }
        });

        btn_fairy_tables = view.findViewById(R.id.btn_fairy_tables);
        btn_fairy_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryComicsActivity.class);
                intent.putExtra("NameFolder", "Fairy_Tales");
                startActivity(intent);
            }
        });

        btn_funny_comics = view.findViewById(R.id.btn_funny_comics);
        btn_funny_comics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryComicsActivity.class);
                intent.putExtra("NameFolder", "Funny");
                startActivity(intent);
            }
        });



        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        return view;

    }
}
