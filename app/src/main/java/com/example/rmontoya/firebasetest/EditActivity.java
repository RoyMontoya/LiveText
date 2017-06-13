package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.example.rmontoya.firebasetest.model.Note;
import com.example.rmontoya.firebasetest.storage.ListStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class EditActivity extends BaseActivity {


    @BindView(R.id.edit_row_text)
    EditText editRowText;
    @BindView(R.id.delete_button)
    Button deleteButton;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Note note;
    private DatabaseReference listReference = ListStorage.getListReference();
    private DatabaseReference noteRef;
    private String noteFirebaseKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().hasExtra(NoteAdapter.ROW_EXTRA)) {
            note = (Note) getIntent().getSerializableExtra(NoteAdapter.ROW_EXTRA);
            noteFirebaseKey = note.getFirebaseKey();
            setNoteInfo();
        } else {
            DatabaseReference pushedRef = listReference.push();
            pushedRef.setValue("");
            noteFirebaseKey = getFullKey(pushedRef.getKey());
        }
        setViews();
    }

    private void setNoteInfo() {
        editRowText.setText(note.getNoteValue());
    }

    @Override
    void setViews() {
        RxView.clicks(deleteButton).subscribe(aVoid -> {
            noteRef.removeValue();
            finish();
        });
        noteRef = database.getReference(noteFirebaseKey);
        RxTextView.textChanges(editRowText).subscribe(charSequence ->
                noteRef.setValue(charSequence.toString()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
