package com.example.ajaykhanna.universityproject.Forum;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ajaykhanna.universityproject.Forum.adapters.DiscussionViewPagerAdapter;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.R;
import com.example.ajaykhanna.universityproject.Utils.Constants;
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

        getCurrentUser();
    }

    void initTabPager() {
        viewPager.setAdapter(new DiscussionViewPagerAdapter(getChildFragmentManager(), yourChatGroupModels));
        tabLayout.setupWithViewPager(viewPager);
    }

    void loadChatGroups() {

        progressDialog.show();
        yourChatGroupModels.clear();
        joinChatGroupModels.clear();
        firebaseDatabase.getReference(Constants.KEY_CHAT_GROUPS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
}
