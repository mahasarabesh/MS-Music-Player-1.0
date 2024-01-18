package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.materialswitch.MaterialSwitch;

public class Settings extends AppCompatActivity {

    SwitchCompat SizeSwitch;
    EditText sizeVal;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SizeSwitch = findViewById(R.id.SizeSwitch);
        sizeVal = findViewById(R.id.SizeVal);
        context =this;
        SizeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!sizeVal.getText().toString().equals("")) {
                        String str= sizeVal.getText().toString();
                        if (Integer.parseInt(sizeVal.getText().toString())>0){
                            MainActivity.songDetailsGenerator.sizeValue=Integer.parseInt(sizeVal.getText().toString());
                            MainActivity.songDetailsGenerator.doInBackground();
                        }
                        else{
                            Toast.makeText(context,"enter valid size",Toast.LENGTH_LONG).show();

                        }
                    }
                    else {
                        SizeSwitch.setChecked(false);
                        Toast.makeText(context,"enter the size",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    MainActivity.songDetailsGenerator.sizeValue=0;
                    MainActivity.songDetailsGenerator.doInBackground();
                }
            }

        });
    }

}