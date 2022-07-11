package com.example.sentiscape;

import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

public class ChatFragmentInstrumentedTest {

    @Test
    public void testFragmentDisplay() {
        FragmentScenario<ChatFragment> chatFragment = FragmentScenario.launchInContainer(ChatFragment.class);
        assertNotNull(chatFragment);
    }

}
