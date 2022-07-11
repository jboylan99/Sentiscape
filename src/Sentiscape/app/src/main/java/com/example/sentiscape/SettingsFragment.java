package com.example.sentiscape;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {

    Button lightMode, darkMode, sentimentAnalysis, deleteAccount;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_settings, null);
        lightMode = (Button) root.findViewById(R.id.lightMode);
        darkMode = (Button) root.findViewById(R.id.darkMode);
        sentimentAnalysis = (Button) root.findViewById(R.id.sentimentAnalysisButton);
        deleteAccount = (Button) root.findViewById(R.id.deleteAccount);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//      Delete Account button deletes user accounts and brings them to splash screen.
        sentimentAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SentimentActivity.class);
                startActivity(intent);
            }
        });

        // Button to switch app theme to light mode.
        lightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Button to switch app theme to dark mode.
        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

//        Delete Account button deletes user account from Firestore and realtime database.
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//                Deleting account from realtime database.
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), LogInActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    Toast.makeText(getActivity(), "Account deleted.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//                Deleting account from Firestore.
                firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).delete();

            }
        });

        return root;
    }
}