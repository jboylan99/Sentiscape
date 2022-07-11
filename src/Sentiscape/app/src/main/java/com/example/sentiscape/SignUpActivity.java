package com.example.sentiscape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    CardView getUserImage;
    ImageView getUserImageImageView;
    String ImageUriAccessToken;
    String name;
    static int PICK_IMAGE = 123;
    Uri imagePath;

    EditText username, email, password;
    Button signUpButton;
    String userID;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        getUserImage = findViewById(R.id.getUserImage);
//        getUserImageImageView = findViewById(R.id.getUserImageImageView);

        username = findViewById(R.id.usernameSignUp);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        signUpButton = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = username.getText().toString();
                String sEmail = email.getText().toString().trim();
                String sPassword = password.getText().toString().trim();

                if(sEmail.isEmpty() || sPassword.isEmpty())
                    Toast.makeText(SignUpActivity.this, "Username or password not entered.", Toast.LENGTH_SHORT).show();
                else if(sPassword.length() < 6)
                    Toast.makeText(SignUpActivity.this, "Password must be greater than 6 characters.", Toast.LENGTH_SHORT).show();
                else if(name.contains(" ") || sPassword.contains(" ") || sEmail.contains(" "))
                    Toast.makeText(SignUpActivity.this, "Username, email address or password entered incorrectly - cannot contain ' '.", Toast.LENGTH_LONG).show();

                else
                    firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                user.updateProfile(profileUpdates);

                                Toast.makeText(SignUpActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), OnboardingActivity.class);
                                sendDataCloudFirestore();
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Failed to create user.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

    private void sendImageToStorage()
    {
        StorageReference imageRef = storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageUriAccessToken = uri.toString();
                        Toast.makeText(getApplicationContext(), "URI Get Success", Toast.LENGTH_SHORT).show();
                        sendDataCloudFirestore();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "URI Get Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Not Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDataCloudFirestore() {
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("image", ImageUriAccessToken);
        userData.put("uid", userID);
        userData.put("status", "Online");

        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("onSuccess: ", "Data Send Success to Cloud Firestore");
            }
        });
    }
}