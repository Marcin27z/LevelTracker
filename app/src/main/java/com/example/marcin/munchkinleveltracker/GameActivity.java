package com.example.marcin.munchkinleveltracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

  private Controller controller;
  private TextView basicLvl;
  private TextView eqLvl;
  private TextView totalLvl;
  private LinearLayout linearLayout;
  private Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final String PREFERENCES_NAME = "MunchkinPreferences";
    final String PREFERENCES_NICK = "Nickname";
    SharedPreferences preferences;
    preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    setContentView(R.layout.activity_game);
    linearLayout = findViewById(R.id.linearLayout);
    handler = new Handler();
    controller = new Controller(preferences.getString(PREFERENCES_NICK, ""), getApplicationContext(), linearLayout, handler);
    MainActivity.masterController.initController(controller);
    controller.initMasterController(MainActivity.masterController);
    basicLvl = findViewById(R.id.basicLvlTextView);
    eqLvl = findViewById(R.id.eqLvLTextView);
    totalLvl = findViewById(R.id.totalLvlTextView);
    updateBasicLvl();
    updateEqLvl();
    updateTotal();
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

  public void removeBasicLvl(View view) {
    controller.removeBasicLvl();
    updateBasicLvl();
    updateTotal();
  }

  public void addBasicLvl(View view) {
    controller.addBasicLvl();
    updateBasicLvl();
    updateTotal();
  }

  public void removeEqLvl(View view) {
    controller.removeEqLvl();
    updateEqLvl();
    updateTotal();
  }

  public void addEqLvl(View view) {
    controller.addEqLvl();
    updateEqLvl();
    updateTotal();
  }

  private void updateTotal() {
    totalLvl.setText(Integer.toString(controller.getMyTotalLvl()));
  }

  private void updateEqLvl() {
    eqLvl.setText(Integer.toString(controller.getMyEqLvl()));
  }

  private void updateBasicLvl() {
    basicLvl.setText(Integer.toString(controller.getMyBasicLvl()));
  }
}
