package uk.co.dannybdutton.flashlightplus.utility;


import android.content.Context;
import android.content.pm.PackageManager;

public class HardwareUtil {

    public static boolean deviceHasNoFlash(Context context) {
        return !context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
