package com.example.marcin.munchkinleveltracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

  private Controller controller;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final String PREFERENCES_NAME = "MunchkinPreferences";
    final String PREFERENCES_NICK = "Nickname";
    SharedPreferences preferences;
    preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    controller = Controller.getInstance(preferences.getString(PREFERENCES_NICK, ""));
    controller.initContext(getApplicationContext());
    setContentView(R.layout.activity_game);
    MainActivity.masterController.initController(controller);
    controller.initMasterController(MainActivity.masterController);
  }

  Controller getController() {
    return controller;
  }

  private boolean doubleBackToExitPressedOnce = false;

  @Override
  public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      return;
    }
    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }
}
