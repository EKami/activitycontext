package tuatinigodard.me.activitycontext.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by GODARD Tuatini on 16/01/16.
 */
public class LocationUtil implements LocationListener {

    private GPSListener gpsListener;

    public interface GPSListener {
        void onNewLocationReceived(Location location);
        void gpsActivated();
        void gpsDeactivated();
    }

    private LocationManager mLocationManager;
    private Context context;

    public LocationUtil(Context context, GPSListener gpsListener) {
        if (context != null) {
            this.context = context;
            this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        this.gpsListener = gpsListener;
    }

    public void startListener() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // Some devices will take a lot of time to reach a GPS location
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        } else {
            gpsListener.gpsDeactivated();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        gpsListener.onNewLocationReceived(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        gpsListener.gpsActivated();
    }

    @Override
    public void onProviderDisabled(String provider) {
        gpsListener.gpsDeactivated();
    }
}