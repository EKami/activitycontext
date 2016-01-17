package tuatinigodard.me.activitycontext.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import tuatinigodard.me.activitycontext.exceptions.GpsNotActivatedException;

/**
 * Created by GODARD Tuatini on 16/01/16.
 */
public class LocationUtil {

    private LocationManager mLocationManager;
    private Context context;

    public LocationUtil(Context context) {
        if (context != null) {
            this.context = context;
            this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public Location getLatestKnownLocation() throws GpsNotActivatedException {
        Location location;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // Some devices will take a lot of time to reach a GPS location
            if (location == null && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location == null) {
                    // For a matter of simplicity, do never return a null location
                    location = new Location("DummyProvider");
                }
            }
        } else {
            throw new GpsNotActivatedException();
        }

        return location;
    }
}