package com.example.marcin.munchkinleveltracker;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class PlayerField extends TableLayout {

  private String name;
  private TextView nick;
  private TextView bsLvlLabel;
  private TextView bsLvlVal;
  private TextView totalLvlLabel;
  private TextView totalLvlVal;
  private TextView gender;
  private TableRow nickRow;
  private TableRow bsLvlLabelRow;
  private TableRow bsLvlValRow;
  private TableRow totalLvlLabelRow;
  private TableRow totalLvlValRow;
  private TableRow genderRow;
  private Handler handler;

  public PlayerField(Context context, Handler handler) {
    super(context);
    this.handler = handler;
    nick = new TextView(context);
    nick.setText("");
    bsLvlLabel = new TextView(context);
    bsLvlLabel.setText("BasicLvl");
    bsLvlVal = new TextView(context);
    bsLvlVal.setText("1");
    totalLvlLabel = new TextView(context);
    totalLvlLabel.setText("TotalLvl");
    totalLvlVal = new TextView(context);
    totalLvlVal.setText("1");
    gender = new TextView(context);
    gender.setText("M");
    nickRow = new TableRow(context);
    bsLvlLabelRow = new TableRow(context);
    bsLvlValRow = new TableRow(context);
    totalLvlLabelRow = new TableRow(context);
    totalLvlValRow = new TableRow(context);
    genderRow = new TableRow(context);
    nick.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    bsLvlLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    bsLvlVal.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    totalLvlLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    totalLvlVal.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    gender.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    nick.setGravity(Gravity.CENTER);
    bsLvlLabel.setGravity(Gravity.CENTER);
    bsLvlVal.setGravity(Gravity.CENTER);
    totalLvlLabel.setGravity(Gravity.CENTER);
    totalLvlVal.setGravity(Gravity.CENTER);
    gender.setGravity(Gravity.CENTER);
    nick.setId(0);
    bsLvlLabel.setId(0);
    bsLvlVal.setId(0);
    totalLvlLabel.setId(0);
    totalLvlVal.setId(0);
    gender.setId(0);
    nickRow.addView(nick);
    nickRow.setGravity(Gravity.CENTER);
    bsLvlLabelRow.addView(bsLvlLabel);
    bsLvlLabelRow.setGravity(Gravity.CENTER);
    bsLvlValRow.addView(bsLvlVal);
    bsLvlValRow.setGravity(Gravity.CENTER);
    totalLvlLabelRow.addView(totalLvlLabel);
    totalLvlLabelRow.setGravity(Gravity.CENTER);
    totalLvlValRow.addView(totalLvlVal);
    totalLvlValRow.setGravity(Gravity.CENTER);
    genderRow.addView(gender);
    genderRow.setGravity(Gravity.CENTER);
    addView(nickRow, new TableRow.LayoutParams(300, 70));
    addView(bsLvlLabelRow, new TableRow.LayoutParams(300, 70));
    addView(bsLvlValRow, new TableRow.LayoutParams(300, 70));
    addView(totalLvlLabelRow, new TableRow.LayoutParams(300, 70));
    addView(totalLvlValRow, new TableRow.LayoutParams(300, 70));
    addView(genderRow, new TableRow.LayoutParams(300, 70));
  }

  void setName(String nickName) {
    nick.setText(nickName);
    name = nickName;
  }

  String getName() {
    return name;
  }

  void update(final int basic, final int total, final boolean genderVal) {
    handler.post(new Runnable() {
      public void run() {
        bsLvlVal.setText(Integer.toString(basic));
        totalLvlVal.setText(Integer.toString(total));
        if(genderVal == Player.MAN){
          gender.setText("M");
          gender.setTextColor(Color.BLUE);
        }
        else {
          gender.setText("W");
          gender.setTextColor(Color.MAGENTA);
        }
      }
    });
  }
}
