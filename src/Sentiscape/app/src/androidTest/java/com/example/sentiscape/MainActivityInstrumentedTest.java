package com.example.sentiscape;

import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    private ActivityScenario<MainActivity> mainActivity = null;

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(OnboardingActivity.class.getName(), null, false);

    @Before
    public void newActivity() {
        mainActivity = activityScenarioRule.getScenario();
    }

    // Test that MainActivity exists and that all elements are properly instantiated.
    @Test
    public void testMain() {
        assertNotNull(mainActivity);

        mainActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.toolbar)));
        mainActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.include)));
        mainActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.fragmentContainer)));
        mainActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.toolbarText)));
        mainActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.toolbar)));
    }
}
