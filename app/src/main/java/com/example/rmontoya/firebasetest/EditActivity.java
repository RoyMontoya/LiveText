package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;
import com.example.rmontoya.firebasetest.model.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edit_row_text)
    EditText editRowText;
    @BindView(R.id.save_fab)
    FloatingActionButton saveFab;
    private Note note;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if(getIntent().hasExtra(NoteAdapter.ROW_EXTRA))
            note = (Note) getIntent().getSerializableExtra(NoteAdapter.ROW_EXTRA);
        ButterKnife.bind(this);
        setViews();
    }

    @Override
    void setViews() {
        editRowText.setText(note.getNoteValue());
        DatabaseReference ref = database.getReference(note.getFirebaseKey());
        RxTextView.textChanges(editRowText).subscribe(charSequence -> ref.setValue(charSequence.toString()));
    }
}
