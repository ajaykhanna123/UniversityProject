package com.example.ajaykhanna.universityproject.Forum.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajaykhanna.universityproject.Forum.DiscussionChatActivity;
import com.example.ajaykhanna.universityproject.Forum.DiscussionFragment;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.Forum.models.ChatModel;
import com.example.ajaykhanna.universityproject.R;
import com.example.ajaykhanna.universityproject.Utils.Constants;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 25-07-2018.
 */
public class YourGrpListAdapter extends RecyclerView.Adapter<YourGrpListAdapter.ViewHolder> {

    private ArrayList<ChatGroupModel> chatGroupModels;
    private Context context;

    public YourGrpListAdapter( Context context, ArrayList<ChatGroupModel> chatGroupModels) {
        this.chatGroupModels = chatGroupModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.your_group_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindChatGroup(chatGroupModels.get(position));
    }

    @Override
    public int getItemCount() {
        return chatGroupModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

         TextView yourGrpNameTxt;
         TextView yourGrpMemberTxt;
         ImageView yourGrpUnreadIndicatorImg;

         ViewHolder(View itemView) {
             super(itemView);
             yourGrpMemberTxt = itemView.findViewById(R.id.yourGrpAdminTxt);
             yourGrpNameTxt = itemView.findViewById(R.id.yourGrpNameTxt);
             yourGrpUnreadIndicatorImg = itemView.findViewById(R.id.yourGrpUnreadIndicatorImg);
         }

         void bindChatGroup(final ChatGroupModel chatGroupModel) {
             yourGrpNameTxt.setText(chatGroupModel.getName());
             String membersTxt = "";
             for(int i = 0; i < chatGroupModel.getMembers().size(); i++) {
                 membersTxt = membersTxt.concat(chatGroupModel.getMembers().get(i).toString());
                 if(i != chatGroupModel.getMembers().size() -1) {
                     membersTxt = membersTxt.concat(", ");
                 }
             }
             yourGrpMemberTxt.setText(membersTxt);
             if(chatGroupModel.getChats() != null) {
                 for (ChatModel chat : chatGroupModel.getChats()) {
                     if (!chat.getRead_by().contains(DiscussionFragment.currentUserRollNo)) {
                         yourGrpUnreadIndicatorImg.setVisibility(View.VISIBLE);
                         yourGrpNameTxt.setTypeface(Typeface.DEFAULT_BOLD);
                         yourGrpMemberTxt.setTypeface(Typeface.DEFAULT_BOLD);
                     } else {
                         yourGrpUnreadIndicatorImg.setVisibility(View.GONE);
                         yourGrpNameTxt.setTypeface(Typeface.DEFAULT);
                         yourGrpMemberTxt.setTypeface(Typeface.DEFAULT);
                     }
                 }
             }
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(chatGroupModel.getChats() != null) {
                         for (ChatModel chat : chatGroupModel.getChats()) {
                             if (!chat.getRead_by().contains(DiscussionFragment.currentUserRollNo)) {
                                 chat.getRead_by().add(DiscussionFragment.currentUserRollNo);
                             }
                             FirebaseDatabase.getInstance().getReference(Constants.KEY_CHAT_GROUPS)
                                     .child(chatGroupModel.getId())
                                     .setValue(chatGroupModel);
                         }
                     }
                     Intent chatIntent = new Intent(context, DiscussionChatActivity.class);
                     chatIntent.putExtra(Constants.KEY_CHAT_GROUP_EXTRA, chatGroupModel);
                     context.startActivity(chatIntent);
                 }
             });
         }

     }
}
