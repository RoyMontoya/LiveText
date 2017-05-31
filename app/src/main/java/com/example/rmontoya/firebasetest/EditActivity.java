package com.example.rmontoya.firebasetest;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edit_row_text)
    EditText editRowText;
    @BindView(R.id.save_fab)
    FloatingActionButton saveFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        setViews();
    }

    @Override
    void setViews() {

    }
}
