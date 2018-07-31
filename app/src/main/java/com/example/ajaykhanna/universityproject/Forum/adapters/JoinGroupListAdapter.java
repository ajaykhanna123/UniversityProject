package com.example.ajaykhanna.universityproject.Forum.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.Forum.DiscussionFragment;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.R;
import com.example.ajaykhanna.universityproject.Utils.Constants;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 29-07-2018.
 */
public class JoinGroupListAdapter extends RecyclerView.Adapter<JoinGroupListAdapter.ViewHolder> {

    private ArrayList<ChatGroupModel> chatGroupModels;
    private Context context;

    public JoinGroupListAdapter( Context context, ArrayList<ChatGroupModel> chatGroupModels) {
        this.chatGroupModels = chatGroupModels;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.join_group_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindJoinChatGroup(chatGroupModels.get(position));
    }

    @Override
    public int getItemCount() {
        return chatGroupModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView joinGrpNameTxt;
        TextView joinGrpAdminTxt;
        Button joinGrpBtn;

        ViewHolder(View itemView) {
            super(itemView);
            joinGrpAdminTxt = itemView.findViewById(R.id.joinGrpAdminTxt);
            joinGrpNameTxt = itemView.findViewById(R.id.joinGrpNameTxt);
            joinGrpBtn = itemView.findViewById(R.id.joinGrpBtn);
        }

        void bindJoinChatGroup(final ChatGroupModel chatGroupModel) {

            joinGrpNameTxt.setText(chatGroupModel.getName());
            joinGrpAdminTxt.setText(Long.toString(chatGroupModel.getAdmin()));

            joinGrpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(chatGroupModel.getPending_requests() != null) {
                        Boolean alreadyPending = false;
                        for(long pendingRollNo: chatGroupModel.getPending_requests()) {
                            if (pendingRollNo == DiscussionFragment.currentUserRollNo) {
                                Toast.makeText(context, "Join request already submitted", Toast.LENGTH_SHORT).show();
                                alreadyPending = true;
                            }
                        }
                        if(!alreadyPending) {
                            chatGroupModel.getPending_requests().add(DiscussionFragment.currentUserRollNo);
                            updatePendingRequests(chatGroupModel.getPending_requests());
                        }
                    }else {
                        ArrayList<Long> pendingRequests = new ArrayList<>();
                        pendingRequests.add(DiscussionFragment.currentUserRollNo);
                        updatePendingRequests(pendingRequests);
                    }
                }

                void updatePendingRequests(ArrayList<Long> pendingRequests) {
                    FirebaseDatabase.getInstance().getReference().child(Constants.KEY_CHAT_GROUPS)
                            .child(chatGroupModel.getName().toLowerCase() + "_" + chatGroupModel.getAdmin())
                            .child(Constants.KEY_PENDING_REQUESTS)
                            .setValue(pendingRequests);
                }
            });
        }
    }
}
