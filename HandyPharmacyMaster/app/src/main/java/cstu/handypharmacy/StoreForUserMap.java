package cstu.handypharmacy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cstu.handypharmacy.controller.GetPharmacy;
import cstu.handypharmacy.model.Pharmacy;

public class StoreForUserMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapClickListener {

    private static final String TAG = "StoreForUserMap";

    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;
    private SupportMapFragment mSupportMapFragment;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    int i = 0;


    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_map);
        //get latlng
        //mTapTextView = (TextView) findViewById(R.id.tap_text);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_support_map_fragment);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        //set Map Type
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //mGoogleMap.setOnMapClickListener(this);
        buildGoogleApiClient();

        mGoogleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            }
            return;
        }
        //current location button
        mGoogleMap.setMyLocationEnabled(true);
        //show traffic
        mGoogleMap.setTrafficEnabled(true);
        //zoom control
        mUiSettings.setZoomControlsEnabled(true);
        //compass
        mUiSettings.setCompassEnabled(true);


        final ProgressDialog progress = new ProgressDialog(StoreForUserMap.this);
        progress.setMessage("Loading Store...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        GetPharmacy.getStoreData(new GetPharmacy.DataCallback() {
            @Override
            public void onFinish() {

                Log.i(TAG, "" + Pharmacy.pharmacies.size());
                for (i = 0; i < Pharmacy.pharmacies.size(); i++) {
                    if (Pharmacy.pharmacies.get(i).getStatus() == 1) {
                        addMarkerToGoogleMap(Pharmacy.pharmacies.get(i).getStore_name() + " Store Tel : " + Pharmacy.pharmacies.get(i).getStore_tel()
                                , "Day Open : " + Pharmacy.pharmacies.get(i).getDay_open() + " Time Open/Close : " + Pharmacy.pharmacies.get(i).getOpenTime() + "-" + Pharmacy.pharmacies.get(i).getCloseTime()
                                , Pharmacy.pharmacies.get(i).getLat()
                                , Pharmacy.pharmacies.get(i).getLng());
                    } else {
                        continue;
                    }
                }

                progress.dismiss();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(getIntent());
                finish();
            }
        }
    }

    //new googleApiClient open app and go to current location
    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //connect and get current location
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //get current location
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    public void addMarkerToGoogleMap(String title, String snippet, double lat, double lng) {
        if (null != mGoogleMap) {
            MarkerOptions options1 = new MarkerOptions();
            options1.title(title);
            options1.snippet(snippet);
            options1.position(new LatLng(lat, lng));
            options1.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon64x48));
            options1.draggable(true).visible(true);

            MarkerOptions options2 = new MarkerOptions();
            options2.title(title);
            options2.snippet(snippet);
            options2.position(new LatLng(lat, lng));
            options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon_star64x48));
            options2.draggable(true).visible(true);

            if (Pharmacy.pharmacies.get(i).getQuality() == 0) {
                mGoogleMap.addMarker(options1);
            } else {
                mGoogleMap.addMarker(options2);
            }
        }
    }
}