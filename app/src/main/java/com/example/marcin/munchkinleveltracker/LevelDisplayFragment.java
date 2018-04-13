package com.example.marcin.munchkinleveltracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LevelDisplayFragment extends Fragment {

  LinearLayout linearLayout;
  Controller controller;
  Handler handler;
  private ArrayList<PlayerField> playerFields;

  public LevelDisplayFragment() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_level_display, container, false);
    playerFields = new ArrayList<>();
    linearLayout = view.findViewById(R.id.linearLayout);
    GameActivity gameActivity = (GameActivity) getActivity();
    controller = gameActivity.getController();
    controller.initLinearLayout(linearLayout, playerFields);
    handler = new Handler();
    controller.initHandler(handler);
    controller.updateView();
    return view;
  }
}
