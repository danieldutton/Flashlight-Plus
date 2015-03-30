package uk.co.dannybdutton.flashlightplus.fragments;


import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.dannybdutton.flashlightplus.R;

public class TorchFragment extends Fragment {

    private Camera camera;

    public TorchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (deviceHasFlash()) {
            camera = Camera.open();

            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        camera.open();
    }

    @Override
    public void onStop() {
        camera.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        camera.release();
    }

    private boolean deviceHasFlash() {
        return getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
