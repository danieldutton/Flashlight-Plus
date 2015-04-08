package uk.co.dannybdutton.flashlightplus.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import uk.co.dannybdutton.flashlightplus.R;

public class TorchFragment extends Fragment implements View.OnClickListener {

    private Camera camera;

    private Parameters cameraParameters;

    private boolean flashIsOn = false;

    private MediaPlayer mediaPlayer;

    public TorchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (deviceHasNoFlash()){
            adviseFlashUnavailable();
        }
        else{
            getCamera();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_torch, container, false);

        ImageButton button = (ImageButton) rootView.findViewById(R.id.buttonFlashOn);
        button.setOnClickListener(this);

        return rootView;
    }

    private boolean deviceHasNoFlash() {
        return !getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void adviseFlashUnavailable() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .create();

        alert.setTitle(R.string.dialog_flash_error_title);
        alert.setMessage(getString(R.string.dialog_flash_error_text));
        alert.setButton(getString(R.string.dialog_flash_error_button_text), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
    }

    @Override
    public void onClick(View v) {
        //Activity theActivity = getActivity();
        //TorchSettingsCoordinator tsc = (TorchSettingsCoordinator) theActivity;
        //tsc.onSelectedSettingChanged();
        if (flashIsOn) {
            turnOffFlash();
        } else {
            turnOnFlash();
        }
    }

    private void playClickSound() {
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.button_click);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void adviseErrorInitialisingFlash() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .create();

        alert.setTitle(R.string.dialog_camera_error_title);
        alert.setMessage(getString(R.string.dialog_camera_error_text));
        alert.setButton(getString(R.string.dialog_camera_error_button_text), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
    }

    private void turnOnFlash() {
        if (!flashIsOn) {
            if (camera == null || cameraParameters == null) {
                adviseErrorInitialisingFlash();
                return;
            }
            playClickSound();

            cameraParameters = camera.getParameters();

            if (flashHasTorchMode()) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(cameraParameters);
                camera.startPreview();
                flashIsOn = true;
            }
        }
    }

    private boolean flashHasTorchMode() {
        List<String> flashModes = cameraParameters.getSupportedFlashModes();

        return flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH);
    }

    private void turnOffFlash() {
        if (flashIsOn) {
            if (camera == null || cameraParameters == null) {
                adviseErrorInitialisingFlash();
                return;
            }
            playClickSound();

            cameraParameters = camera.getParameters();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(cameraParameters);
            camera.stopPreview();
            flashIsOn = false;
        }
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                cameraParameters = camera.getParameters();
            } catch (RuntimeException e) {
                adviseErrorInitialisingFlash();
            }
        }
    }

    private boolean powerSaverCouldBeActive() {

        final int PowerSaveThreshold = 20;

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, iFilter);

        final int DefaultValue = 0;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, DefaultValue);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, DefaultValue);

        int batteryPct = Math.round((level / (float) scale) * 100);

        if (batteryPct <= PowerSaveThreshold)
            return true;
        else return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        getCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        turnOffFlash();
        //camera should maybe released in here instead of onStop
    }

    @Override
    public void onResume() {
        super.onResume();

        if (flashIsOn)
            turnOnFlash();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
