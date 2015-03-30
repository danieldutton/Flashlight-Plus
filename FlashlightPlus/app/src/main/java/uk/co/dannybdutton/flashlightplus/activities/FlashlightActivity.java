package uk.co.dannybdutton.flashlightplus.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import uk.co.dannybdutton.flashlightplus.R;
import uk.co.dannybdutton.flashlightplus.fragments.TorchFragment;


public class FlashlightActivity extends FragmentActivity implements TorchSettingsCoordinator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSelectedSettingChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        TorchFragment torchFragment = (TorchFragment)
                fragmentManager.findFragmentById(R.id.fragmentTorch);

        //do work here in torchFragment
    }
}
