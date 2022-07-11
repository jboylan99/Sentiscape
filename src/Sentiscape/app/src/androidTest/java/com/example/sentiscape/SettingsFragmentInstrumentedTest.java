//package com.example.testapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.SwitchCompat;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import static org.junit.Assert.*;
//
//@RunWith(AndroidJUnit4.class)
//public class SettingsFragmentInstrumentedTest {
//    @Test
//    public void darkMode() {
//        SettingsFragment settingsFragment = new SettingsFragment();
//        final SwitchCompat theme = (SwitchCompat) settingsFragment.getView().findViewById(R.id.theme);
//        settingsFragment.getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                boolean b = theme.performClick();
//                System.out.println(b);
//                try {
//                    assertEquals(true, b);
//                } catch (NullPointerException e) {
//                    System.out.println(b);
//                }
//
//            }
//        });

//
//    }
//}
