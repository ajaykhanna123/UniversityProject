package com.example.ajaykhanna.universityproject.Forum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.Forum.adapters.DiscussionViewPagerAdapter;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.R;
import com.example.ajaykhanna.universityproject.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DiscussionFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressDialog progressDialog;
    FloatingActionButton addGroupBtn;
    public static long currentUserRollNo;
    ArrayList<ChatGroupModel> yourChatGroupModels;
    ArrayList<ChatGroupModel> joinChatGroupModels;
    AlertDialog.Builder dialog;

    public DiscussionFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yourChatGroupModels = new ArrayList<>();
        joinChatGroupModels = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        tabLayout = view.findViewById(R.id.discussionTabLayout);
        viewPager = view.findViewById(R.id.discussionViewPager);
        addGroupBtn = view.findViewById(R.id.discussionAddGroupFloatingBtn);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading chats");
        progressDialog.setMessage("Please wait...");
        createDialog();

        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                dialog.show();
            }
        });
        getCurrentUser();
    }

    void initTabPager() {
        viewPager.setAdapter(new DiscussionViewPagerAdapter(getChildFragmentManager(), yourChatGroupModels, joinChatGroupModels));
        tabLayout.setupWithViewPager(viewPager);
    }

    void loadChatGroups() {

        progressDialog.show();
        firebaseDatabase.getReference(Constants.KEY_CHAT_GROUPS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                yourChatGroupModels.clear();
                joinChatGroupModels.clear();
                for(DataSnapshot chatSnapShot: dataSnapshot.getChildren()){
                    ChatGroupModel chatGroupModel = chatSnapShot.getValue(ChatGroupModel.class);
                    if(chatGroupModel.getMembers().contains(currentUserRollNo))
                        yourChatGroupModels.add(chatGroupModel);
                    else
                        joinChatGroupModels.add(chatGroupModel);
                    progressDialog.hide();
                }
                initTabPager();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
                Toast.makeText(getContext(), "Couldn't load chat groups", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentUser() {
        progressDialog.show();
        String currentUser = mAuth.getCurrentUser().getUid();
        DatabaseReference myRefUsrId = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        myRefUsrId.keepSynced(true);


        myRefUsrId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserRollNo = dataSnapshot.child("rollNo").getValue(long.class);
                loadChatGroups();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
                String message = databaseError.getMessage();
                Toast.makeText(getContext(),"Data not retrieved:"+message,Toast.LENGTH_LONG).show();
            }
        });
    }

    void createDialog() {
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Add Group");

        final EditText editText = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter new group name");

        dialog.setView(editText);
        dialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewGroup(editText.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    void createNewGroup(String groupName) {
        String groupId = groupName.toLowerCase() + "_" + currentUserRollNo;
        long groupAdmin = currentUserRollNo;
        ArrayList<Long> members = new ArrayList<>();
        members.add(currentUserRollNo);
        if(TextUtils.isEmpty(groupName)){
            Toast.makeText(getContext(), "Please enter a valid group name", Toast.LENGTH_SHORT).show();
            return;
        }
        for (ChatGroupModel chatGroupModel: yourChatGroupModels) {
            if(groupId.equals(chatGroupModel.getId())){
                Toast.makeText(getContext(), "You already have a group with this name", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ChatGroupModel chatGroupModel = new ChatGroupModel();
        chatGroupModel.setAdmin(groupAdmin);
        chatGroupModel.setId(groupId);
        chatGroupModel.setName(groupName);
        chatGroupModel.setMembers(members);
        firebaseDatabase.getReference().child(Constants.KEY_CHAT_GROUPS).child(groupId).setValue(chatGroupModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Group created", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
