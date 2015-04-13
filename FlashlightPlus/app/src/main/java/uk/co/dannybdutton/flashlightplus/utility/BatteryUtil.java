package uk.co.dannybdutton.flashlightplus.utility;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryUtil {

    public static boolean batteryPowerIsLow(Activity activity) {

        if (activity == null) throw new NullPointerException("param activity cannot be null");

        final int PowerSaveThreshold = 20;

        int batteryLevel = getCurrentBatteryChargePercentage(activity);

        return (batteryLevel <= PowerSaveThreshold) ? true : false;
    }

    public static int getCurrentBatteryChargePercentage(Activity activity) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = activity.registerReceiver(null, iFilter);

        final int DefaultValue = 0;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, DefaultValue);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, DefaultValue);

        int batteryPct = calculateBatteryPercentage(level, scale);

        return batteryPct;
    }

    private static int calculateBatteryPercentage(int level, int scale) {
        return Math.round((level / (float) scale) * 100);
    }
}
