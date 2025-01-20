package com.example.sqlitedbactivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.sqlitedbactivity.FeedReaderContract.FeedEntry.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.DialogInterface;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText name, contact, address;
    Button insert, update, delete, view;
    ImageButton btnSetting;

    MediaPlayer mediaPlayer;

    Button btnChangeColor;
    Boolean soundsOnorOff = true;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            soundsOnorOff = getIntent().getBooleanExtra("soundsOnorOff", true);
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        name = findViewById(R.id.etName);
        contact = findViewById(R.id.etContact);
        address = findViewById(R.id.etAddress);
        insert = findViewById(R.id.btnInsert);
        delete = findViewById(R.id.btnDelete);
        update = findViewById(R.id.btnUpdate);
        view = findViewById(R.id.btnView);
        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnSetting = findViewById(R.id.imageButtonSettings);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound_effect);

        DBHelper db = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (soundsOnorOff){
                    mediaPlayer.start();
                }
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String addressTXT = address.getText().toString();

                Boolean checkInsertData = db.insertUserData(nameTXT, contactTXT, addressTXT);

                if(checkInsertData == true) {
                    Toast.makeText(MainActivity.this, "New Entry Saved.", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    contact.setText("");
                    address.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Entry Not Found", Toast.LENGTH_SHORT).show();

            }

        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundsOnorOff){
                    mediaPlayer.start();
                }
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String addressTXT = address.getText().toString();


                Boolean checkUpdateData = db.updateUserData(nameTXT, contactTXT, addressTXT);
                if(checkUpdateData == true) {
                    Toast.makeText(MainActivity.this, "Entry Updated.", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    contact.setText("");
                    address.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Entry does not exist.", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundsOnorOff){
                    mediaPlayer.start();
                }
                String nameTXT = name.getText().toString();

                Boolean checkDeleteData = db.deleteUserData(nameTXT);
                if(checkDeleteData == true) {
                    Toast.makeText(MainActivity.this, "Entry Deleted.", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    contact.setText("");
                    address.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Entry Not Saved.", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Cursor res = db.getData();
                if (soundsOnorOff){
                    mediaPlayer.start();
                }
                if(res.getCount() == 0){
                    Toast.makeText(MainActivity.this, "No Data Exists.", Toast.LENGTH_SHORT).show();
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append(USER_NAME + " " + res.getString(0) + "\n");
                        buffer.append(USER_CONTACT + " " + res.getString(1) + "\n");
                        buffer.append(USER_ADDRESS + " " + res.getString(2) + "\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("User Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                // Generate random colors for each button
                int colorInsert = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                int colorView = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                int colorDelete = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                int colorUpdate = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

                // Set background colors for each button
                insert.setBackgroundColor(colorInsert);
                view.setBackgroundColor(colorView);
                delete.setBackgroundColor(colorDelete);
                update.setBackgroundColor(colorUpdate);

                if (soundsOnorOff){
                    mediaPlayer.start();
                }
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                intent.putExtra("soundsOnorOff", soundsOnorOff);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the app
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
