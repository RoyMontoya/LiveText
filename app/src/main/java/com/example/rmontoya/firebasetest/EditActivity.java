package com.example.rmontoya.firebasetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import com.example.rmontoya.firebasetest.adapter.NoteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edit_row_text)
    EditText editRowText;
    @BindView(R.id.save_fab)
    FloatingActionButton saveFab;
    private String note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if(getIntent().hasExtra(NoteAdapter.ROW_EXTRA))
            note = getIntent().getExtras().getString(NoteAdapter.ROW_EXTRA);
        ButterKnife.bind(this);
        setViews();
    }

    @Override
    void setViews() {
        editRowText.setText(note);
    }
}
