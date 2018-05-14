package com.example.marcin.munchkinleveltracker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

class Client extends Thread {

  private Socket socket;
  private InetAddress address;
  private int port;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private MessageWaiter messageWaiter;
  private onlineEventListener onlineEventListener;

  Client(InetAddress address, int port, onlineEventListener onlineEventListener) {
    this.address = address;
    this.port = port;
    this.onlineEventListener = onlineEventListener;
  }

  public void run() {
    try {
      socket = new Socket(address, port);
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectInputStream = new ObjectInputStream(socket.getInputStream());
      messageWaiter = new MessageWaiter(objectInputStream, onlineEventListener, 0, socket);
      messageWaiter.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void send(Object object) {
    try {
      objectOutputStream.writeObject(object);
      objectOutputStream.reset();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void disconnect() {
    try {
      socket.close();
      messageWaiter.join();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
