package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.example.rmontoya.firebasetest.model.Note;
import com.example.rmontoya.firebasetest.storage.ListStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    @BindView(R.id.edit_row_text)
    EditText editRowText;
    private Note note;
    private DatabaseReference listReference = ListStorage.getListReference();
    private DatabaseReference noteRef;
    private String noteFirebaseKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(NoteAdapter.ROW_EXTRA)) {
            note = (Note) getIntent().getSerializableExtra(NoteAdapter.ROW_EXTRA);
            noteFirebaseKey = note.getFirebaseKey();
            setNoteInfo();
        } else {
            DatabaseReference pushedRef = listReference.push();
            pushedRef.setValue("");
            noteFirebaseKey = "notes/" + pushedRef.getKey();
        }


        setViews();
    }

    private void setNoteInfo() {
        editRowText.setText(note.getNoteValue());
    }

    @Override
    void setViews() {
        noteRef = database.getReference(noteFirebaseKey);
        RxTextView.textChanges(editRowText).subscribe(charSequence ->
                noteRef.setValue(charSequence.toString()));
    }
}
