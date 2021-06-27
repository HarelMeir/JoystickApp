package com.example.joystickapp.view;

//android x
import androidx.appcompat.app.AppCompatActivity;
//default
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
//Logging(status based on reuslt.
import android.util.Log;
//set text to button for instance.
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joystickapp.R;
import com.example.joystickapp.viewModel.VM;

import org.w3c.dom.Text;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private VM vm;
    private Button confirmButton;
    private SeekBar rudder;
    private SeekBar throttle;
    private TextView rudderVal;
    private TextView throttleVal;
    private Joystick joystick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vm = new VM();
        //creating seeksBar listeners.
        rudder = (SeekBar)findViewById(R.id.rudder_bar);
        rudderVal = (TextView)findViewById(R.id.r_value);

        throttle = (SeekBar)findViewById(R.id.throttle_bar);
        throttleVal = (TextView)findViewById(R.id.t_value);
        joystick = findViewById(R.id.joystick_frag);
        joystick.service = (a,e)-> {
            vm.updateAileron(a);
            vm.updateElevator(e);
        };


        rudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float currentValue = (rudder.getProgress() - 100f) / 100f;
                rudderVal.setText(String.valueOf(currentValue));
                vm.updateRudder(currentValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float currentValue = throttle.getProgress() / 100f;
                throttleVal.setText(String.valueOf(currentValue));
                vm.updateThrottle(currentValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        //create click listener for the confirm button.
        confirmButton  = (Button)findViewById(R.id.confirm_str);
        confirmButton.setOnClickListener(this::connect);





    }

    @SuppressLint("SetTextI18n")
    private void connect(View v) {
        String error = "Details are Missing or incorrect. try again.\n";
        try {
            this.confirmButton.setEnabled(false);

            //the editText input.
            EditText ipText = (EditText)findViewById(R.id.cpu_IP);
            EditText portText = (EditText)findViewById(R.id.server_port);

            //convert it to the right types.
            String ip = ipText.getText().toString().trim();
            String portStr = portText.getText().toString().trim();

            //checking if all the details inserted.
            if(isMissing(ip, portStr)) {
                this.confirmButton.setEnabled(true);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                return;
            }
            //converting the port to int, and trying to connect.
            int port = Integer.parseInt(portStr);
            vm.connect(ip, port);
            this.confirmButton.setText("Connected!");

        } catch(Exception e) {
            //e.printStackTrace();
            this.confirmButton.setEnabled(true);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }


    private boolean isMissing(String ip, String port) {
        return ip.equals("") || port.equals("");
    }


}