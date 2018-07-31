package com.example.ajaykhanna.universityproject.Forum.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ajaykhanna.universityproject.Forum.DiscussionJoinGroupFragment;
import com.example.ajaykhanna.universityproject.Forum.DiscussionYourGroupFragment;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 24-07-2018.
 */
public class DiscussionViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titleList = {"Your Groups", "Join Groups"};
    private ArrayList<ChatGroupModel> yourChatGroupModels;
    private ArrayList<ChatGroupModel> joinChatGroupModels;

    public DiscussionViewPagerAdapter(FragmentManager fm, ArrayList<ChatGroupModel> yourChatGroupModels,
                                      ArrayList<ChatGroupModel> joinChatGroupModels) {
        super(fm);
        this.yourChatGroupModels = yourChatGroupModels;
        this.joinChatGroupModels = joinChatGroupModels;
    }

    @Override
    public int getCount() {
        return titleList.length;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList("yourChatGroupModels", yourChatGroupModels);
        fragmentArguments.putParcelableArrayList("joinChatGroupModels", joinChatGroupModels);
        switch (position) {
            case 0 : DiscussionYourGroupFragment discussionYourGroupFragment =  new DiscussionYourGroupFragment();
                discussionYourGroupFragment.setArguments(fragmentArguments);
                return discussionYourGroupFragment;
            case 1:
                DiscussionJoinGroupFragment discussionJoinGroupFragment = new DiscussionJoinGroupFragment();
                discussionJoinGroupFragment.setArguments(fragmentArguments);
                return discussionJoinGroupFragment;
        }
        return new Fragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }
}
