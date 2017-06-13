package com.example.rmontoya.firebasetest;

import android.support.v7.app.AppCompatActivity;

import com.example.rmontoya.firebasetest.storage.ListStorage;

public abstract class BaseActivity extends AppCompatActivity {

    abstract void setViews();

    public String getFullKey(String rowKey) {
        return ListStorage.getListReference().getKey() + "/" + rowKey;
    }
}
