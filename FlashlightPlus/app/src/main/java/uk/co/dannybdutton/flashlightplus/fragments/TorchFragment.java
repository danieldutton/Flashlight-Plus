package uk.co.dannybdutton.flashlightplus.fragments;


import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import uk.co.dannybdutton.flashlightplus.R;

public class TorchFragment extends Fragment implements View.OnClickListener {

    private Camera camera;

    private boolean flashIsOn = false;

    private MediaPlayer mediaPlayer;

    public TorchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DeviceHasNoFlash())
            adviseNoFlashDeviceAvailable();
    }

    private boolean DeviceHasNoFlash() {
        return !getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void adviseNoFlashDeviceAvailable() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .create();

        alert.setTitle("Error");
        alert.setMessage("Sorry, your device doesn't support flash light!");
        alert.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
    }

    private void adviseErrorInitialisingFlash() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .create();

        alert.setTitle("Error");
        alert.setMessage("Error initialising camera!");
        alert.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
    }

    private void displayFlash() {

        if (camera == null)
            adviseErrorInitialisingFlash();

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_torch, container, false);

        Button button = (Button) rootView.findViewById(R.id.buttonFlashOn);
        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        camera.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
        Toast.makeText(getActivity(), "On Resume", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        camera.release();
        Toast.makeText(getActivity(), "On Stop", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.release();
        Toast.makeText(getActivity(), "On Destroy", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onClick(View v) {
        //Activity theActivity = getActivity();
        //TorchSettingsCoordinator tsc = (TorchSettingsCoordinator) theActivity;
        //tsc.onSelectedSettingChanged();
        if (flashIsOn) {
            flashIsOn = false;
            camera.release();
            playClickSound();
        } else {
            flashIsOn = true;
            playClickSound();
            camera = camera.open();
            displayFlash();
        }

        Toast.makeText(getActivity(), "Button Working", Toast.LENGTH_LONG)
                .show();
    }

    private void playClickSound(){
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.button_click);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
        mediaPlayer.release();
    }
}
