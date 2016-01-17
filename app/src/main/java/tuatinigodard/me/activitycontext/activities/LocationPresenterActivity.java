package tuatinigodard.me.activitycontext.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tuatinigodard.me.activitycontext.R;
import tuatinigodard.me.activitycontext.exceptions.GpsNotActivatedException;
import tuatinigodard.me.activitycontext.utils.LocationUtil;

/**
 * @author GODARD Tuatini on 16/01/16.
 */
public class LocationPresenterActivity extends AppCompatActivity {

    public static final String LOCATION_EXTRA = "location";

    private Location location;
    private TextView currentPositionTextView;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivated);
        initComponents();
    }

    private void initComponents() {
        location = getIntent().getExtras().getParcelable(LOCATION_EXTRA);
        Button refreshPositionButton = (Button) findViewById(R.id.refreshPosition);
        currentPositionTextView = (TextView) findViewById(R.id.currentPositionTv);
        errorTv = (TextView) findViewById(R.id.errorTv);
        currentPositionTextView.setText(String.format(getString(R.string.your_position), location.getLatitude(), location.getLongitude()));
        refreshPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Clear the error textView first
                    errorTv.setText("");
                    location = LocationUtil.getLatestKnownLocation(LocationPresenterActivity.this);
                    currentPositionTextView.setText(String.format(getString(R.string.your_position), location.getLatitude(), location.getLongitude()));
                } catch (GpsNotActivatedException e) {
                    errorTv.setText(R.string.activate_gps);
                }
            }
        });
    }

}
