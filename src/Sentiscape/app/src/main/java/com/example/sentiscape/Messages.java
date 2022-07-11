package com.example.sentiscape;

public class Messages {

    String message;
    String senderID;
    long timeStamp;
    String currentTime;
    Integer sentimentValue;

    public Messages() {
    }

    public Messages(String message, String senderID, long timeStamp, String currentTime, Integer sentimentValue) {
        this.message = message;
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.currentTime = currentTime;
        this.sentimentValue = sentimentValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getSentimentValue() { return sentimentValue; }

    public void setSentimentValue(Integer sentimentValue) { this.sentimentValue = sentimentValue; }
    
}
