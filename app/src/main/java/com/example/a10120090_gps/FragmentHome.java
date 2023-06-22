package com.example.a10120090_gps;
// NIM   : 10120090
// Nama  : Muhammad Rizky Muhyi
// Kelas : IF-3

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    // Inisialisasi Variabel
    FusedLocationProviderClient client;
    private GoogleMap mMap;

    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();

    //List Tempat
    LatLng kedai = new LatLng(-6.9630825,107.6564151);
    LatLng basomuslim = new LatLng(-6.9569839,107.6538574);
    LatLng udinramen = new LatLng(-6.9441519,107.6578748);
    LatLng mixue = new LatLng(-6.9574748,107.6477492);
    LatLng basosaturnus = new LatLng(-6.9524581,107.6625136);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inisialisasi View
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Inisialisasi Map Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Inisialisasi Lokasi Client
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        //List Array diambil dari list tempat
        arrayList.add(kedai);
        arrayList.add(basomuslim);
        arrayList.add(udinramen);
        arrayList.add(mixue);
        arrayList.add(basosaturnus);

        //Sinkronisasi Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //Dimana jika map berhasil dijalankan, dan menampilkan title
                mMap = googleMap;
                mMap.addMarker(new MarkerOptions().position(kedai).title("Kedai Al-Bashiiru"));
                mMap.addMarker(new MarkerOptions().position(basomuslim).title("Baso Muslim"));
                mMap.addMarker(new MarkerOptions().position(udinramen).title("Udin Ramen"));
                mMap.addMarker(new MarkerOptions().position(mixue).title("Mixue Ciwastra"));
                mMap.addMarker(new MarkerOptions().position(basosaturnus).title("Bakso Urat Saturnus"));
                for (int i=0;i<arrayList.size();i++){
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                }
            }
        });

        //Cek Kondisi Permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Jikas Permission Sukses, maka memanggil method
            getCurrentLocation();
        }
        else {
            //Jika Permission gagal, maka memanggil method
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        //Return View
        return view;
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        //Inisialisasi mapFragment
        SupportMapFragment mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        //Inisialisasi locationManager
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Cek Kondisi Location Manager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(
                    task -> {

                        //Inisialisasi Lokasi
                        Location location = task.getResult();

                        //Cek Kondisi Lokasi
                        if (location != null) {

                            //Ketika hasil lokasi tidak null maka set latitude dan longitud
                            mapFragment.getMapAsync(googleMap -> {
                                LatLng lokasi = new LatLng(location.getLatitude(),location.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(lokasi).title("Lokasi Anda");
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                                googleMap.addMarker(options);
                            });
                        }
                        else {
                            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void
                                onLocationResult(@NonNull LocationResult locationResult)
                                {
                                    mapFragment.getMapAsync(googleMap -> {
                                        LatLng lokasi = new LatLng(location.getLatitude(),location.getLongitude());
                                        MarkerOptions options = new MarkerOptions().position(lokasi).title("Lokasi Sekarang");
                                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                                        googleMap.addMarker(options);
                                    });
                                }
                            };

                            //Update Lokasi
                            client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    });
        }
        else {
            startActivity(
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}