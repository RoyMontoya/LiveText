package com.example.rmontoya.firebasetest.storage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListStorage {

    private static final String NOTES_REFERENCE = "notes";
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference listReference = database.getReference(NOTES_REFERENCE);

    public static DatabaseReference getListReference() {
        return listReference;
    }

}
