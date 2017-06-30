package com.cafesuspenso.ufcg.cafesuspenso.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.cafesuspenso.ufcg.cafesuspenso.Activity.MainActivity;
import com.cafesuspenso.ufcg.cafesuspenso.Model.Cafeteria;
import com.cafesuspenso.ufcg.cafesuspenso.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myLocation;
    private boolean permissionAsked;
    private static final int LOCATION_REQUEST_CODE = 12;

    private LocationManager mLocationManager;
    private MainActivity main;
    private List<Cafeteria> cafeterias;
    Cafeteria selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
        main.moveBottomBarDown();
        getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadMarkers(mMap);

        myLocation = getMyLocation();
        if(myLocation != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f));
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        addComplaintInUserLocation(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selected = null;
                main.setCafeteriaSelected(null);
                for(Cafeteria c: cafeterias){
                    if(c.getLocation().equals(marker.getPosition())) {
                        main.setCafeteriaSelected(c);
                        selected = c;
                        break;
                    }
                }

                if(selected != null)
                    main.moveBottomBarUp(marker.getTitle(), selected.getAvailableCoffee());
                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {


            @Override
            public void onMapClick(LatLng latLng) {
                main.moveBottomBarDown();
            }
        });
    }

    private void loadMarkers(GoogleMap mMap) {
        cafeterias = new ArrayList<>();
        LatLng caf1 = new LatLng(-7.24833418, -35.89189947);
        LatLng caf2 = new LatLng(-7.24659935, -35.89460313);
        LatLng caf3 = new LatLng(-7.24530218, -35.89180147);
        LatLng caf4 = new LatLng(-7.24749935, -35.89260113);

        //TODO CARREGAR MAPA COM CAFETERIAS
        Cafeteria cafeteria1 = new Cafeteria("João", "joao@gmail.com", "12398", "Café do João", "O café do João é o café mais saudavel do bairro, venha já conferir...", "CNPJ AQUI", caf1, "http://valealternativo.com.br/uploads/noticias/1aa8d5668ea39233ad5079ffbec2db37_400.JPG");
        cafeteria1.setAvailableCoffee(0);
        Cafeteria cafeteria2 = new Cafeteria("Maria", "maria@gmail.com", "12398", "Pé de café Cafeteria", "O café da Maria é o café mais saudavel do bairro, venha já conferir...", "CNPJ AQUI", caf2, "http://www.radioguaiba.com.br/wp-content/uploads/2015/09/20150917_121306.jpg");
        cafeteria2.setAvailableCoffee(15);
        Cafeteria cafeteria3 = new Cafeteria("Antonio", "antonio@gmail.com", "12398", "Café da Praça", "O café da Praça é o café mais saudavel do bairro, venha já conferir...", "CNPJ AQUI", caf3, "http://bloglovers.com.br/wp-content/uploads/2014/06/cafe-da-praca-2_Fotor.jpg");
        cafeteria3.setAvailableCoffee(3);
        Cafeteria cafeteria4 = new Cafeteria("Jardel", "jardel@gmail.com", "12398", "Café do Bairro", "O café do Bairro é o café mais saudavel do bairro, venha já conferir...", "CNPJ AQUI", caf4, "http://www.centerjeans.com.br/lazer-e-alimentacao/img/cafe-do-bairro.jpg");
        cafeteria4.setAvailableCoffee(0);

        cafeterias.add(cafeteria1);
        cafeterias.add(cafeteria2);
        cafeterias.add(cafeteria3);
        cafeterias.add(cafeteria4);
        Bitmap coffeeBig = BitmapFactory.decodeResource(getResources(), R.drawable.coffee);
        Bitmap resized = Bitmap.createScaledBitmap(coffeeBig, 50, 50, true);
        BitmapDescriptor coffee = BitmapDescriptorFactory.fromBitmap(resized);

        Bitmap coffeegrayBig = BitmapFactory.decodeResource(getResources(), R.drawable.coffeegray);
        Bitmap resized2 = Bitmap.createScaledBitmap(coffeegrayBig, 50, 50, true);
        BitmapDescriptor coffeegray = BitmapDescriptorFactory.fromBitmap(resized2);
        for(Cafeteria c : cafeterias){
            if(c.getAvailableCoffee() > 0){
                mMap.addMarker(new MarkerOptions().position(c.getLocation()).title(c.getPlacename()).icon(coffee));
            } else {
                mMap.addMarker(new MarkerOptions().position(c.getLocation()).title(c.getPlacename()).icon(coffeegray));
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.content_main, viewGroup, false);

        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    private void addComplaintInUserLocation(boolean isFromMap) {
        myLocation = getMyLocation();
        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f));

            if (myLocation == null)
                openMapPermissionDialog();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Your gps is off, please turn on and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng getMyLocation() {
        Location first = getLastBestLocation();
        if (first != null)
            return new LatLng(first.getLatitude(), first.getLongitude());

        Location second = getLastKnownLocation();
        if (second != null)
            return new LatLng(second.getLatitude(), second.getLongitude());

        return null;
    }

    private Location getLastKnownLocation() {
        if (mLocationManager == null) return null;
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private Location getLastBestLocation() {

        Location response = null;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (locationGPS != null) {
                GPSLocationTime = locationGPS.getTime();
            }

            long NetLocationTime = 0;

            if (locationNet != null) {
                NetLocationTime = locationNet.getTime();
            }

            if (0 < GPSLocationTime - NetLocationTime) {
                response = locationGPS;
            } else {
                response = locationNet;
            }
        }
        return response;
    }

    private void verifyGpsState() {
        boolean locationPermission = ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (locationPermission) {

            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }
        } else if (!permissionAsked) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);

            permissionAsked = true;

        }


        /**
         mLocationFAB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        addComplaintInUserLocation(true);
        }
        });
         */
    }

    @Override
    public void onResume() {
        super.onResume();

        verifyGpsState();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.message_gps_off))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void openMapPermissionDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(getString(R.string.map_permission_error));
        builder1.setTitle(getString(R.string.map_permission_error_title));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
