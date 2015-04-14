package uk.co.dannybdutton.flashlightplus.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import uk.co.dannybdutton.flashlightplus.R;
import uk.co.dannybdutton.flashlightplus.fdialogs.DialogInfoFragment;
import uk.co.dannybdutton.flashlightplus.fragments.TorchFragment;
import uk.co.dannybdutton.flashlightplus.utility.BatteryUtil;
import uk.co.dannybdutton.flashlightplus.utility.HardwareUtil;


public class FlashlightActivity extends FragmentActivity implements StrobeControlCoordinator {

    private final String DialogTag = "InfoDialog";

    private final String DialogMessageKey = "Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        if (HardwareUtil.deviceHasNoFlash(this)) {
            adviseFlashUnavailable();
            return;
        }

        if (BatteryUtil.batteryPowerIsLow(this)) {
            advisePhonePowerLow();
        }
    }

    private void adviseFlashUnavailable() {
        DialogInfoFragment infoDialog = new DialogInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DialogMessageKey, getString(R.string.dialog_flash_error_message));
        infoDialog.setArguments(bundle);

        infoDialog.show(getSupportFragmentManager(), DialogTag);
    }

    private void advisePhonePowerLow() {
        DialogInfoFragment infoDialog = new DialogInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DialogMessageKey, getString(R.string.dialog_powerLow_error_message));
        infoDialog.setArguments(bundle);

        infoDialog.show(getSupportFragmentManager(), DialogTag);
    }

    @Override
    public void onSelectedSettingChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        TorchFragment torchFragment = (TorchFragment)
                fragmentManager.findFragmentById(R.id.fragmentTorch);
    }
}
