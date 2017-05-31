package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.notes_list)
    RecyclerView notesList;
    private static final String NOTES_REFERENCE = "notes";
    private List<String> notes = new ArrayList<>();
    private NoteAdapter adapter;
    private DatabaseReference myRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setDatabaseReferences();
        setViews();
    }

    private void setDatabaseReferences() {
        myRef = database.getReference(NOTES_REFERENCE);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String row = dataSnapshot.getValue(String.class);
                notes.add(row);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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



    private void setNoteList() {
        notesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes);
        notesList.setAdapter(adapter);
    }

    @Override
    void setViews() {
        setNoteList();
    }
}
