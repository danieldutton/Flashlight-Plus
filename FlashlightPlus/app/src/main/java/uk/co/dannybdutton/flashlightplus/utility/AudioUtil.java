package uk.co.dannybdutton.flashlightplus.utility;


import android.content.Context;
import android.media.MediaPlayer;

import uk.co.dannybdutton.flashlightplus.R;

public class AudioUtil {

    private static MediaPlayer mediaPlayer;

    public static void playAudio(Context context, int resId) {

        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}
