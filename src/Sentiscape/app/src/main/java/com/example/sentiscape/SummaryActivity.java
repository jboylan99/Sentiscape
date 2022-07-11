package com.example.sentiscape;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//import com.chaquo.python.android.AndroidPlatform;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

// SummaryActivity class displays the conversation summary for the day and includes the primary emotion felt.
public class SummaryActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView name, date, summary, emotion, times;
    Button refresh;
    CardView cardView;

    String chatUID, receiverUsername, senderUID, receiverUID, currentUser;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    RetrieveMessages retrieveMessages = new RetrieveMessages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

//      Toolbar display.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_more_vert_24);
        toolbar.setOverflowIcon(drawable);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        cardView = findViewById(R.id.summaryCardView);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        summary = (TextView) findViewById(R.id.summary);
        emotion = (TextView) findViewById(R.id.emotion);
        times = (TextView) findViewById(R.id.times);

        refresh = (Button) findViewById(R.id.refresh);

        // Getting values from SpecificChatActivity.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name.setText("Conversation with " + extras.getString("senderName"));
            chatUID = extras.getString("chatUID");
            receiverUsername = extras.getString("receiverUsername");
            senderUID = extras.getString("senderUID");
            receiverUID = extras.getString("receiverUID");
            currentUser = extras.getString("currentUser");

            Log.i("chatUID", chatUID);
        }

        try {
            // Get the most recent summary data to display on creation.
            getJsonData();
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }

        // Run retrieveMessages. This will collect conversation data and post it to server.
        retrieveMessages.getMessages(chatUID, receiverUsername, currentUser, senderUID, receiverUID);

        // Refresh button to refresh summary if it's been updated.
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }


    // Function to retrieve summary.
    public void getJsonData() throws JSONException, InterruptedException {

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        // Run in background.
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Send a GET request to URL.
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://ptsv2.com/t/sentiscape_summary" + chatUID + "/d/latest/json");

                HttpResponse response = null;
                try {
                    response = httpClient.execute(httpGet);

                    // If the URL exists, then break down JSON data.
                    if (response.getStatusLine().getStatusCode() == 200) {

                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            String retSrc = EntityUtils.toString(entity);

                            // FormValues contains all the data related to the summary - date, summary,sentiment and times.
                            JSONObject jsonObject = new JSONObject(retSrc);
                            JSONObject tokenList = jsonObject.getJSONObject("FormValues");

                            // Get key, value of date and update date textView with the value.
                            JSONArray dateArray = tokenList.getJSONArray("date");
                            String jsonDate = dateArray.toString();
                            Log.i("JSON DATE", jsonDate );
                            updateDate(jsonDate);

                            // Get key, value of summary and update date textView with the value.
                            JSONArray summaryArray = tokenList.getJSONArray("summary");
                            String jsonSummary = summaryArray.toString();
                            Log.i("JSON SUMMARY", jsonSummary );
                            updateSummary(jsonSummary);

                            // Get key, value of sentiment and update date textView with the value.
                            JSONArray sentimentArray = tokenList.getJSONArray("sentiment");
                            String jsonSentiment = sentimentArray.toString();
                            Log.i("JSON SENTIMENT", jsonSentiment );
                            updateSentiment(jsonSentiment);

                            // Get key, value of times and update date textView with the value.
                            JSONArray timesArray = tokenList.getJSONArray("times");
                            String jsonTimes = timesArray.toString();
                            Log.i("JSON TIMES", jsonTimes );
                            updateTimes(jsonTimes);
                        }
                    } else {
                        Log.i("Server response", "Failed to get server response." );
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    // Function to update date textView.
    public  void updateDate(String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String substr = str.substring(2, str.length() - 2);
                date.setText(substr);
            }
        });
    }

    // Function to update summary textView.
    public  void updateSummary(String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String substr = str.substring(2, str.length() - 2);
                summary.setText(substr);
            }
        });
    }

    // Function to update sentiment textView. Also updates cardView colour depending on the sentiment.
    public  void updateSentiment(String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String substr = str.substring(2, str.length() - 2);

                if (substr.equals("Negative")) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(SummaryActivity.this, R.color.negative));
                } else if (substr.equals("Positive")) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(SummaryActivity.this, R.color.positive));
                }
                emotion.setText(substr);
            }
        });
    }

    // Function to update times textView.
    public  void updateTimes(String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String substr = str.substring(2, str.length() - 2);
                times.setText(substr);
            }
        });
    }

    // Set status to offline on stop.
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Offline");
    }

    // Set status to online on start.
    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Online");
    }
}