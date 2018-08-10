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

import com.example.ajaykhanna.universityproject.Forum.adapters.YourGrpListAdapter;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;
import com.example.ajaykhanna.universityproject.R;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 25-07-2018.
 */
public class DiscussionYourGroupFragment extends Fragment {

    RecyclerView yourGrpList;
    ArrayList<ChatGroupModel> yourChatGroupModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion_your_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            yourChatGroupModels = getArguments().getParcelableArrayList("yourChatGroupModels");
        }

        yourGrpList = view.findViewById(R.id.yourGrpsList);
        yourGrpList.setAdapter(new YourGrpListAdapter(getContext(), yourChatGroupModels));
        yourGrpList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
