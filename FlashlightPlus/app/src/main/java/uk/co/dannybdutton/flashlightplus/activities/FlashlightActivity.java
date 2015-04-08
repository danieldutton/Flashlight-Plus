package uk.co.dannybdutton.flashlightplus.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import uk.co.dannybdutton.flashlightplus.R;
import uk.co.dannybdutton.flashlightplus.fragments.TorchFragment;


public class FlashlightActivity extends FragmentActivity implements TorchSettingsCoordinator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        Toast.makeText(this, "Activity - onCreate", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Activity - onStart", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity - onResume", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Activity - onPause", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Activity - onStop", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Activity - onRestart", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Activity - onDestroy", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onSelectedSettingChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        TorchFragment torchFragment = (TorchFragment)
                fragmentManager.findFragmentById(R.id.fragmentTorch);

        //do work here in torchFragment
    }
}
