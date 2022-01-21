package com.example.flashlightapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TextView percentageLabel = ((MainActivity)context).findViewById(R.id.percLabel);
        ImageView batteryPhoto = ((MainActivity)context).findViewById(R.id.betteryImg);

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);//tagadeja batterija
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);//max batterija
            int percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");//Baterijas daudzums teksts
            Resources res = context.getResources();
            if (percentage >= 90) {//Atkarībā no baterijas mainās bilde (kopā 5 bildes) 100% 75% 50% 25% 0%
                batteryPhoto.setImageDrawable(res.getDrawable(R.drawable.prc100));

            } else if (90 > percentage && percentage >= 60) {
                batteryPhoto.setImageDrawable(res.getDrawable(R.drawable.prc75));

            } else if (60 > percentage && percentage >= 40) {
                batteryPhoto.setImageDrawable(res.getDrawable(R.drawable.prc50));

            } else if (40 > percentage && percentage >= 10) {
                batteryPhoto.setImageDrawable(res.getDrawable(R.drawable.prc25));

            } else {
                batteryPhoto.setImageDrawable(res.getDrawable(R.drawable.prc0));

            }

        }
    }
}
