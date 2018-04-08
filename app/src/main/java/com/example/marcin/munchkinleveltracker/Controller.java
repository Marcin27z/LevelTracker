package com.example.marcin.munchkinleveltracker;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

class Controller {

  private GameState gameState;
  private Context context;
  private LinearLayout linearLayout;
  private ArrayList<PlayerField> playerFields;
  private MasterController masterController;
  private Handler handler;

  Controller(String myNickName, Context context, LinearLayout linearLayout, Handler handler) {
    gameState = GameState.getInstance();
    playerFields = new ArrayList<>();
    this.context = context;
    this.linearLayout = linearLayout;
    this.handler = handler;
    addPlayer(myNickName);
    updateView();
  }

  void initMasterController(MasterController masterController) {
    this.masterController = masterController;
  }

  private void addPlayerField(String nickName) {
    final PlayerField playerField = new PlayerField(context, handler);
    playerField.setName(nickName);
    playerFields.add(playerField);
    handler.post(new Runnable() {
      public void run() {
        linearLayout.addView(playerField, new TableLayout.LayoutParams(300, 400));
      }
    });
  }

  void updateView() {
    for(int i = 1; i < gameState.players.size(); ++i) {
      boolean exists = false;
      for(PlayerField playerField: playerFields) {
        if(playerField.getName().equals(gameState.players.get(i).getNickName()))
          exists = true;
      }
      if(!exists)
        addPlayerField(gameState.players.get(i).getNickName());
      updatePlayerField(i - 1);
    }
  }

  void updatePlayerField(int i) {
    playerFields.get(i).update(gameState.players.get(i + 1).getBasicLvl(), gameState.players.get(i + 1).getTotalLvl());
  }

  private void addPlayer(String nickName) {
    gameState.addPlayer(nickName, 1, 0);
  }

  void addBasicLvl() {
    gameState.players.get(0).addBasicLvl();
    masterController.sendGameState();
  }

  void removeBasicLvl() {
    gameState.players.get(0).removeBasicLvl();
    masterController.sendGameState();
  }

  void addEqLvl() {
    gameState.players.get(0).addEqLvl();
    masterController.sendGameState();
  }

  void removeEqLvl() {
    gameState.players.get(0).removeEqLvl();
    masterController.sendGameState();
  }

  int getMyBasicLvl() {
    return gameState.players.get(0).getBasicLvl();
  }

  int getMyEqLvl() {
    return gameState.players.get(0).getEqLvl();
  }

  int getMyTotalLvl() {
    return gameState.players.get(0).getTotalLvl();
  }
}
