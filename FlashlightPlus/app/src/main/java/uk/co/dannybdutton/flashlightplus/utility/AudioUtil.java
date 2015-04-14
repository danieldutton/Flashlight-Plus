package uk.co.dannybdutton.flashlightplus.utility;


import android.content.Context;
import android.media.MediaPlayer;

public class AudioUtil {

    private static MediaPlayer mediaPlayer;

    public static void playAudio(Context context, int resId) {

        if (context == null) throw new NullPointerException("context cannot be null");

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
