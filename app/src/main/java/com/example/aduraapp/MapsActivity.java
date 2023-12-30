package com.example.aduraapp;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.aduraapp.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements LocationListener, MapView.OnTouchListener {

    private MapView mapView;
    private ActivityMapsBinding binding;
    private LocationManager locationManager;
    private IMapController mapController;
    private Marker marker;

    private boolean isFirstLayout = true;

    private GestureDetector gestureDetector;
    private Timer doubleTapTimer;
    private String tipe_laporan;
    private Button loc;
    private String addressLine;
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init osmdroid configuration
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tipe_laporan = getIntent().getStringExtra("TIPE_LAPORAN");
        Log.d("TAG", "onCreate: "+ tipe_laporan);

        loc = findViewById(R.id.buttonSendLocation);
        mapView = findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        mapController = mapView.getController();
        mapController.setZoom(9.5);

        // Inisialisasi LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Cek izin lokasi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Mendapatkan lokasi terkini
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                onLocationChanged(lastKnownLocation); // Menangani lokasi terkini
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            // Handle jika izin tidak diberikan
            // ...
        }

        mapView.setOnTouchListener(this);

        // Inisialisasi GestureDetector untuk double click
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Tanggapan terhadap double click
                doubleTap = true;
                return super.onDoubleTap(e);
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("MedisCreate".equals(tipe_laporan)){
                    double latitude = marker.getPosition().getLatitude();
                    double longitude = marker.getPosition().getLongitude();
                    getAddressFromLocation(latitude, longitude);
                    Intent intent = new Intent(MapsActivity.this, MedisCreateActivity.class);
                    intent.putExtra("ADDRESS", addressLine);
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    startActivity(intent);
                }
            }
        });
    }
        private void getAddressFromLocation(double latitude, double longitude) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    // Dapatkan alamat dari hasil geocoder
                    Address address = addresses.get(0);
                    addressLine = address.getAddressLine(0);

                    // Tampilkan atau gunakan alamat sesuai kebutuhan
                    Log.d("MapsActivity", "Address: " + addressLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Memperbarui peta dengan lokasi terkini
        GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        mapController.setCenter(currentLocation);
        Log.d("MapsActivity", "Location changed: " + location.getLatitude() + ", " + location.getLongitude());

        // Memperbarui atau menambahkan marker
        if (marker == null) {
            marker = new Marker(mapView);
            marker.setPosition(currentLocation);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(marker);
        } else {
            marker.setPosition(currentLocation);
        }
        mapView.invalidate();

        // Hentikan pembaruan lokasi setelah satu kali mendapatkan lokasi
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Handle sentuhan pada peta, misalnya drag marker
        gestureDetector.onTouchEvent(event);

        if (doubleTap) {
            // Handle double click pada peta
            GeoPoint doubleTapLocation = (GeoPoint) mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
            moveMarker(doubleTapLocation);
            doubleTap = false;
        }
        return false;
    }

    private void moveMarker(GeoPoint newLocation) {
        // Memindahkan marker ke lokasi baru
        if (marker != null) {
            marker.setPosition(newLocation);
            mapView.invalidate();
        }
    }

    // Implementasi method-method lainnya
}
