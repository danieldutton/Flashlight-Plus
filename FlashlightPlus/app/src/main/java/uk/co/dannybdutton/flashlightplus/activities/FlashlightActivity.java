package uk.co.dannybdutton.flashlightplus.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import uk.co.dannybdutton.flashlightplus.R;
import uk.co.dannybdutton.flashlightplus.fragments.TorchFragment;
import uk.co.dannybdutton.flashlightplus.utility.BatteryUtil;


public class FlashlightActivity extends FragmentActivity implements StrobeControlCoordinator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        if (deviceHasNoFlash()) {
            adviseFlashUnavailable();
            return;
        }

        if (BatteryUtil.batteryPowerIsLow(this)) {
            promptPossibleInsufficientPower();
        }
    }

    private boolean deviceHasNoFlash() {
        return !getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void adviseFlashUnavailable() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();

        alert.setTitle(R.string.dialog_flash_error_title);
        alert.setMessage(getString(R.string.dialog_flash_error_text));
        alert.setButton(getString(R.string.dialog_flash_error_button_text), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //finish it here
            }
        });
        alert.show();
    }

    private void promptPossibleInsufficientPower() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();

        alert.setTitle("Possible Power Saver Mode");
        alert.setMessage("Battery level low.  If you device power saves the torch may not work");
        alert.setButton(getString(R.string.dialog_flash_error_button_text), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alert.show();
    }

    @Override
    public void onSelectedSettingChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        TorchFragment torchFragment = (TorchFragment)
                fragmentManager.findFragmentById(R.id.fragmentTorch);

        //do work here in torchFragment
    }
}
