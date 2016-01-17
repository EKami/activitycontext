package tuatinigodard.me.activitycontext.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tuatinigodard.me.activitycontext.R;
import tuatinigodard.me.activitycontext.exceptions.GpsNotActivatedException;
import tuatinigodard.me.activitycontext.utils.LocationUtil;

/**
 * @author GODARD Tuatini on 16/01/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final int GPS_PERMISSION_REQUEST_CODE = 10;
    private Button showMeMyPositionButton;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        showMeMyPositionButton = (Button) findViewById(R.id.showMeMyPosition);
        errorTv = (TextView) findViewById(R.id.errorTv);
        showMeMyPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGPSPermissions();
            }
        });
    }

    /**
     * Permission asked at the click of the button
     */
    private void requestGPSPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                GPS_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.gps_permission_granted, Toast.LENGTH_SHORT).show();
                    try {
                        Location location = LocationUtil.getLatestKnownLocation(this);
                        Intent gpsActivityIntent = new Intent(MainActivity.this, LocationPresenterActivity.class);
                        gpsActivityIntent.putExtra(LocationPresenterActivity.LOCATION_EXTRA, location);
                        startActivity(gpsActivityIntent);
                        finish();
                    } catch (GpsNotActivatedException e) {
                        errorTv.setText(getString(R.string.activate_gps));
                    }
                } else {
                    errorTv.setText(R.string.cannot_continue_without_gps_permissions);
                }
            }
        }
    }
}
