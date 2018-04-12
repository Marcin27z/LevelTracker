package com.example.marcin.munchkinleveltracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

public class LevelControlFragment extends Fragment {

  private TextView basicLvl;
  private TextView eqLvl;
  private TextView totalLvl;
  private Button removeBasicLvl;
  private Button addBasicLvl;
  private Button removeEqLvl;
  private Button addEqLvl;
  private Controller controller;
  private SwitchCompat genderSwitch;

  public LevelControlFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_level_control, container, false);
    GameActivity gameActivity = (GameActivity) getActivity();
    controller = gameActivity.getController();
    basicLvl = view.findViewById(R.id.basicLvlTextView);
    eqLvl = view.findViewById(R.id.eqLvLTextView);
    totalLvl = view.findViewById(R.id.totalLvlTextView);
    removeBasicLvl = view.findViewById(R.id.removeBasicLvl);
    addBasicLvl = view.findViewById(R.id.addBasicLvl);
    removeEqLvl = view.findViewById(R.id.removeEqLvl);
    addEqLvl = view.findViewById(R.id.addEqLvl);
    genderSwitch = view.findViewById(R.id.genderSwitch);
    removeBasicLvl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        controller.removeBasicLvl();
        updateBasicLvl();
        updateTotal();
      }
    });
    addBasicLvl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        controller.addBasicLvl();
        updateBasicLvl();
        updateTotal();
      }
    });
    removeEqLvl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        controller.removeEqLvl();
        updateEqLvl();
        updateTotal();
      }
    });
    addEqLvl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        controller.addEqLvl();
        updateEqLvl();
        updateTotal();
      }
    });
    genderSwitch.setChecked(!controller.getGender());
    genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        controller.setGender(!isChecked);
      }
    });
    updateBasicLvl();
    updateEqLvl();
    updateTotal();
    return view;
  }

  private void updateTotal() {
    totalLvl.setText(Integer.toString(controller.getMyTotalLvl()));
  }

  private void updateEqLvl() {
    eqLvl.setText(Integer.toString(controller.getMyEqLvl()));
  }

  private void updateBasicLvl() {
    basicLvl.setText(Integer.toString(controller.getMyBasicLvl()));
  }

}
