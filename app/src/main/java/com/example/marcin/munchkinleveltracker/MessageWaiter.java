package com.example.marcin.munchkinleveltracker;

import java.io.IOException;
import java.io.ObjectInputStream;

class MessageWaiter extends Thread {

  private ObjectInputStream objectInputStream;
  private onlineEventListener onlineEventListener;
  private int id;

  MessageWaiter(ObjectInputStream objectInputStream, onlineEventListener onlineEventListener, int id) {
    this.objectInputStream = objectInputStream;
    this.onlineEventListener = onlineEventListener;
    this.id = id;
  }

  public void run() {
    while(true) {
      try {
        Object object = objectInputStream.readObject();
        onlineEventListener.newMessageArrived(object, id);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
