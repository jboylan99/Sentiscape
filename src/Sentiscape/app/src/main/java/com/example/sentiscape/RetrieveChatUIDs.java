package com.example.sentiscape;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetrieveChatUIDs {

    POSTConversation postConversation = new POSTConversation();
    String chatUID, sender, receiver, senderName, receiverName;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("chats");

    public void getChatUIDs() {
        DatabaseReference userRef = database.getReference().child("chats");
        Log.i("UIDs", userRef.toString());
    }
}
