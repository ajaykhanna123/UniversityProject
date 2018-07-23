package com.example.ajaykhanna.universityproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class NoticeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private FloatingActionButton floatAddNotice;


    public NoticeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notice, container, false);

        floatAddNotice=view.findViewById(R.id.floatAddNotice);

        floatAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addNoticeIntent=new Intent(getActivity().getApplicationContext(),AddNoticeActivity.class);
                startActivity(addNoticeIntent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event



}
