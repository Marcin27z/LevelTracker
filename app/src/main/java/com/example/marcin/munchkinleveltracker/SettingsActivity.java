package com.example.marcin.munchkinleveltracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

  private final String PREFERENCES_NAME = "MunchkinPreferences";
  private final String PREFERENCES_IP = "IP";
  private final String PREFERENCES_PORT = "Port";
  private final String PREFERENCES_NICK = "Nickname";
  private SharedPreferences preferences;
  private EditText ip;
  private EditText port;
  private EditText nickName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    ip = findViewById(R.id.editTextIp);
    ip.setRawInputType(InputType.TYPE_CLASS_NUMBER);
    port = findViewById(R.id.editTextPort);
    port.setRawInputType(InputType.TYPE_CLASS_NUMBER);
    nickName = findViewById(R.id.editTextNickName);
    preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    nickName.setText(preferences.getString(PREFERENCES_NICK, ""));
    ip.setText(preferences.getString(PREFERENCES_IP, ""));
    port.setText(Integer.toString(preferences.getInt(PREFERENCES_PORT, 9999)));
  }

  public void saveIp(View view) {
    SharedPreferences.Editor preferencesEditor = preferences.edit();
    preferencesEditor.putString(PREFERENCES_IP, ip.getText().toString());
    preferencesEditor.putInt(PREFERENCES_PORT, Integer.parseInt(port.getText().toString()));
    preferencesEditor.apply();
    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
  }

  public void saveNick(View view) {
    SharedPreferences.Editor preferencesEditor = preferences.edit();
    preferencesEditor.putString(PREFERENCES_NICK, nickName.getText().toString());
    preferencesEditor.apply();
    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
  }


}
