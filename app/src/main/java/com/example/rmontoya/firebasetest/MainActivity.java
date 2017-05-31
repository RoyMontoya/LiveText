package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.notes_list)
    RecyclerView notesList;
    private static final String NOTES_REFERENCE = "notes";
    private List<String> notes = Arrays.asList("1","2","3");
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
        myRef.setValue(notes);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setViews() {
        setNoteList();
    }

    private void setNoteList() {
        notesList.setLayoutManager(new LinearLayoutManager(this));
        notesList.setAdapter(new NoteAdapter(notes));
    }
}
