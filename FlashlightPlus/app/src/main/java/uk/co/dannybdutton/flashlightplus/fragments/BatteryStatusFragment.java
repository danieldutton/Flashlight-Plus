package uk.co.dannybdutton.flashlightplus.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private float getCurrentBatteryChargePercentage() {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, iFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = (level / (float) scale);

        return batteryPct;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_battery_status, container, false);

        TextView textViewBatteryStatus = (TextView) rootView
                .findViewById(R.id.textViewBatteryStatus);

        textViewBatteryStatus.setText(String.valueOf(getCurrentBatteryChargePercentage()));

        return rootView;
    }
}
