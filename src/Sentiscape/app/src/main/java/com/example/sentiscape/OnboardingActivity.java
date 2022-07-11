package com.example.sentiscape;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fragmentManager = getSupportFragmentManager();

        // Create new instance of onboarding fragment.
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());

        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // When swiping right out of last screen, start main activity.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Begin onboarding screens on creation.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment);
        fragmentTransaction.commit();
    }



    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        // Each source represents a different onboarding screen.
        // First argument is the title if the screen.
        // Second argument is the description.
        // Third argument is the background colour.
        // Fourth argument is the icon displayed above the title.
        // Fifth argument is a square shape to display the current page of onboarding.
        PaperOnboardingPage source = new PaperOnboardingPage("Welcome to Sentiscape!", "Swipe to learn about its Summarisation and Affection features.", Color.parseColor("#C93C54"),R.drawable.ic_sentiscape_welcome, R.drawable.senderchatbubble);
        PaperOnboardingPage source1 = new PaperOnboardingPage("Summarisation", "This feature allows you to view summaries of conversations you've had.", Color.parseColor("#f59042"),R.drawable.ic_baseline_speaker_notes, R.drawable.senderchatbubble);
        PaperOnboardingPage source2 = new PaperOnboardingPage("Affection", "This feature allows you to see if conversations were positive, negative or neutral.", Color.parseColor("#C93C54"),R.drawable.ic_affection, R.drawable.senderchatbubble);
        PaperOnboardingPage source3 = new PaperOnboardingPage("Positive Affection", "Positive Affection looks like this.", Color.parseColor("#ffc266"),R.drawable.ic_happy, R.drawable.senderchatbubble);
        PaperOnboardingPage source4 = new PaperOnboardingPage("Negative Affection", "Negative Affection looks like this.", Color.parseColor("#FF018786"),R.drawable.ic_sad, R.drawable.senderchatbubble);
        PaperOnboardingPage source5 = new PaperOnboardingPage("That's all, now get messaging!", "Swipe once more to continue to the chat screen.", Color.parseColor("#C93C54"),R.drawable.ic_exit_onboarding, R.drawable.senderchatbubble);

        // All screens are stored in an array list.
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        elements.add(source);
        elements.add(source1);
        elements.add(source2);
        elements.add(source3);
        elements.add(source4);
        elements.add(source5);
        return elements;
    }
}