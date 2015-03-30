package uk.co.dannybdutton.flashlightplus.fragments;


import android.content.pm.PackageManager;
import android.hardware.Camera;
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

    private boolean toggledOn = false;

    public TorchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DeviceHasNoFlash())
            adviseNoFlashDeviceAvailable();
    }

    private void adviseNoFlashDeviceAvailable() {
        Toast.makeText(getActivity(), "No Flash is available on your device", Toast.LENGTH_LONG)
                .show();
    }

    private void adviseErrorInitialisingFlash() {
        Toast.makeText(getActivity(), "Error Initialising Camera", Toast.LENGTH_LONG)
                .show();
    }

    private void displayFlash() {

        camera = Camera.open();

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

    private boolean DeviceHasNoFlash() {
        return !getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onClick(View v) {
        myButtonClickHandler(v);
    }

    public void myButtonClickHandler(View view) {

        //Activity theActivity = getActivity();
        //TorchSettingsCoordinator tsc = (TorchSettingsCoordinator) theActivity;
        //tsc.onSelectedSettingChanged();
        if (toggledOn) {
            toggledOn = false;
            camera.release();
        } else {
            toggledOn = true;
            displayFlash();
        }

        Toast.makeText(getActivity(), "Button Working", Toast.LENGTH_LONG)
                .show();
    }
}
