package com.example.sentiscape;

import android.util.Log;

import androidx.annotation.NonNull;

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

public class POSTConversation {

    // Send POST request to server containing conversation data.
    public void postRequest(String conversation, String sentimentValue, String chatUID, String startTime, String endTime) throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        String url = "http://ptsv2.com/t/sentiscape_conversation/post";
        Log.i("URL: ", url);
        OkHttpClient client = new OkHttpClient();

        // Create new JSON object with the following key, value pairs.
        JSONObject postData = new JSONObject();
        try {
            postData.put("conversation", conversation);
            postData.put("sentimentValue", sentimentValue);
            postData.put("chatUID", chatUID);
            postData.put("startTime", startTime);
            postData.put("endTime", endTime);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, postData.toString());

        // Build the post request.
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String message = response.body().string();
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                String message = e.getMessage().toString();
            }
        });
    }
}
