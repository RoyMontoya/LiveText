package com.example.rmontoya.firebasetest.model;

import java.io.Serializable;

public class Note implements Serializable {

    private String noteValue;
    private String firebaseKey;

    public Note(String text, String key) {
        this.noteValue = text;
        this.firebaseKey = key;
    }

    public String getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(String noteValue) {
        this.noteValue = noteValue;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}
