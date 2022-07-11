package com.example.sentiscape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SentimentActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    EditText sentimentInput;
    TextView sentimentText;
    Button sentimentButton;
    RelativeLayout relativeLayout;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentiment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_more_vert_24);
        toolbar.setOverflowIcon(drawable);

        sentimentInput = findViewById(R.id.sentimentInput);
        sentimentText = findViewById(R.id.sentimentText);
        sentimentButton = findViewById(R.id.sentimentButton);
        relativeLayout = findViewById(R.id.sentimentRelativeLayout);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        sentimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSentiment(sentimentInput.getText().toString());
            }
        });
    }

    public void getSentiment(String input) {

        OkHttpClient client = new OkHttpClient();
        final String[] responseStr = {null};

        String url = "http://b511-2001-bb6-4898-7a58-8daa-89b6-9559-be3a.ngrok.io";
        String data = "'data':'" + input + "'";
        RequestBody body = RequestBody.create(data, MediaType.parse("application/json"));

        Request postRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(postRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("TAG", "ERROR - " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                responseStr[0] = response.body().string();
                Log.i("LOG", "SUCCESS - " + responseStr[0]);
                try {
                    JSONObject responseObj = new JSONObject(responseStr[0]);
                    JSONArray sentences = responseObj.getJSONArray("sentences");
                    JSONObject sentimentObj = sentences.getJSONObject(0);
                    String sentiment = sentimentObj.getString("sentiment");
                    Integer sentimentValue = Integer.valueOf(sentimentObj.getString("sentimentValue"));
                    updateUI(sentiment, sentimentValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateUI(String sentiment, Integer sentimentValue) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                sentimentText.setText(sentiment);
                if (sentimentValue < 2){
                    relativeLayout.setBackgroundResource(R.color.negative);
                } else if (sentimentValue > 2) {
                    relativeLayout.setBackgroundResource(R.color.positive);
                } else {
                    relativeLayout.setBackgroundResource(R.color.neutral);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Online");
    }
}