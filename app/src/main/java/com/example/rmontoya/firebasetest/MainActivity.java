package com.example.rmontoya.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.example.rmontoya.firebasetest.model.Note;
import com.example.rmontoya.firebasetest.storage.ListStorage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.notes_list)
    RecyclerView notesList;
    @BindView(R.id.add_fab)
    FloatingActionButton addFab;
    private HashMap<String, Note> noteHash = new HashMap<>();
    private List<Note> noteArray = new ArrayList<>();
    private NoteAdapter adapter;
    private DatabaseReference listReference = ListStorage.getListReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setDatabaseReferences();
        setViews();
    }


    private void setNoteList() {
        notesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(noteArray);
        notesList.setAdapter(adapter);
    }

    private void updateAdapter() {
        noteArray.clear();
        noteArray.addAll(noteHash.values());
        adapter.notifyDataSetChanged();
    }

    @Override
    void setViews() {
        setNoteList();
        RxView.clicks(addFab).subscribe(aVoid -> {
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        });
    }

    private void setDatabaseReferences() {
        listReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note note = new Note(dataSnapshot.getValue(String.class), getFullKey(dataSnapshot.getKey()));
                noteHash.put(note.getFirebaseKey(), note);
                updateAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String position = getFullKey(dataSnapshot.getKey());
                Note note = noteHash.get(position);
                note.setNoteValue(dataSnapshot.getValue().toString());
                noteHash.put(position, note);
                updateAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String position = getFullKey(dataSnapshot.getKey());
                noteHash.remove(position);
                updateAdapter();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
