package com.example.infinite.widgettask;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayTime;

    private EditText edt_lbl;

    private Button submit;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        initViews();

        setListeners();

    }


    private void initViews() {

        displayTime = findViewById(R.id.txt_display_time);
        displayTime.setPaintFlags(displayTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        edt_lbl = findViewById(R.id.edt_lbl);

        submit = findViewById(R.id.submit);
    }

    private void setListeners() {

        submit.setOnClickListener(this);
        displayTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txt_display_time:

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String str_selected_time = "Selected Time : " + String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
                        displayTime.setText(str_selected_time);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;

            case R.id.submit:

                hide_keyboard();

                String time = displayTime.getText().toString();

                if (!time.equalsIgnoreCase("Click to select Time")) {

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    String label = edt_lbl.getText().toString();

                    editor.putString("time", time);
                    editor.putString("label", label);
                    editor.apply();

                    Toast toast = Toast.makeText(context, "Widget Added, to display it on home screen just select it from \"Add widgets\" by pressing long click on your home screen", Toast.LENGTH_LONG);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.GREEN);
                    toast.show();

                } else {

                    Toast toast_err = Toast.makeText(context, "Please select time!!!", Toast.LENGTH_LONG);
                    TextView v = (TextView) toast_err.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.parseColor("#FF4081"));
                    toast_err.show();

                }


                break;
        }
    }

    private void hide_keyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}

