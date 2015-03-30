package uk.co.dannybdutton.flashlightplus.fragments;


import android.app.AlertDialog;
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

    private Camera.Parameters parameters;

    private boolean flashIsOn = false;

    private MediaPlayer mediaPlayer;

    public TorchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DeviceHasNoFlash())
            adviseNoFlashDeviceAvailable();

        getCamera();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_torch, container, false);

        Button button = (Button) rootView.findViewById(R.id.buttonFlashOn);
        button.setOnClickListener(this);

        return rootView;
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

        alert.setTitle("Error");
        alert.setMessage("Error initialising camera!");
        alert.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
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
    }

    @Override
    public void onResume() {
        super.onResume();

        if (flashIsOn)
            turnOnFlash();

        Toast.makeText(getActivity(), "On Resume", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }
        Toast.makeText(getActivity(), "On Stop", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getActivity(), "On Destroy", Toast.LENGTH_SHORT)
                .show();
    }

    private void turnOnFlash() {
        if (!flashIsOn) {
            if (camera == null || parameters == null) {
                adviseErrorInitialisingFlash();
                return;
            }
            playClickSound();

            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            flashIsOn = true;
        }

    }

    private void turnOffFlash() {
        if (flashIsOn) {
            if (camera == null || parameters == null) {
                adviseErrorInitialisingFlash();
                return;
            }
            playClickSound();

            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            flashIsOn = false;
        }
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                parameters = camera.getParameters();
            } catch (RuntimeException e) {
                adviseErrorInitialisingFlash();
            }
        }
    }
}
