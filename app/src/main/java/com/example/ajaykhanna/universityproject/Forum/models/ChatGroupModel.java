package com.example.ajaykhanna.universityproject.Forum.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 25-07-2018.
 */
public class ChatGroupModel implements Parcelable {

    private long admin;
    private String name;
    private ArrayList<ChatModel> chats;
    private ArrayList<Long> members;
    private ArrayList<Long> pending_requests;
    private String id;

    public ChatGroupModel() {
    }

    public ChatGroupModel(long admin, String name, ArrayList<ChatModel> chats, ArrayList<Long> members, ArrayList<Long> pending_requests, String id) {
        this.admin = admin;
        this.name = name;
        this.chats = chats;
        this.members = members;
        this.pending_requests = pending_requests;
        this.id = id;
    }

    protected ChatGroupModel(Parcel in) {
        admin = in.readLong();
        name = in.readString();
        chats = in.createTypedArrayList(ChatModel.CREATOR);
        id = in.readString();
        members = new ArrayList<>();
        pending_requests = new ArrayList<>();
        in.readList(members, Long.class.getClassLoader());
        in.readList(pending_requests, Long.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(admin);
        dest.writeString(name);
        dest.writeTypedList(chats);
        dest.writeString(id);
        dest.writeList(members);
        dest.writeList(pending_requests);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatGroupModel> CREATOR = new Creator<ChatGroupModel>() {
        @Override
        public ChatGroupModel createFromParcel(Parcel in) {
            return new ChatGroupModel(in);
        }

        @Override
        public ChatGroupModel[] newArray(int size) {
            return new ChatGroupModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAdmin() {
        return admin;
    }

    public void setAdmin(long admin) {
        this.admin = admin;
    }

    public ArrayList<ChatModel> getChats() {
        return chats;
    }

    public void setChats(ArrayList<ChatModel> chats) {
        this.chats = chats;
    }

    public ArrayList<Long> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Long> members) {
        this.members = members;
    }

    public ArrayList<Long> getPending_requests() {
        return pending_requests;
    }

    public void setPending_requests(ArrayList<Long> pending_requests) {
        this.pending_requests = pending_requests;
    }
}
