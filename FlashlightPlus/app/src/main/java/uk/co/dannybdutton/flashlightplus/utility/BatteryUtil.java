package uk.co.dannybdutton.flashlightplus.utility;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryUtil {

    public static boolean batteryPowerIsLow(Context context) {

        if (context == null) throw new NullPointerException("context cannot be null");

        final int PowerSaveThreshold = 20;

        int batteryLevel = getCurrentBatteryChargePercentage(context);

        return (batteryLevel <= PowerSaveThreshold) ? true : false;
    }

    public static int getCurrentBatteryChargePercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

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
