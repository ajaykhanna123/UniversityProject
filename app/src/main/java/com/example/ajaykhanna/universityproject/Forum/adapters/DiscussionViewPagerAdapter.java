package com.example.ajaykhanna.universityproject.Forum.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ajaykhanna.universityproject.Forum.DiscussionYourGrpFragment;
import com.example.ajaykhanna.universityproject.Forum.models.ChatGroupModel;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 24-07-2018.
 */
public class DiscussionViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titleList = {"Your Groups", "Join Groups"};
    ArrayList<ChatGroupModel> chatGroupModels;
    ArrayList<Fragment> fragments = new ArrayList<>();

    public DiscussionViewPagerAdapter(FragmentManager fm, ArrayList<ChatGroupModel> chatGroupModels) {
        super(fm);
        this.chatGroupModels = chatGroupModels;
    }

    @Override
    public int getCount() {
        return titleList.length;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList("chatGrpModels", chatGroupModels);
        switch (position) {
            case 0 : DiscussionYourGrpFragment discussionYourGrpFragment =  new DiscussionYourGrpFragment();
                discussionYourGrpFragment.setArguments(fragmentArguments);
                return discussionYourGrpFragment;
        }
        return new Fragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }

    public void clearAll() {

    }
}
