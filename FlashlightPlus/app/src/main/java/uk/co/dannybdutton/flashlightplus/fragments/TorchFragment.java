package uk.co.dannybdutton.flashlightplus.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import uk.co.dannybdutton.flashlightplus.R;

public class TorchFragment extends Fragment implements View.OnClickListener {

    //region Variables
    private Camera camera;

    private Parameters cameraParameters;

    private boolean flashIsOn = false;

    private MediaPlayer mediaPlayer;
    //endregion

    //region Constructor(s)
    public TorchFragment() {
    }
    //endregion

    //region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDeviceCamera();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_torch, container, false);

        ImageButton button = (ImageButton) rootView.findViewById(R.id.buttonFlashOn);
        button.setOnClickListener(this);

        return rootView;
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
    }

    @Override
    public void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    //endregion

    @Override
    public void onClick(View v) {
        if (flashIsOn) {
            turnOffFlash();
        } else {
            turnOnFlash();
        }
    }

    private void turnOnFlash() {
        //encapsulate this duplicate code in a new method
        if (camera == null || cameraParameters == null) {
            adviseErrorInitialisingFlash();
            return;
        }
        playButtonClickAudio();

        cameraParameters = camera.getParameters();

        if (flashHasTorchMode()) {
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(cameraParameters);
            camera.startPreview();
            flashIsOn = true;
        }
    }

    private boolean flashHasTorchMode() {
        List<String> flashModes = cameraParameters.getSupportedFlashModes();

        return flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH);
    }


    //region Methods
    private void playButtonClickAudio() {
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.button_click);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }



    private void turnOffFlash() {
        if (flashIsOn) {
            if (camera == null || cameraParameters == null) {
                adviseErrorInitialisingFlash();
                return;
            }
            playButtonClickAudio();

            cameraParameters = camera.getParameters();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(cameraParameters);
            camera.stopPreview();
            flashIsOn = false;
        }
    }

    private void initDeviceCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                cameraParameters = camera.getParameters();
            } catch (RuntimeException e) {
                adviseErrorInitialisingFlash();
            }
        }
    }

    //use dialog fragment here
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
    //endregion

}
