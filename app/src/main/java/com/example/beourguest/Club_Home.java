package com.example.beourguest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class Club_Home extends Fragment {
    private SliderAdapter sliderAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.club_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.ViewPager);
        sliderAdapter = new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        super.onViewCreated(view, savedInstanceState);
    }


}
