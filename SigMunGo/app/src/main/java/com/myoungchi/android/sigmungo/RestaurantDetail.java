package com.myoungchi.android.sigmungo;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.myoungchi.android.sigmungo.adapter.RestaurantDetailAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by geni on 2017. 8. 5..
 */

public class RestaurantDetail extends AppCompatActivity implements OnMapReadyCallback{
    private Toolbar toolbar;
    private TextView restaurantName, restaurantPhone, restaurantLocation;
    private Button writeComplain;
    private ViewPager restaurantPhoto;
    private String[] testerRestaurant1 = {
            "더 테라스",     //음식점명
            "경기도 안양시 만안구 안양예술공원로 103번길 김중업 박물관 3층",     //음식점 위치
            "031-689-4540"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail);
        restaurantName = (TextView)findViewById(R.id.restaurant_name);
        restaurantPhone = (TextView)findViewById(R.id.restaurant_phone);
        restaurantLocation = (TextView)findViewById(R.id.restaurant_location);
        restaurantPhoto = (ViewPager)findViewById(R.id.restaurant_photo);
        writeComplain = (Button)findViewById(R.id.writeComplain);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        restaurantName.setText(testerRestaurant1[0]);
        restaurantLocation.setText(testerRestaurant1[1]);
        restaurantPhone.setText(testerRestaurant1[2]);

        restaurantPhoto.setAdapter(new RestaurantDetailAdapter(getApplicationContext()));

        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        writeComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WriteComplain.class));
            }
        });
    }

    //Google map api 구현부
    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng SEOUL = new LatLng(37.417672, 126.918140);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("경기도 안양시 만안구 안양예술공원로 103번길 김중업 박물관 3층");
        markerOptions.snippet("더 테라스");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    //툴바에서 back버튼을 클릭할시에 종료시켜주는 코드
    public void onBackBtnClicked(View v){
        finish();
    }
}
