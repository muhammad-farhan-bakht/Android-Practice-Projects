package com.example.farhan.practicemaps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Boolean mapReady = false;

    static final CameraPosition GULSHAN = CameraPosition.builder()
            .target(new LatLng(24.9207, 667.0882))
            .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

    static final CameraPosition PAKISTAN = CameraPosition.builder()
            .target(new LatLng(30.3753, 69.3451))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();

    static final CameraPosition TOKYO = CameraPosition.builder()
            .target(new LatLng(35.6895, 139.6917))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();

    static final CameraPosition KARACHI = CameraPosition.builder()
            .target(new LatLng(25.0700, 67.2848))
            .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

    MarkerOptions gulshan;
    MarkerOptions subWayGulshan;
    MarkerOptions theBakersGulshan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        Button btnMapCustom = findViewById(R.id.btn_Map_Custom);
        btnMapCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        Button btnMapSatellite = findViewById(R.id.btn_Map_Satellite);
        btnMapSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        Button btnMapHybrid = findViewById(R.id.btn_Map_Hybrid);
        btnMapHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        Button btnPakistan = findViewById(R.id.btn_Map_Pakistan);
        btnPakistan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(PAKISTAN);
            }
        });

        Button btnKarachi = findViewById(R.id.btn_Map_Karachi);
        btnKarachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(KARACHI);
            }
        });

        Button btnTokyo = findViewById(R.id.btn_Map_Tokyo);
        btnTokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    flyTo(TOKYO);
            }
        });

        gulshan = new MarkerOptions()
                .position(new LatLng(24.9207, 67.0882))
                .title("Gulshan-e-Iqbal");
        subWayGulshan = new MarkerOptions()
                .position(new LatLng(24.930254, 67.098374))
                .title("Sub Way");
        theBakersGulshan = new MarkerOptions()
                .position(new LatLng(24.932050, 67.100847))
                .title("The Bakers")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_drop));

        Button btnShowPolyLine = findViewById(R.id.btn_Map_PolyLine);
        btnShowPolyLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPolyLine();
            }
        });

        Button btnShowCircle = findViewById(R.id.btn_Map_Circle);
        btnShowCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCircle();
            }
        });

        Button btnMapStreetView = findViewById(R.id.btn_Map_StreetView);
        btnMapStreetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapStreetView.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mMap = googleMap;

        mMap.addMarker(gulshan);
        mMap.addMarker(subWayGulshan);
        mMap.addMarker(theBakersGulshan);

        flyTo(GULSHAN);
    }

    private void flyTo(CameraPosition target) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);

    }

    private void showPolyLine() {
        if (mapReady) {
            LatLng gulshan = new LatLng(24.9207, 67.0882);
            LatLng subWay = new LatLng(24.930254, 67.098374);
            LatLng theBakers = new LatLng(24.932050, 67.100847);

            mMap.addPolyline(new PolylineOptions().geodesic(true).add(gulshan).add(subWay).add(theBakers));
            mMap.addPolygon(new PolygonOptions().add(gulshan, subWay, theBakers).fillColor(Color.GREEN));
        }
    }

    private void showCircle() {
        if (mapReady) {
            LatLng gulshan = new LatLng(24.9207, 67.0882);
            mMap.addCircle(new CircleOptions()
                    .center(gulshan)
                    .radius(5000)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(64, 0, 255, 0)));
        }
    }

}
