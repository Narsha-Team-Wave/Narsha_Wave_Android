package kr.hs.dgsw.noepa_ls.java;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import kr.hs.dgsw.noepa_ls.R;
import kr.hs.dgsw.noepa_ls.custom.RadarMarkerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.pwittchen.neurosky.library.NeuroSky;
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException;
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener;
import com.github.pwittchen.neurosky.library.message.enums.BrainWave;
import com.github.pwittchen.neurosky.library.message.enums.Signal;
import com.github.pwittchen.neurosky.library.message.enums.State;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class JMainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "NeuroSky";
    private int MY_PERMISSION_ACCESS_ALL = 100;
    private NeuroSky neuroSky;
    private int chekcATTENTION = 0;

    boolean check = false;
    int checkAttenTion = 0;
    int checkMeditation = 0;
    int checkBlink = 0;

    ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
    private int[] brainWaveList = new int[8];

    TextView tvState;
    TextView tvAttention;
    TextView tvMeditation;
    TextView tvBlink;
    TextView tvDelta, tvTheta, tvLowalpha, tvHighalpha, tvLowbeta, tvHighbeta, tvLowgamma, tvMidgamma;
    RadarChart chart1;
    LineChart LineChart1;
    LineChart LineChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvState = (TextView)findViewById(R.id.tv_state);
        tvAttention = (TextView)findViewById(R.id.tv_attention);
        tvMeditation = (TextView)findViewById(R.id.tv_meditation);
        tvBlink = (TextView)findViewById(R.id.tv_blink);

        tvDelta = ((TextView)findViewById(R.id.tv_delta));
        tvTheta = ((TextView)findViewById(R.id.tv_theta));
        tvLowalpha =((TextView)findViewById(R.id.tv_lowalpha));
        tvHighalpha =((TextView)findViewById(R.id.tv_highalpha));
        tvLowbeta =((TextView)findViewById(R.id.tv_lowbeta));
        tvHighbeta =((TextView)findViewById(R.id.tv_highbeta));
        tvLowgamma =((TextView)findViewById(R.id.tv_lowgamma));
        tvMidgamma =((TextView)findViewById(R.id.tv_midgamma));

        findViewById(R.id.btn_connect).setOnClickListener(customClick);
        findViewById(R.id.btn_disconnect).setOnClickListener(customClick);
        findViewById(R.id.btn_start_monitoring).setOnClickListener(customClick);
        findViewById(R.id.btn_stop_monitoring).setOnClickListener(customClick);

        if (android.os.Build.VERSION.SDK_INT >= 31){
            Log.d("TAG", "asd");
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.BLUETOOTH_SCAN,
                        android.Manifest.permission.BLUETOOTH_ADVERTISE,
                        android.Manifest.permission.BLUETOOTH_CONNECT
                };
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL);
            }
        }else{
            Log.d("TAG", "asd2");
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL);
            }
        }


        neuroSky = createNeuroSky();


        chart1 = findViewById(R.id.chart1);
        LineChart1 = findViewById(R.id.LineChart1);
        LineChart2 = findViewById(R.id.LineChart2);

        initChart();
        initLineChart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_ACCESS_ALL) {
            if (grantResults.length > 0) {
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) System.exit(0);
                }
            }
        }
    }

    private void initLineChart() {

        LineChart1.setDrawGridBackground(true);
        LineChart1.setBackgroundColor(Color.BLACK);
        LineChart1.setGridBackgroundColor(Color.BLACK);

        LineChart2.setDrawGridBackground(true);
        LineChart2.setBackgroundColor(Color.BLACK);
        LineChart2.setGridBackgroundColor(Color.BLACK);
        // description text
        // description text
        LineChart1.getDescription().setEnabled(true);
        LineChart2.getDescription().setEnabled(true);
        Description des1 = LineChart1.getDescription();
        Description des2 = LineChart2.getDescription();
        des1.setEnabled(true);
        des1.setText("Real-Time DATA");
        des1.setTextSize(15f);
        des1.setTextColor(Color.WHITE);
        des2.setEnabled(true);
        des2.setText("Real-Time DATA");
        des2.setTextSize(15f);
        des2.setTextColor(Color.WHITE);

        LineChart1.setTouchEnabled(false);
        LineChart2.setTouchEnabled(false);


        LineChart1.setDragEnabled(false);
        LineChart1.setScaleEnabled(false);
        LineChart2.setDragEnabled(false);
        LineChart2.setScaleEnabled(false);

        LineChart1.setAutoScaleMinMaxEnabled(true);
        LineChart2.setAutoScaleMinMaxEnabled(true);


        LineChart1.setPinchZoom(false);
        LineChart2.setPinchZoom(false);

        LineChart1.getXAxis().setDrawGridLines(true);
        LineChart1.getXAxis().setDrawAxisLine(false);
        LineChart2.getXAxis().setDrawGridLines(true);
        LineChart2.getXAxis().setDrawAxisLine(false);

        LineChart1.getXAxis().setEnabled(true);
        LineChart1.getXAxis().setDrawGridLines(false);
        LineChart2.getXAxis().setEnabled(true);
        LineChart2.getXAxis().setDrawGridLines(false);


        Legend l1 = LineChart1.getLegend();
        Legend l2 = LineChart2.getLegend();
        l1.setEnabled( true );
        l1.setFormSize(10f); // set the size of the legend forms/shapes
        l2.setEnabled( true );
        l2.setFormSize(10f);

        l1.setTextSize(12f);
        l1.setTextColor(Color.WHITE);
        l2.setTextSize(12f);
        l2.setTextColor(Color.WHITE);

//Y축

//Y축
        YAxis leftAxis1 = LineChart1.getAxisLeft();
        YAxis leftAxis2 = LineChart2.getAxisLeft();
        leftAxis1.setEnabled( true );
        leftAxis1.setTextColor( Color.RED);
        leftAxis1.setDrawGridLines(true);
        leftAxis1.setGridColor(Color.GRAY);
        leftAxis2.setEnabled( true );
        leftAxis2.setTextColor( Color.RED);
        leftAxis2.setDrawGridLines(true);
        leftAxis2.setGridColor(Color.GRAY);


        YAxis rightAxis1 = LineChart1.getAxisRight();
        rightAxis1.setEnabled(false);
        YAxis rightAxis2 = LineChart1.getAxisRight();
        rightAxis2.setEnabled(false);


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(0F, 0F));
        LineDataSet set1 = new LineDataSet(yVals1, "Meditation");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha (65);
        set1.setFillColor (ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        LineData lineData1 = new LineData(set1);
        lineData1.setValueTextColor(Color.WHITE);
        lineData1.setValueTextSize(9f);

        // set data
        LineChart1.setData(lineData1);

        //


        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        yVals2.add(new Entry(0F, 0F));
        LineDataSet set2 = new LineDataSet(yVals2, "Meditation");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(ColorTemplate.getHoloBlue());
        set2.setValueTextColor(ColorTemplate.getHoloBlue());
        set2.setLineWidth(1.5f);
        set2.setDrawCircles(false);
        set2.setDrawValues(false);
        set2.setFillAlpha (65);
        set2.setFillColor (ColorTemplate.getHoloBlue());
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setDrawCircleHole(false);

        LineData lineData2 = new LineData(set2);
        lineData2.setValueTextColor(Color.WHITE);
        lineData2.setValueTextSize(9f);

        // set data
        LineChart2.setData(lineData2);




// don't forget to refresh the drawing
        LineChart1.invalidate();
        LineChart2.invalidate();
    }

    private void initChart() {
        Log.d(LOG_TAG, "initChart");
        chart1.setBackgroundColor(Color.rgb(60, 65, 82));

        chart1.getDescription().setEnabled(false);

        chart1.setWebLineWidth(1f);
        chart1.setWebColor(Color.LTGRAY);
        chart1.setWebLineWidthInner(1f);
        chart1.setWebColorInner(Color.LTGRAY);
        chart1.setWebAlpha(100);



        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(chart1);

        chart1.setMarker(mv); // Set the marker to the chart
        setData();
//        val xAxis: XAxis = chart1.getXAxis()
        XAxis xAxis = chart1.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("data1");
        labels.add("data2");
        labels.add("data3");
        labels.add("data4");
        labels.add("data5");
        labels.add("data6");
        labels.add("data7");
        labels.add("data8");

        IndexAxisValueFormatter formatData = new IndexAxisValueFormatter(labels);
        xAxis.setValueFormatter(formatData);

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart1.getYAxis();
        yAxis.setLabelCount(8, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum( 0f);
        //yAxis.axisMaximum = 100f
        yAxis.setDrawLabels(false);

        Legend l = chart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }
    private void  addEntry1( Double num) {
        LineData data1 = LineChart1.getData();
        if (data1 == null) {
            data1 = new LineData();
            LineChart1.setData(data1);
        }

        ILineDataSet set1 = data1.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well
        if (set1 == null) {
            set1 = createSet();
            data1.addDataSet( set1);
        }
        //data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);

        data1.addEntry(
                new Entry(set1.getEntryCount(),
                        num.floatValue()), 0
        );
        data1.notifyDataChanged();

        // let the chart know it's data has changed
        LineChart1.notifyDataSetChanged();
        LineChart1.setVisibleXRangeMaximum(150F);
        // this automatically refreshes the chart (calls invalidate())
        LineChart1.moveViewTo(data1.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);
    }



    private void addEntry2( Double num) {

        LineData data2 = LineChart2.getData();
        if (data2 == null) {
            data2 = new LineData();
            LineChart2.setData(data2);
        }

        ILineDataSet set2 = data2.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well
        if (set2 == null) {
            set2 = createSet();
            data2.addDataSet( set2);
        }
        //data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);

        data2.addEntry(
                new Entry(set2.getEntryCount(),
                        num.floatValue()), 0
        );
        data2.notifyDataChanged();

        // let the chart know it's data has changed
        LineChart2.notifyDataSetChanged();
        LineChart2.setVisibleXRangeMaximum(150F);
        // this automatically refreshes the chart (calls invalidate())
        LineChart2.moveViewTo(set2.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);


    }
    private ILineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(1f);
        set.setDrawValues(false);
        set.setValueTextColor(Color.WHITE);
        set.setColor(Color.WHITE);
        set.setMode  (LineDataSet.Mode.LINEAR);
        set.setDrawCircles(false);
        set.setHighLightColor (Color.rgb(190, 190, 190));
        return set;
    }

    private void setData() {
        float mul = 80f;
        float min = 20f;
        float cnt = 8;

        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for(int i = 0 ; i<cnt ; i++){
            float val1 =  ((float)(Math.random() * mul) + min);
            entries1.add(new RadarEntry(val1));
            float val2 = (float)(Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled( true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet>  sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        // sets.add(set2)
        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);
        chart1.setData(data);
        chart1.invalidate();
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
        int  num = Integer.parseInt(getFormattedMessage("%d", signal));
        switch (signal) {
            case ATTENTION:
                if (num != checkAttenTion) {
                    tvAttention.setText(getFormattedMessage("attention: %d", signal));
                    checkAttenTion = num;
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    addEntry1((double) checkAttenTion);
                                }
                            }
                    );

                    check = true;
                } else
                    check = false;

                break;
            case MEDITATION:
                if (num != checkMeditation) {
                    tvMeditation.setText(getFormattedMessage("attention: %d", signal));
                    checkMeditation = num;
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    addEntry2((double) checkMeditation);
                                }
                            }
                    );

                    check = true;
                } else
                    check = false;

                break;
            case BLINK:
                if (num != checkBlink) {
                    tvBlink.setText(getFormattedMessage("attention: %d", signal));
                    checkBlink = num;
                    check = true;
                } else
                    check = false;

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

