package com.example.sentiscape;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.grpc.internal.JsonParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SpecificChatActivity extends AppCompatActivity {

    EditText getMessage;
    ImageButton sendMessageButton;

    CardView sendMessageCardView;
    Toolbar toolbarSpecificChat;
    ImageView imageViewSpecificUser;
    TextView nameSpecificUser;

    private String enteredMessage;
    Intent intent;
    String receiverUsername, receiverUID, senderUID;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    String senderRoom, receiverRoom;
    Integer sentimentValue;

    ImageButton backButtonSpecificChat;

    RecyclerView messageRecyclerView;
    RelativeLayout relativeLayout;

    String currentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessageAdapter messageAdapter;
    ArrayList<Messages> messagesArrayList;

    List<String> conversation =  new ArrayList<String>();
    String toStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);

        getMessage = findViewById(R.id.getMessage);
        sendMessageCardView = findViewById(R.id.cardViewSendMessage);
        sendMessageButton = findViewById(R.id.cardViewSendButton);
        toolbarSpecificChat = findViewById(R.id.toolbarSpecificChat);
        nameSpecificUser = findViewById(R.id.nameOfUser);
        backButtonSpecificChat = findViewById(R.id.backButtonSpecificChat);

        messagesArrayList = new ArrayList<>();
        messageRecyclerView = findViewById(R.id.recyclerViewSpecificChat);
        relativeLayout = findViewById(R.id.specificChatRelativeLayout);

        intent = getIntent();

        setSupportActionBar(toolbarSpecificChat);
        toolbarSpecificChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Toolbar is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm'\n'dd/MM");

        senderUID = firebaseAuth.getUid();
        receiverUID = getIntent().getStringExtra("receiverUid");
        receiverUsername = getIntent().getStringExtra("name");

        senderRoom = senderUID + receiverUID;
        receiverRoom = receiverUID + senderUID;

        backButtonSpecificChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firebaseFirestore.collection("Users").document(senderUID);
                documentReference.update("status", "Offline");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages");
        messageAdapter = new MessageAdapter(SpecificChatActivity.this, messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                if (messageAdapter.getItemCount() > 0) {

                    Integer messageNumber = messageAdapter.getItemCount();
                    Integer totalSentiment = 5;
                    while(messageNumber > 0) {
                        messageNumber = messageNumber - 1;
                        if(messagesArrayList.get(messageNumber).getSentimentValue() < 2) {
                            totalSentiment = totalSentiment - 1;
                        } else if (messagesArrayList.get(messageNumber).getSentimentValue() > 2) {
                            totalSentiment = totalSentiment + 1;
                        }
                    }
                    updateUI(totalSentiment);

                    messageRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nameSpecificUser.setText(receiverUsername);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                enteredMessage = getMessage.getText().toString();
                if(enteredMessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message", Toast.LENGTH_SHORT).show();
                } else {
                    sendSentimentMessage(enteredMessage);
                }
                getMessage.setText(null);
            }
        });
    }

    // Displays menu button for Summary Activity that is displayed in toolbar.
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.summary, menu);

        MenuItem menuItem = menu.findItem(R.id.summary);

        return super.onCreateOptionsMenu(menu);
    }

    // When moving to Summary Activity, put extra arguments in bundle and send when activity is started.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                intent = new Intent(getApplicationContext(), SummaryActivity.class);
                intent.putExtra("senderName", getIntent().getStringExtra("name"));
                intent.putExtra("chatUID", senderRoom);
                intent.putExtra("receiverUsername", receiverUsername);
                intent.putExtra("senderUID", senderUID);
                intent.putExtra("receiverUID", receiverUID);
                intent.putExtra("currentUser", firebaseAuth.getCurrentUser().getDisplayName());
                startActivity(intent);
                return true;
        }
    }

    public void sendSentimentMessage(String input) {
        OkHttpClient client = new OkHttpClient();
        final String[] responseStr = {null};

        String url = "http://8f44-2001-bb6-4898-7a58-ca1a-5744-e2e7-da0d.ngrok.io";
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                responseStr[0] = response.body().string();
                Log.i("LOG", "SUCCESS - " + responseStr[0]);
                try {
                    JSONObject responseObj = new JSONObject(responseStr[0]);
                    JSONArray sentences = responseObj.getJSONArray("sentences");
                    JSONObject sentimentObj = sentences.getJSONObject(0);
                    Integer sentimentValue = Integer.valueOf(sentimentObj.getString("sentimentValue"));
                    setSentimentValue(sentimentValue);

                    conversation.add(enteredMessage);
                    toStr = String.join("\n", conversation);

                    Date date = new Date();
                    currentTime = simpleDateFormat.format(calendar.getTime());
                    Messages messages = new Messages(enteredMessage, firebaseAuth.getUid(), date.getTime(), currentTime, sentimentValue);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages").push()
                            .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseDatabase.getReference().child("chats").child(receiverRoom).child("messages").push()
                                    .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setSentimentValue(Integer sentimentInt) {
        sentimentValue = sentimentInt;
    }

    public void updateUI(Integer sentimentValue) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (sentimentValue < 0){
                    relativeLayout.setBackgroundResource(R.color.highNegative);
                } else if (sentimentValue < 2) {
                    relativeLayout.setBackgroundResource(R.color.midNegative);
                } else if (sentimentValue < 5) {
                    relativeLayout.setBackgroundResource(R.color.lowNegative);
                } else if (sentimentValue > 10) {
                    relativeLayout.setBackgroundResource(R.color.highPositive);
                } else if (sentimentValue > 8) {
                    relativeLayout.setBackgroundResource(R.color.midPositive);
                } else if (sentimentValue > 5) {
                    relativeLayout.setBackgroundResource(R.color.lowPositive);
                } else {
                    relativeLayout.setBackgroundResource(R.color.neutral);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status", "Online");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(SpecificChatActivity.this, messagesArrayList);
        messageRecyclerView.setAdapter(messageAdapter);

        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (messageAdapter != null) {
            messageAdapter.notifyDataSetChanged();
        }
    }
}