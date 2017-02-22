package cstu.handypharmacy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cstu.handypharmacy.model.Pharmacy;

public class StoreRegisterMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapClickListener, View.OnClickListener {

    private static String TAG = "LatLng";

    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;
    private SupportMapFragment mSupportMapFragment;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    private TextView mTapTextView;
    private Marker marker;
    private Button mButton;
    private double lat, lng;

    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.regis_map);
        mTapTextView = (TextView) findViewById(R.id.tap_text);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_support_map_fragment);

        mapFragment.getMapAsync(this);
        mButton = (Button) findViewById(R.id.save);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        //set Map Type
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.setOnMapClickListener(this);
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

    //get LatLng on tapped
    @Override
    public void onMapClick(LatLng point) {
        mTapTextView.setText("Tapped, Point=" + point);
        lat = point.latitude;
        lng = point.longitude;
        if (marker != null) {
            marker.remove();
        }
        //add marker on same position of tapped
        marker = mGoogleMap.addMarker(new MarkerOptions()
                //marker option
                .title("Your Store")
                .snippet("Here?")
                .position(new LatLng(point.latitude, point.longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon64x48))
                .draggable(true).visible(true));
    }

    @Override
    public void onClick(View v) {

        Pharmacy pharmacy = Pharmacy.getInstance();

        pharmacy.setLat(lat);
        pharmacy.setLng(lng);

        Intent intent = new Intent(v.getContext(), RegisterDrugStoreActivity.class);
        startActivity(intent);

        /*
        Toast.makeText(this, "You Click!!", Toast.LENGTH_SHORT).show();
        if(v.getId() == R.id.save){

            final ProgressDialog progress = new ProgressDialog(v.getContext());
            progress.setMessage("Saving...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();

            GetPharmacy.saveLatLng(lat, lng, new GetPharmacy.DataCallback() {
                @Override
                public void onFinish() {
                    progress.dismiss();
                    Log.i(TAG, "insert complete");
                }
            });
        }
        */
    }
}