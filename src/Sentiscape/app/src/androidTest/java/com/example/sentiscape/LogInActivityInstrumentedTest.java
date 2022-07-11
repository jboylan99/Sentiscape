package com.example.sentiscape;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogInActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LogInActivity> activityScenarioRule = new ActivityScenarioRule<LogInActivity>(LogInActivity.class);

    private ActivityScenario<LogInActivity> logInActivity = null;

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitor2 = InstrumentationRegistry.getInstrumentation().addMonitor(SignUpActivity.class.getName(), null, false);

    @Before
    public void newActivity() {
        logInActivity = activityScenarioRule.getScenario();
    }

    // Test that LogInActivity exists and that all elements are properly instantiated.
    @Test
    public void testLogIn() {
        assertNotNull(logInActivity);

        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.emailLogIn)));
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.passwordLogIn)));
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.logIn)));
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.signUp)));
    }

    // Test that Log In button goes to MainActivity when credentials are entered.
    @Test
    public void testLogInButton() {
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.logIn)));

        onView(withId(R.id.emailLogIn)).perform(clearText(), typeText("jason@gmail.com"), ViewActions.closeSoftKeyboard());
        pauseTestFor(500);
        onView(withId(R.id.passwordLogIn)).perform(clearText(), typeText("jason123"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.logIn)).perform(click());

        Activity mainActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(mainActivity);
        newActivity();
    }

    // Test that Log In button goes to MainActivity when credentials are not entered.
    @Test
    public void testLogInButtonNotEntered() {
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.logIn)));

        onView(withId(R.id.logIn)).perform(click());

        Activity mainActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(mainActivity);

    }

    // Test that Sign Up button goes to SignUpActivity.
    @Test
    public void testLogInToSignUp() {
        logInActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.logIn)));

        onView(withId(R.id.signUp)).perform(click());

        Activity signUpActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor2, 5000);
        assertNotNull(signUpActivity);

    }

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        logInActivity = null;
    }

}
