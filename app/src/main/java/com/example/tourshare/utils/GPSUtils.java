package com.example.tourshare.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Locale;



@SuppressLint("MissingPermission")
public class GPSUtils {
    private static LocationManager mLocationManager;

    private static final String TAG = "GPSUtils";

    private static Location mLocation = null;

    private static Activity mContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public GPSUtils(Activity context) {
        this.mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Determine whether the GPS is activated normally
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "Please Open GPS...", Toast.LENGTH_SHORT).show();
            // Return to the GPS navigation setting interface
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivityForResult(intent, 0);
            return;
        }

        // Set query conditions for obtaining geographic location information
        String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
        // get location information
        // If the query request is not set, the parameter passed by the getLastKnownLocation method is LocationManager.GPS_PROVIDER
        Location location = mLocationManager.getLastKnownLocation(bestProvider);
//        getLocationData(location);
        mLocation = location;

        //NMEA0183 Raw data
        //mLocationManager.addNmeaListener(mNmeaMessageListener);

        // Binding listener, there are 4 parameters
        // Parameter 1, device: there are two kinds of GPS_PROVIDER and NETWORK_PROVIDER
        // Parameter 2, location information update cycle, in milliseconds
        // Parameter 3, the minimum distance of position change: when the change of position distance
        // exceeds this value, the position information will be updated
        // parameter 4, listen
        // Remarks: For parameters 2 and 3, if parameter 3 is not 0, then parameter 3 shall prevail;
        // if parameter 3 is 0, it will be updated regularly by time; if both are 0,
        // it will be refreshed at any time

        // Update once every 1 second, or once the minimum displacement change exceeds 1 meter;
        // Note: The update accuracy here is very low, it is recommended to start a Thread in the service, sleep(10000) in run; then execute handler.sendMessage() to update the location
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    /**
     * return query conditions
     *
     * @return
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // Setting the positioning accuracy Criteria.ACCURACY_COARSE is relatively rough, Criteria.ACCURACY_FINE is relatively fine
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // Set whether to require speed
        criteria.setSpeedRequired(true);
        // Set whether to allow carrier charges
        criteria.setCostAllowed(false);
        // Set whether to require orientation information
        criteria.setBearingRequired(true);
        // Set whether to require altitude information
        criteria.setAltitudeRequired(true);
        // Set demand for power
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


    /**
     * @return Location--->getLongitude() gets longitude/getLatitude() gets latitude
     */
    public static Location getLocation() {
        Log.e("---------","5555555");
        if (mLocation == null) {
            Log.e("GPSUtils", "setLocationData: Get current location information is empty");
            return null;
        }
        return mLocation;
    }

    public static String getLocalCity(){
        if (mLocation==null){
            Log.e("GPSUtils", "getLocalCity: Get city information is empty");
            return "";
        }
        List<Address> result = getAddress(mLocation);

        String city = "";
        if (result != null && result.size() > 0) {
            city = result.get(0).getLocality();//get city
        }
        return city;
    }

    public static String getAddressStr(){
        if (mLocation==null){
            Log.e("GPSUtils", "getAddressStr: Get address details is empty");
            return "";
        }
        List<Address> result = getAddress(mLocation);

        String address = "";
        if (result != null && result.size() > 0) {
            address = result.get(0).getAddressLine(0);//Get detailed address
        }
        return address;
    }


    // Location monitoring
    private static LocationListener locationListener = new LocationListener() {

        //Triggered when the location information changes
        public void onLocationChanged(Location location) {
            mLocation = location;
        }

        //Triggered when GPS state changes
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // When GPS status is visible
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "The current GPS status is visible");
                    break;
                // When the GPS status is out of service area
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "The current GPS status is out of service area");
                    break;
                // When the GPS status is out of service
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "The current GPS status is out of service");
                    break;
            }
        }

        //Triggered when GPS is on
        public void onProviderEnabled(String provider) {
            Location location = mLocationManager.getLastKnownLocation(provider);
            mLocation = location;
        }

        //Triggered when GPS is disabled
        public void onProviderDisabled(String provider) {
            mLocation = null;
        }
    };

    // Get address information
    private static List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(mContext, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
