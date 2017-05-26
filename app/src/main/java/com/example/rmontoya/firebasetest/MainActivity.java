package com.example.rmontoya.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.submit_button)
    Button submitButton;
    @BindView(R.id.cloud_text)
    EditText cloudText;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        setDatabaseReferences();
        setViews();
    }

    private void setDatabaseReferences() {
        myRef = database.getReference("message");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                cloudText.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setViews() {
        RxTextView.textChanges(cloudText).subscribe(charSequence -> {
            myRef.setValue(charSequence.toString());
        });
    }
}
