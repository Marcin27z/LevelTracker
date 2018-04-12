package com.example.marcin.munchkinleveltracker;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

class Controller {

  private static Controller instance;

  static Controller getInstance(String nickName) {
    if(instance == null)
      instance = new Controller(nickName);
    return instance;
  }

  private GameState gameState;
  private Player me;
  private Context context;
  private LinearLayout linearLayout;
  private ArrayList<PlayerField> playerFields;
  private MasterController masterController;
  private Handler handler;

  private Controller(String myNickName) {
    gameState = GameState.getInstance();
    addPlayer(myNickName);
    me = gameState.players.get(0);
    if(myNickName.matches(".*a"))
      me.setGender(Player.WOMAN);
    else
      me.setGender(Player.MAN);
    updateView();
  }

  void initContext(Context context) {
    this.context = context;
  }

  void initHandler(Handler handler) {
    this.handler = handler;
  }

  void initLinearLayout(LinearLayout linearLayout, ArrayList<PlayerField> playerFields) {
    this.linearLayout = linearLayout;
    this.playerFields = playerFields;
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
    playerFields.get(i).update(gameState.players.get(i + 1).getBasicLvl(), gameState.players.get(i + 1).getTotalLvl(), gameState.players.get(i + 1).getGender());
  }

  private void addPlayer(String nickName) {
    gameState.addPlayer(nickName, 1, 0);
  }

  void addBasicLvl() {
    me.addBasicLvl();
    masterController.sendGameState();
  }

  void removeBasicLvl() {
    me.removeBasicLvl();
    masterController.sendGameState();
  }

  void addEqLvl() {
    me.addEqLvl();
    masterController.sendGameState();
  }

  void removeEqLvl() {
    me.removeEqLvl();
    masterController.sendGameState();
  }

  void setGender(boolean value) {
    me.setGender(value);
    masterController.sendGameState();
  }

  boolean getGender() {
    return me.getGender();
  }
  int getMyBasicLvl() {
    return me.getBasicLvl();
  }

  int getMyEqLvl() {
    return me.getEqLvl();
  }

  int getMyTotalLvl() {
    return me.getTotalLvl();
  }

}
