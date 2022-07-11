package com.example.sentiscape;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SplashScreenActivityInstrumentationTest {

    @Rule
    public ActivityScenarioRule<SplashScreenActivity> activityScenarioRule = new ActivityScenarioRule<SplashScreenActivity>(SplashScreenActivity.class);

    private ActivityScenario<SplashScreenActivity> splashScreenActivity = null;

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(LogInActivity.class.getName(), null, false);

    @Before
    public void newActivity() {
        splashScreenActivity = activityScenarioRule.getScenario();
    }

    // Test that SplashScreenActivity exists and that it goes to LogInActivity automatically.
    @Test
    public void testSplashScreen() {
        assertNotNull(splashScreenActivity);

        Activity logInActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(logInActivity);
    }

    @After
    public void tearDown() {
        splashScreenActivity = null;
    }
}

