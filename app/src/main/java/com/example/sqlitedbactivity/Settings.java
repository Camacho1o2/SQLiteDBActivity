package com.example.sqlitedbactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sqlitedbactivity.databinding.ActivitySettingsBinding;

public class Settings extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySettingsBinding binding;
    Boolean soundsOnorOff;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch soundsSwitch;
    Button goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundsSwitch = findViewById(R.id.switchSounds);
        goHome = findViewById(R.id.buttonGoBack);

        try{
            soundsOnorOff = getIntent().getBooleanExtra("soundsOnorOff", true);
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

       /* soundsSwitch.setChecked(soundsOnorOff);*/



        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                intent.putExtra("soundsOnorOff", soundsOnorOff);
                startActivity(intent);
            }
        });
    }

    public void onSoundSwitchChecked(){
        if (soundsSwitch.isChecked()){
            soundsOnorOff = true;
        } else {
            soundsOnorOff = false;
        }
    }

}