package com.example.marcin.munchkinleveltracker;

import java.net.InetAddress;

public class OnlineController implements onlineEventListener {

  private Server server;
  private Client client;
  private GameState gameState;
  private int me;
  private MasterController masterController;

  void startServer(int port) {
    server = new Server(port, this);
    server.start();
    gameState = GameState.getInstance();
    me = 0;
  }

  void startClient(InetAddress address, int port) {
    client = new Client(address, port, this);
    client.start();
    gameState = GameState.getInstance();
    me = 1;
  }

  void sendGameState() {
    if(me == 1) client.send(gameState);
    if(me == 0) server.sendToAll(gameState);
  }

  public void newMessageArrived(Object object, int id) {
    if(me == 0) server.sendToAllExcept(object, id);
    GameState receivedGameState = (GameState) object;
    for(Player receivedPlayer: receivedGameState.players) {
      boolean found = false;
      for(Player player: gameState.players) {
        if(receivedPlayer.getNickName().equals(player.getNickName())){
          player.assign(receivedPlayer);
          found = true;
          break;
        }
      }
        /*for(int i = 0; i < gameState.players.size(); ++i) {
          if(receivedPlayer.getNickName().equals(gameState.players.get(i).getNickName())) {
            gameState.players.get(i).addBasicLvl();
            found = true;
            break;
          }
        }*/
      if(!found) {
        gameState.addPlayer(receivedPlayer.getNickName(), receivedPlayer.getBasicLvl(), receivedPlayer.getEqLvl());
      }
    }
    masterController.updateView();
  }

  void initMasterController(MasterController masterController) {
    this.masterController = masterController;
  }
}

interface onlineEventListener {
  void newMessageArrived(Object object, int id);
}
