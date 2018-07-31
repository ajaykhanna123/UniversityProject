package com.example.ajaykhanna.universityproject.Forum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajaykhanna.universityproject.Forum.adapters.JoinGroupListAdapter;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.R;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 29-07-2018.
 */
public class DiscussionJoinGroupFragment extends Fragment {

    RecyclerView joinGroupList;
    ArrayList<ChatGroupModel> joinChatGroupModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion_join_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null) {
            joinChatGroupModels = getArguments().getParcelableArrayList("joinChatGroupModels");
        }

        joinGroupList = view.findViewById(R.id.joinGroupList);
        joinGroupList.setAdapter(new JoinGroupListAdapter(getContext(), joinChatGroupModels));
        joinGroupList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
