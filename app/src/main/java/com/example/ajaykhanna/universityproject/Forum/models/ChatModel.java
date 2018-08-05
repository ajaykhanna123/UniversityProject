package com.example.ajaykhanna.universityproject.Forum.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 25-07-2018.
 */
public class ChatModel implements Parcelable{

    private long from_id, timestamp;
    private String message;
    private ArrayList<Long> read_by;

    public ChatModel() {
    }

    public ChatModel(long from_id, long timestamp, String message, ArrayList<Long> read_by) {
        this.from_id = from_id;
        this.timestamp = timestamp;
        this.message = message;
        this.read_by = read_by;
    }

    protected ChatModel(Parcel in) {
        from_id = in.readLong();
        timestamp = in.readLong();
        message = in.readString();
        read_by = new ArrayList<>();
        read_by = in.readArrayList(Long.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(from_id);
        dest.writeLong(timestamp);
        dest.writeString(message);
        dest.writeList(read_by);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

    public ArrayList<Long> getRead_by() {
        return read_by;
    }

    public void setRead_by(ArrayList<Long> read_by) {
        this.read_by = read_by;
    }

    public long getFrom_id() {
        return from_id;
    }

    public void setFrom_id(long from_id) {
        this.from_id = from_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
