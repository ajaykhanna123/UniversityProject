package com.example.ajaykhanna.universityproject.Forum.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajaykhanna.universityproject.Forum.DiscussionChatActivity;
import com.example.ajaykhanna.universityproject.Forum.DiscussionFragment;
import com.example.ajaykhanna.universityproject.Forum.models.ChatModel;
import com.example.ajaykhanna.universityproject.R;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 02-08-2018.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ChatModel> chatModels;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == DiscussionChatActivity.MY_MESSAGE) {
            view = LayoutInflater.from(context).inflate(R.layout.my_message, parent, false);
            return new MyMessageViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.their_message, parent, false);
            return new YourMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyMessageViewHolder) {
            ((MyMessageViewHolder) holder).bindMyMessage(chatModels.get(position));
        }
        if(holder instanceof YourMessageViewHolder) {
            ((YourMessageViewHolder) holder).bindYourMessage(chatModels.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatModels.get(position).getFrom_id() == DiscussionFragment.currentUserRollNo? DiscussionChatActivity.MY_MESSAGE :
                DiscussionChatActivity.YOUR_MESSAGE;
    }

    @Override
    public int getItemCount() {
        if(chatModels != null)
            return chatModels.size();
        else
            return 0;
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder {

        TextView myMessageBody;

        public MyMessageViewHolder(View itemView) {
            super(itemView);
            myMessageBody = itemView.findViewById(R.id.message_body);
        }

        void bindMyMessage(ChatModel chatModel) {
            myMessageBody.setText(chatModel.getMessage());
        }
    }

    class YourMessageViewHolder extends RecyclerView.ViewHolder {

        TextView senderName, yourMessageBody;
//        ImageView senderImage;

        public YourMessageViewHolder(View itemView) {
            super(itemView);
//            senderImage = itemView.findViewById(R.id.senderImage);
            senderName = itemView.findViewById(R.id.senderName);
            yourMessageBody = itemView.findViewById(R.id.message_body);
        }

        void bindYourMessage(ChatModel chatModel) {
            yourMessageBody.setText(chatModel.getMessage());
            senderName.setText(Long.toString(chatModel.getFrom_id()));
        }
    }
}
