package com.example.reload_basic_topics;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * This is a modified instrumented test that will run on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestExample {
    @Test
    public void verifyAppContext() {
        // Get the application context for testing.
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertNotNull("Context should not be null", context);
        assertEquals("com.example.reload_basic_topics", context.getPackageName());
    }
}
