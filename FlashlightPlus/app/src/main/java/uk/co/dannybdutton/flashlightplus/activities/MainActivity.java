package uk.co.dannybdutton.flashlightplus.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import uk.co.dannybdutton.flashlightplus.R;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
