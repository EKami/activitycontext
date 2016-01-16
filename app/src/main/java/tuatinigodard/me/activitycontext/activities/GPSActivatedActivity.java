package tuatinigodard.me.activitycontext.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import tuatinigodard.me.activitycontext.R;

/**
 * @author GODARD Tuatini on 16/01/16.
 */
public class GPSActivatedActivity extends AppCompatActivity {

    public static final String LOCATION_EXTRA = "location";

    private Location location;
    private Button refreshPositionButton;
    private TextView currentPositionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivated);
        location = getIntent().getExtras().getParcelable(LOCATION_EXTRA);
        refreshPositionButton = (Button) findViewById(R.id.refreshPosition);
        currentPositionTextView = (TextView) findViewById(R.id.currentPositionTv);
        currentPositionTextView.setText(String.format(getString(R.string.your_position), location.getLatitude(), location.getLongitude()));
    }

}
