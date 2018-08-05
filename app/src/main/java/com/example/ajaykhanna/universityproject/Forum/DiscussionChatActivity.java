package com.example.ajaykhanna.universityproject.Forum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.Forum.adapters.ChatAdapter;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.Forum.models.ChatModel;
import com.example.ajaykhanna.universityproject.R;
import com.example.ajaykhanna.universityproject.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiscussionChatActivity extends AppCompatActivity {

    public static final int MY_MESSAGE = 1;
    public static final int YOUR_MESSAGE = 2;
    Toolbar toolbar;
    RecyclerView chatRecyclerView;
    EditText messageTxt;
    ImageButton sendBtn;
    ChatGroupModel chatGroupModel;
    ArrayList<ChatModel> chatModels;
    ChatAdapter chatAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_chat);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chatGroupModel.getName());

        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.scrollToPosition(chatModels.size() - 1);
        loadChatMessages();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageTxt.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(DiscussionChatActivity.this, "Please enter some message", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                ArrayList<Long> read_by = new ArrayList<>();
                read_by.add(DiscussionFragment.currentUserRollNo);
                ChatModel chatModel = new ChatModel(DiscussionFragment.currentUserRollNo, System.currentTimeMillis(), message, read_by);
                if(chatModels == null)
                    chatModels = new ArrayList<>();
                chatModels.add(chatModel);
                database.getReference(Constants.KEY_CHAT_GROUPS).child(chatGroupModel.getId()).child("chats").setValue(chatModels);
                messageTxt.setText("");
            }
        });
    }

    void init() {
        toolbar = findViewById(R.id.chatToolbar);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageTxt = findViewById(R.id.messageEditText);
        sendBtn = findViewById(R.id.chatSendButton);
        chatGroupModel = getIntent().getParcelableExtra(Constants.KEY_CHAT_GROUP_EXTRA);
        chatModels = chatGroupModel.getChats();
        chatAdapter = new ChatAdapter(this, chatModels);
        linearLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void loadChatMessages() {
        FirebaseDatabase.getInstance().getReference().child(Constants.KEY_CHAT_GROUPS).child(chatGroupModel.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chatGroupModel = dataSnapshot.getValue(ChatGroupModel.class);
                        chatModels = chatGroupModel.getChats();
                        chatAdapter = new ChatAdapter(DiscussionChatActivity.this, chatModels);
                        chatRecyclerView.setAdapter(chatAdapter);
                        chatRecyclerView.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.scrollToPosition(chatModels.size() - 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DiscussionChatActivity.this, "Couldn't load chats", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
