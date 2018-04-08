package com.example.marcin.munchkinleveltracker;

class MasterController {

  private OnlineController onlineController;
  private Controller controller;

  void initOnlineController(OnlineController onlineController) {
    this.onlineController= onlineController;
  }

  void initController(Controller controller) {
    this.controller = controller;
  }

  void sendGameState() {
    onlineController.sendGameState();
  }

  void updatePlayerField(int i) {
    controller.updatePlayerField(i);
  }

  void updateView() {
    controller.updateView();
  }
}
