package com.example.giloli.Activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.giloli.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_google_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng quangtrung = new LatLng(10.852947, 106.629489);
        LatLng quan3 = new LatLng(10.790869, 106.682178);
        LatLng phunhuan = new LatLng(10.911893, 106.679912);
        mMap.addMarker(new MarkerOptions().position(phunhuan).title("Giloli Cơ sở Phú Nhuận"));
        mMap.addMarker(new MarkerOptions().position(quan3).title("Giloli Cơ sở Quận 3"));
        mMap.addMarker(new MarkerOptions().position(quangtrung).title("Giloli Cơ sở Quang Trung"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quangtrung));
        float zoomlevel = 12.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quangtrung, zoomlevel));
    }
}
