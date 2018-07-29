package com.example.ajaykhanna.universityproject.Forum.models;

import java.util.ArrayList;

/**
 * Created by Ayush Kataria on 25-07-2018.
 */
public class ChatModel {

    private long from_id, timestamp;
    private String message;
    private ArrayList<Long> read_by;

    public ChatModel() {
    }

    public ChatModel(long from_id, long timestamp, String message) {
        this.from_id = from_id;
        this.timestamp = timestamp;
        this.message = message;
    }

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
