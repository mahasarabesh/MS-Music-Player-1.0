package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    public static EditText sizeVal;
    public static Spinner LengthSpinner;
    public static Spinner SleepSpinner;
    public static EditText LengthInput;
    public static EditText SleepInput;
    public static ArrayList<CheckBox> AudioFormat;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sizeVal = findViewById(R.id.SizeVal);
        LengthInput=findViewById(R.id.LengthInput);
        LengthSpinner=findViewById(R.id.LengthSpinner);
        SleepSpinner=findViewById(R.id.SleepSpinner);
        SleepInput=findViewById(R.id.SleepInput);
        AudioFormat=new ArrayList<>();
        AudioFormat.add(findViewById(R.id.mp3Checkbox));
        AudioFormat.add(findViewById(R.id.flacCheckbox));
        AudioFormat.add(findViewById(R.id.wavCheckbox));
        AudioFormat.add(findViewById(R.id.oggCheckbox));
        context =this;
    }

}