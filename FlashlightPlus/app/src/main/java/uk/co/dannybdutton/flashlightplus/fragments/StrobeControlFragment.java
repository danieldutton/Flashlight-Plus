package uk.co.dannybdutton.flashlightplus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import uk.co.dannybdutton.flashlightplus.R;

public class StrobeControlFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener {

    private SeekBar seekBar;

    private boolean strobeIsOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_strobe_control, container, false);

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBarStrobe);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setEnabled(false);

        Button buttonStrobe = (Button) rootView.findViewById(R.id.buttonStrobe);
        buttonStrobe.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        Toast.makeText(getActivity(), String.valueOf(progress), Toast.LENGTH_SHORT)
                .show();
        //do we really want to new this up locally here
        //Activity theActivity = getActivity();
        //StrobeControlCoordinator scc = (StrobeControlCoordinator) theActivity;
        //call the interface method here to start strobe
        //state needs to be maintained if orientation changes
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View v) {
        if (strobeIsOn) {
            strobeIsOn = false;
            seekBar.setEnabled(false);
        } else if (!strobeIsOn) {
            strobeIsOn = true;
            seekBar.setEnabled(true);
        }
    }
}
