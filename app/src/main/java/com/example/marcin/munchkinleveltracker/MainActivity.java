package com.example.marcin.munchkinleveltracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

  private Intent gameActivity;
  private Intent settingsActivity;
  private final String PREFERENCES_NAME = "MunchkinPreferences";
  private final String PREFERENCES_IP = "IP";
  private final String PREFERENCES_PORT = "Port";
  private final String PREFERENCES_NICK = "Nickname";
  private SharedPreferences preferences;
  private String nickName;
  private OnlineController onlineController;
  private InetAddress inetAddress;
  private int port;
  static MasterController masterController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    nickName = preferences.getString(PREFERENCES_NICK, "Guest");
    try {
      inetAddress = InetAddress.getByName(preferences.getString(PREFERENCES_IP, "127.0.0.1"));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    port = preferences.getInt(PREFERENCES_PORT, 9999);
    gameActivity = new Intent(this, GameActivity.class);
    onlineController = new OnlineController();
    masterController = new MasterController();
    masterController.initOnlineController(onlineController);
    onlineController.initMasterController(masterController);
    setContentView(R.layout.activity_main);
  }

  public void join(View view) {
    onlineController.startClient(inetAddress, port);
    startActivity(gameActivity);
  }

  public void host(View view) {
    onlineController.startServer(port);
    startActivity(gameActivity);
  }

  public void settings(View view) {
    settingsActivity = new Intent(this, SettingsActivity.class);
    startActivity(settingsActivity);
  }
}
