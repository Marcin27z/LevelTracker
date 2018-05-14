package com.example.marcin.munchkinleveltracker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

class MessageWaiter extends Thread {

  private ObjectInputStream objectInputStream;
  private onlineEventListener onlineEventListener;
  private int id;
  private Socket socket;

  MessageWaiter(ObjectInputStream objectInputStream, onlineEventListener onlineEventListener, int id, Socket socket) {
    this.objectInputStream = objectInputStream;
    this.onlineEventListener = onlineEventListener;
    this.id = id;
    this.socket = socket;
  }

  public void run() {
    while(!socket.isClosed()) {
      try {
        Object object = objectInputStream.readObject();
        onlineEventListener.newMessageArrived(object, id);
      } catch(SocketException e)  {
        onlineEventListener.endDisconnected(id);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
