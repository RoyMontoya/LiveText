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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.notes_list)
    RecyclerView notesList;
    @BindView(R.id.add_fab)
    FloatingActionButton addFab;
    private List<Note> notes = new ArrayList<>();
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

    private void setDatabaseReferences() {
        listReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note note = new Note(dataSnapshot.getValue(String.class), getFullReference(dataSnapshot.getKey()));
                notes.add(note);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //TODO change the key
                int position = Integer.valueOf(dataSnapshot.getKey());
                Note note = notes.get(position);
                note.setNoteValue(dataSnapshot.getValue().toString());
                notes.set(position, note);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String getFullReference(String rowKey) {
        return listReference.getKey() + "/" + rowKey;
    }


    private void setNoteList() {
        notesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes);
        notesList.setAdapter(adapter);
    }

    @Override
    void setViews() {
        setNoteList();
        RxView.clicks(addFab).subscribe(aVoid -> {
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        });
    }
}
