package com.example.marcin.munchkinleveltracker;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable{
  private static GameState instance = null;

  ArrayList<Player> players;

  private GameState() {
    players = new ArrayList<>();
  }

  public static GameState getInstance() {
    if(instance == null)
      instance = new GameState();
    return instance;
  }

  void addPlayer(String nickName, int basicLvl, int eqLvl) {
    players.add(new Player(nickName,basicLvl, eqLvl));
  }


}

class Player implements Serializable {
  private int basicLvl;
  private int eqLvl;
  private String nickName;

  Player(String nickName, int basicLvl, int eqLvl) {
    this(nickName);
    this.basicLvl = basicLvl;
    this.eqLvl = eqLvl;
  }

  private Player(String nickName) {
    this.nickName = nickName;
  }

  void assign(Player other) {
    basicLvl = other.getBasicLvl();
    eqLvl = other.getEqLvl();
  }

  void addBasicLvl() {
    if(basicLvl < 10)
      basicLvl++;
  }

  void removeBasicLvl() {
    if(basicLvl > 1)
      basicLvl--;
  }

  void addEqLvl() {
    eqLvl++;
  }

  void removeEqLvl() {
    if(eqLvl > 0)
      eqLvl--;
  }

  int getBasicLvl() {
    return basicLvl;
  }

  int getEqLvl() {
    return eqLvl;
  }

  int getTotalLvl() {
    return eqLvl + basicLvl;
  }

  String getNickName() {
    return nickName;
  }
}