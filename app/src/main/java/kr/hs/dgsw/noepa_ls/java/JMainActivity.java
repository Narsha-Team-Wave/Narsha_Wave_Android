package kr.hs.dgsw.noepa_ls.java;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kr.hs.dgsw.noepa_ls.R;

import com.github.pwittchen.neurosky.library.NeuroSky;
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException;
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener;
import com.github.pwittchen.neurosky.library.message.enums.BrainWave;
import com.github.pwittchen.neurosky.library.message.enums.Signal;
import com.github.pwittchen.neurosky.library.message.enums.State;
import java.util.Locale;
import java.util.Set;

public class JMainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "NeuroSky";
    private NeuroSky neuroSky;
    private int chekcATTENTION = 0;

    TextView tvState;
    TextView tvAttention;
    TextView tvMeditation;
    TextView tvBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvState = (TextView)findViewById(R.id.tv_state);
        tvAttention = (TextView)findViewById(R.id.tv_attention);
        tvMeditation = (TextView)findViewById(R.id.tv_meditation);
        tvBlink = (TextView)findViewById(R.id.tv_blink);
        findViewById(R.id.btn_connect).setOnClickListener(customClick);
        findViewById(R.id.btn_disconnect).setOnClickListener(customClick);
        findViewById(R.id.btn_start_monitoring).setOnClickListener(customClick);
        findViewById(R.id.btn_stop_monitoring).setOnClickListener(customClick);
        neuroSky = createNeuroSky();
    }

    @Override protected void onResume() {
        super.onResume();
        if (neuroSky != null && neuroSky.isRawSignalEnabled()) {
            neuroSky.startMonitoring();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (neuroSky != null && neuroSky.isRawSignalEnabled()) {
            neuroSky.stopMonitoring();
        }
    }

    @NonNull private NeuroSky createNeuroSky() {
        return new NeuroSky(new ExtendedDeviceMessageListener() {
            @Override public void onStateChange(State state) {
                handleStateChange(state);
            }

            @Override public void onSignalChange(Signal signal) {
                handleSignalChange(signal);
            }

            @Override public void onBrainWavesChange(Set<BrainWave> brainWaves) {
                handleBrainWavesChange(brainWaves);
            }
        });
    }


    private void handleStateChange(final State state) {
        if (neuroSky != null && state.equals(State.CONNECTED)) {
            neuroSky.startMonitoring();
        }

        tvState.setText(state.toString());
        Log.d(LOG_TAG, state.toString());
    }

    private void handleSignalChange(final Signal signal) {
        switch (signal) {
            case ATTENTION:
                tvAttention.setText(getFormattedMessage("attention: %d", signal));
                chekcATTENTION = Integer.parseInt(getFormattedMessage("%d", signal));
                break;
            case MEDITATION:
                tvMeditation.setText(getFormattedMessage("meditation: %d", signal));
                break;
            case BLINK:
                tvBlink.setText(getFormattedMessage("blink: %d", signal));
                break;
        }

        Log.d(LOG_TAG, String.format("%s: %d", signal.toString(), signal.getValue()));
    }

    private String getFormattedMessage(String messageFormat, Signal signal) {
        return String.format(Locale.getDefault(), messageFormat, signal.getValue());
    }

    private void handleBrainWavesChange(final Set<BrainWave> brainWaves) {
        for (BrainWave brainWave : brainWaves) {
            Log.d(LOG_TAG, String.format("%s: %d", brainWave.toString(), brainWave.getValue()));

            if(chekcATTENTION>0){

                switch(brainWave.toString()){
                    case "DELTA":
                        ((TextView)findViewById(R.id.tv_delta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "THETA":
                        ((TextView)findViewById(R.id.tv_theta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "LOW_ALPHA":
                        ((TextView)findViewById(R.id.tv_lowalpha)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "HIGH_ALPHA":
                        ((TextView)findViewById(R.id.tv_highalpha)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "LOW_BETA":
                        ((TextView)findViewById(R.id.tv_lowbeta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "HIGH_BETA":
                        ((TextView)findViewById(R.id.tv_highbeta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "LOW_GAMMA":
                        ((TextView)findViewById(R.id.tv_lowgamma)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    case "MID_GAMMA":
                        ((TextView)findViewById(R.id.tv_midgamma)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        break;
                    default:
                        Log.d(LOG_TAG, "unhandled signal");
                        break;
                }
            }else{
                Toast.makeText(getApplicationContext(), "착용을 바르게 해주세요", Toast.LENGTH_SHORT).show();
            }

        }
    }
    View.OnClickListener customClick =new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_connect:
                    try {
                        neuroSky.connect();
                    } catch (BluetoothNotEnabledException e) {
                        Toast.makeText(JMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, e.getMessage());
                    }
                    break;
                case R.id.btn_disconnect:
                    neuroSky.disconnect();
                    break;
                case R.id.btn_start_monitoring:
                    neuroSky.startMonitoring();
                    break;
                case R.id.btn_stop_monitoring:
                    neuroSky.stopMonitoring();
                    break;
            }
        }
    };


}
