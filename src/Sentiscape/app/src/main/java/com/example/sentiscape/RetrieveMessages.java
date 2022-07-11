package com.example.sentiscape;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RetrieveMessages {

    POSTConversation postConversation = new POSTConversation();
    String chatUID, sender, receiver, senderName, receiverName;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("chats");


    // Gets relevant information for both users and gets required database references from Firebase.
    public void getMessages(String senderRoom, String receiverUsername, String currentUser, String senderUID, String receiverUID) {
        chatUID = senderRoom;
        sender = senderUID;
        receiver = receiverUID;
        senderName = currentUser;
        receiverName = receiverUsername;
        DatabaseReference userRef = database.getReference().child("chats").child(chatUID).child("messages");
        DatabaseReference chatUIDRef = database.getReference().child("chats");
        getData(userRef);
    }

    // Retreives data needed to be included in POST request for summarisation (conversation, average sentiment value, timespan).
   public void getData(DatabaseReference userRef) {
       userRef.addValueEventListener(new ValueEventListener() {
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               // Array lists to store messages and times of messages sent.
               ArrayList<String> messages = new ArrayList<>();
               ArrayList<String> times = new ArrayList<>();
               String startTime = "";
               String endTime = "";
               int sentimentValue = 0;
               for(DataSnapshot ds : snapshot.getChildren()) {
                   // Get current date.
                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
                   String date = simpleDateFormat.format(new Date());
                   // Get date of each message sent. Get substring of this to remove time - only date is needed.
                   String currentTime = ds.child("currentTime").getValue(String.class);
                   String currentDate = currentTime.substring(currentTime.length() - 5);
                   // If the date in the message is equal to the current date, it can be included in today's summarisation.
                   if(date.equals(currentDate)) {
                       // Get values so they can be added to array lists.
                       String message = ds.child("message").getValue(String.class);
                       String id = ds.child("senderID").getValue(String.class);
                       // Add sentiment value so average can be calculated later.
                       sentimentValue += ds.child("sentimentValue").getValue(int.class);

                       // Add sender names to each message to make summaries clearer.
                       if(id.equals(sender)) {
                           messages.add(senderName + ": " + message);
                       }
                       else if(id.equals(receiver)) {
                           messages.add(receiverName + ": " + message);
                       }
                       // Add the time of the message sent. Substring removes date and only includes 24hr time.
                       times.add(currentTime.substring(0, 5));
                   }
               }
               // Calculate average value of sentiment analysis.
               float sentimentAverage = (float) sentimentValue / messages.size();
               // Add punctuation to end of each sentence.
               String conversation = String.join(". ", messages);

               // If the times array list isn't empty, get the first and last time to display timespan.
               if (!times.isEmpty()) {
                   startTime = times.get(0);
                   endTime = times.get(times.size() - 1);
               }
               try {
                   // Send POST request to server with all of the values calculated. summarisation.py will then calculate summary.
                   postConversation.postRequest(conversation, Float.toString(sentimentAverage), chatUID, startTime, endTime);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.i("RetrieveMessages", "Failed");

           }
       });
   }
}
