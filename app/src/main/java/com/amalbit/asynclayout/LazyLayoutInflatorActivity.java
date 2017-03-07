package com.amalbit.asynclayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by amal.chandran on 19/09/16.
 */
public class LazyLayoutInflatorActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout parentLayout;
    private Button btnAdd;
    private Button btnRemove;
    private EditText edtCount;
    private SwitchCompat switchAsync;

    private boolean isAsyncOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflation);
        bindViews();
        addListners();
    }

    private void bindViews(){
        parentLayout = (LinearLayout) findViewById(R.id.layout_parent);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnRemove = (Button) findViewById(R.id.btn_remove);
        edtCount = (EditText) findViewById(R.id.edt_viewcount);
        switchAsync = (SwitchCompat) findViewById(R.id.switch1);
    }

    private void addListners(){
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        switchAsync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAsyncOn = b;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                String number = edtCount.getText().toString();
                if(number.isEmpty())number = "refactor1";
                int count = Integer.parseInt(number);
                loadInsaneNumberOfViews(count);
                break;
            case R.id.btn_remove:
                parentLayout.removeAllViews();
                break;
        }
    }

    private void loadInsaneNumberOfViews(int count){
        for(int i = 0; i < count; i++){
            log(System.currentTimeMillis()+"");
            if(!isAsyncOn) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_dummy, null);
                LinearLayout inflatedLayout = (LinearLayout) view.findViewById(R.id.layout_dummy);
                parentLayout.addView(inflatedLayout);
            }else {
                AsyncLayoutInflater asyncInflator = new AsyncLayoutInflater(this);
                asyncInflator.inflate(R.layout.layout_dummy, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(View view, int resid, ViewGroup parent) {
                        LinearLayout inflatedLayout = (LinearLayout) view.findViewById(R.id.layout_dummy);
                        parentLayout.addView(inflatedLayout);
                    }
                });
            }
        }
    }

    private void log(String log){
        Log.i(LazyLayoutInflatorActivity.class.getSimpleName(), ""+log);
    }
}
