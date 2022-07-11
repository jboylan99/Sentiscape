package com.example.sentiscape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Messages> messagesArrayList;

    int ITEM_SEND = 1;
    int ITEM_SEND_POS = 2;
    int ITEM_SEND_NEG = 3;
    int ITEM_RECEIVE = 4;
    int ITEM_RECEIVE_POS = 5;
    int ITEM_RECEIVE_NEG = 6;

    public MessageAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_layout, parent, false);
            return new SenderViewHolder(view);
        } else if (viewType == ITEM_SEND_POS) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_positive_layout, parent, false);
            return new SenderPositiveViewHolder(view);
        } else if (viewType == ITEM_SEND_NEG) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_negative_layout, parent, false);
            return new SenderNegativeViewHolder(view);
        } else if (viewType == ITEM_RECEIVE_POS) {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_positive_layout, parent, false);
            return new ReceiverPositiveViewHolder(view);
        } else if (viewType == ITEM_RECEIVE_NEG) {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_negative_layout, parent, false);
            return new ReceiverNegativeViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_layout, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesArrayList.get(position);
        if(holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        } else if (holder.getClass() == SenderPositiveViewHolder.class) {
            SenderPositiveViewHolder viewHolder = (SenderPositiveViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        } else if (holder.getClass() == SenderNegativeViewHolder.class) {
            SenderNegativeViewHolder viewHolder = (SenderNegativeViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        } else if (holder.getClass() == ReceiverPositiveViewHolder.class) {
            ReceiverPositiveViewHolder viewHolder = (ReceiverPositiveViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        } else if (holder.getClass() == ReceiverNegativeViewHolder.class) {
            ReceiverNegativeViewHolder viewHolder = (ReceiverNegativeViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.textViewMessage.setText(messages.getMessage());
            viewHolder.timeMessage.setText(messages.getCurrentTime());
        }

    }

    @Override
    public int getItemViewType(int position) {
        Messages messages = messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderID())) {
            if(messages.getSentimentValue() > 2) {
                return ITEM_SEND_POS;
            } else if (messages.getSentimentValue() < 2) {
                return ITEM_SEND_NEG;
            } else {
                return ITEM_SEND;
            }
        } else {
            if(messages.getSentimentValue() > 2) {
                return ITEM_RECEIVE_POS;
            } else if (messages.getSentimentValue() < 2) {
                return ITEM_RECEIVE_NEG;
            } else {
                return ITEM_RECEIVE;
            }
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }

    class SenderPositiveViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public SenderPositiveViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }

    class SenderNegativeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public SenderNegativeViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }

    class ReceiverPositiveViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public ReceiverPositiveViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }

    class ReceiverNegativeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView timeMessage;

        public ReceiverNegativeViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.senderMessage);
            timeMessage = itemView.findViewById(R.id.messageTime);
        }
    }
}
