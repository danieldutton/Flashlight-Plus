package uk.co.dannybdutton.flashlightplus.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.dannybdutton.flashlightplus.R;

public class BatteryStatusFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_battery_status, container, false);

        TextView textViewBatteryStatus = (TextView) rootView
                .findViewById(R.id.textViewBatteryStatus);

        textViewBatteryStatus.setText(String.valueOf(getCurrentBatteryChargePercentage() + "%"));

        return rootView;
    }

    private int getCurrentBatteryChargePercentage() {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, iFilter);

        final int DefaultValue = 0;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, DefaultValue);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, DefaultValue);

        int batteryPct = calculateBatteryPercentage(level, scale);

        return batteryPct;
    }

    private int calculateBatteryPercentage(int level, int scale) {
        return Math.round((level / (float) scale) * 100);
    }

    private void displayBatteryLevelImage(float batteryLevel) {
        if (batteryLevel > 90)
            Log.i("Tag", "Battery level greater than 90");
        else if (batteryLevel > 50 && batteryLevel < 90)
            Log.i("Tag", "Battery level greater than 90");
        else if (batteryLevel > 30 && batteryLevel < 50)
            Log.i("Tag", "Battery level greater than 90");
        else
            Log.i("Tag", "Battery level less than 30");
    }


}
