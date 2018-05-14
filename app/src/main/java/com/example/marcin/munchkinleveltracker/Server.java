package com.example.marcin.munchkinleveltracker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Server extends Thread {

  private ServerSocket serverSocket;
  private int port;
  private ArrayList<ClientHandler> clientHandlers;
  private onlineEventListener onlineEventListener;
  private int clients = 0;

  Server(int port, onlineEventListener onlineEventListener) {
    this.port = port;
    this.onlineEventListener = onlineEventListener;
    try {
      serverSocket = new ServerSocket(port);
      clientHandlers = new ArrayList<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    Socket clientSocket;
    ClientHandler clientHandler;
    while(true) {
      try {
        clientSocket = serverSocket.accept();
        clientHandler = new ClientHandler(clientSocket, onlineEventListener, clients++);
        clientHandlers.add(clientHandler);
        clientHandler.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  void sendToAll(Object object) {
    for(ClientHandler clientHandler: clientHandlers) {
      clientHandler.send(object);
    }
  }

  void sendToAllExcept(Object object, int id) {
    for(ClientHandler clientHandler: clientHandlers) {
      if(clientHandler.getClientId() != id)
        clientHandler.send(object);
    }
  }

  void disconnectClient(int id) {
    clientHandlers.get(id).disconnect();
    try {
      clientHandlers.get(id).join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class ClientHandler extends Thread {

  private Socket socket;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private MessageWaiter messageWaiter;
  private onlineEventListener onlineEventListener;
  private int id;
  private BlockingQueue<Object> queue;

  ClientHandler(Socket socket, onlineEventListener onlineEventListener, int id) {
    this.socket = socket;
    this.onlineEventListener = onlineEventListener;
    this.id = id;
    try {
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectInputStream = new ObjectInputStream(socket.getInputStream());
      messageWaiter = new MessageWaiter(objectInputStream, onlineEventListener, id, socket);
      messageWaiter.start();
      queue = new ArrayBlockingQueue<>(10);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    while(true) {
      try {
        objectOutputStream.writeObject(queue.take());
        objectOutputStream.reset();
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  void send(Object object) {
    try {
      queue.put(object);
    } catch (InterruptedException e) {
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

  int getClientId() {
    return id;
  }
}