//            if(chekcATTENTION>0){

                switch(brainWave.toString()){
                    case "DELTA":
//                        ((TextView)findViewById(R.id.tv_delta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvDelta.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[0] != brainWave.getValue()) {
                            brainWaveList[0] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 400000))
                                entries1.set(0, new RadarEntry(brainWave.getValue() * 100f / 400000));
                            Log.d(LOG_TAG + "1", entries1.get(0).toString());
                        }
                        break;
                    case "THETA":
//                        ((TextView)findViewById(R.id.tv_theta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvTheta.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[1] != brainWave.getValue()) {
                            brainWaveList[1] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 45000))
                                entries1.set(1, new RadarEntry(brainWave.getValue() * 100f / 45000));

                        }

                        break;
                    case "LOW_ALPHA":
//                        ((TextView)findViewById(R.id.tv_lowalpha)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvLowalpha.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[2] != brainWave.getValue()) {
                            brainWaveList[2] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 10000))
                                entries1.set(2, new RadarEntry(brainWave.getValue() * 100f / 10000));

                        }

                        break;
                    case "HIGH_ALPHA":
//                        ((TextView)findViewById(R.id.tv_highalpha)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvHighalpha.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[3] != brainWave.getValue()) {
                            brainWaveList[3] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 15000))
                                entries1.set(3, new RadarEntry(brainWave.getValue() * 100f / 15000));

                        }

                        break;
                    case "LOW_BETA":
