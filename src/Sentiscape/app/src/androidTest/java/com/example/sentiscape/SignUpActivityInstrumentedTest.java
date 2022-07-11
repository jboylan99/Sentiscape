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

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule = new ActivityScenarioRule<SignUpActivity>(SignUpActivity.class);

    private ActivityScenario<SignUpActivity> signUpActivity = null;

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(OnboardingActivity.class.getName(), null, false);

    @Before
    public void newActivity() {
        signUpActivity = activityScenarioRule.getScenario();
    }

    // Test that SignUpActivity exists and that all elements are properly instantiated.
    @Test
    public void testSignUp() {
        assertNotNull(signUpActivity);

        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.usernameSignUp)));
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.emailSignUp)));
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.passwordSignUp)));
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.signUp)));
    }

    // Test that Sign Up button goes to OnboardingActivity when credentials are entered.
    @Test
    public void testSignUpButton() {
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.signUp)));

        onView(withId(R.id.usernameSignUp)).perform(clearText(), typeText("tester6"), ViewActions.closeSoftKeyboard());
        pauseTestFor(500);
        onView(withId(R.id.emailSignUp)).perform(clearText(), typeText("tester6@gmail.com"), ViewActions.closeSoftKeyboard());
        pauseTestFor(500);
        onView(withId(R.id.passwordSignUp)).perform(clearText(), typeText("tester6"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signUp)).perform(click());

        Activity onboardingActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(onboardingActivity);
    }
    // Test that Sign Up button does not go to OnboardingActivity when credentials are not entered.
    @Test
    public void testSignUpButtonNotEntered() {
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.signUp)));

        onView(withId(R.id.signUp)).perform(click());

        Activity onboardingActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(onboardingActivity);

    }

    // Test that Sign Up button does not go to OnboardingActivity when credentials are entered incorrectly.
    @Test
    public void testSignUpButtonWongDetails() {
        signUpActivity.onActivity(activity -> assertNotNull(activity.findViewById(R.id.signUp)));

        onView(withId(R.id.usernameSignUp)).perform(clearText(), typeText("tester 2"), ViewActions.closeSoftKeyboard());
        pauseTestFor(500);
        onView(withId(R.id.emailSignUp)).perform(clearText(), typeText("tester2@gmail.com"), ViewActions.closeSoftKeyboard());
        pauseTestFor(500);
        onView(withId(R.id.passwordSignUp)).perform(clearText(), typeText("tester 2123"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signUp)).perform(click());

        Activity onboardingActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(onboardingActivity);

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
        signUpActivity = null;
    }
}
