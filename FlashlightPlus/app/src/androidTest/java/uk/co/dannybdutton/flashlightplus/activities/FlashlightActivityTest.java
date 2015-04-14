package uk.co.dannybdutton.flashlightplus.activities;


import android.test.ActivityInstrumentationTestCase2;

public class FlashlightActivityTest extends ActivityInstrumentationTestCase2 {

    FlashlightActivity sut;

    public FlashlightActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sut = (FlashlightActivity) getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