//                        ((TextView)findViewById(R.id.tv_lowbeta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvLowbeta.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[4] != brainWave.getValue()) {
                            brainWaveList[4] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 18000))
                                entries1.set(4, new RadarEntry(brainWave.getValue() * 100f / 18000));

                        }
                        break;
                    case "HIGH_BETA":
//                        ((TextView)findViewById(R.id.tv_highbeta)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvHighbeta.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[5] != brainWave.getValue()) {
                            brainWaveList[5] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 24000))
                                entries1.set(5, new RadarEntry(brainWave.getValue() * 100f / 24000));

                        }
                        break;
                    case "LOW_GAMMA":
//                        ((TextView)findViewById(R.id.tv_lowgamma)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvLowgamma.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[6] != brainWave.getValue()) {
                            brainWaveList[6] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 10000))
                                entries1.set(6, new RadarEntry(brainWave.getValue() * 100f / 10000));

                        }
                        break;
                    case "MID_GAMMA":
//                        ((TextView)findViewById(R.id.tv_midgamma)).setText(String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
                        tvMidgamma.setText(brainWave.toString() + ": " + brainWave.getValue());
                        if (brainWaveList[7] != brainWave.getValue()) {
                            brainWaveList[7] = brainWave.getValue();
                            if (checkData(brainWave.getValue() * 100f / 10000))
                                entries1.set(7, new RadarEntry(brainWave.getValue() * 100f / 10000));

                        }
                        break;
                    default:
                        Log.d(LOG_TAG, "unhandled signal");
                        break;
                }
                Log.d("LOG_TAG", entries1.toString());
                Log.d("LOG_TAG", "data3 +" + entries1.size());
                chart1.notifyDataSetChanged();
                chart1.invalidate();
                for (int i = 0 ; i<8 ; i++) {
                    Log.d(LOG_TAG, "data2 " + i + " : " + entries1.get(i));
                }
            }
//            }else{
//        Toast.makeText(getApplicationContext(), "착용을 바르게 해주세요", Toast.LENGTH_SHORT).show();

//        }
    }
    private boolean checkData(Float radarEntry) {
        return radarEntry <= 150;

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
